package com.njupt.sms.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.njupt.sms.Session;
import com.njupt.sms.beans.Student;
import com.njupt.sms.utils.*;
import org.jfree.chart.*;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.*;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class HomeStudent {
    private JPanel homeStudent;
    private JButton exitButton;
    private JTabbedPane tabbedPane1;
    private JTextField studentCode;
    private JTextField name;
    private JSlider age;
    private JComboBox sex;
    private JTextField birthday;
    private JTextField address;
    private JTextField phone;
    private JTextField email;
    private JButton modifyButton;
    private JLabel nameLabel;
    private JTextField studentClass;
    private JTable table1;
    private JButton chooseOkButton;
    private JTable table2;
    private JLabel tip;
    private JLabel agelabel;
    private JButton 成绩可视化Button;
    private JRadioButton pie;
    private JRadioButton bar;
    public JButton button2;
    public JTextField FilePath;
    private JFrame frame;

    private GradesQueryModel gradesQueryModel;

    private ChooseCourseModel chooseCourseModel;


    public static void main(String[] args) {

    }

    public HomeStudent() {


        // 初始化个人资料界面中的信息
        StudentInfoUtils studentInfoUtils = new StudentInfoUtils();
        Student sessionStudent = (Student) Session.userInfo;
        $$$setupUI$$$();
        Student databaseStudent = studentInfoUtils.findStudentInfoByUsername(sessionStudent.getUsername());
        updateStudentInfo(databaseStudent);

        chooseCourseModel = new ChooseCourseModel();
        table1.setModel(chooseCourseModel);


        gradesQueryModel = new GradesQueryModel();
        table2.setModel(gradesQueryModel);

        frame = new JFrame("HomeStudent");
        frame.setContentPane(homeStudent);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();

        UICommonUtils.makeFrameToCenter(frame);

        frame.setVisible(true);


        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student preModifyStudent = new Student();

                preModifyStudent.setAge(age.getValue());
                preModifyStudent.setSex(sex.getItemAt(sex.getSelectedIndex()).toString().trim());

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date newDate = null;

                boolean flag = false;
                boolean sqlflag = true;

                try {
                    newDate = dateFormat.parse(birthday.getText().trim());

                } catch (ParseException e1) {
                    sqlflag = false;
                    e1.printStackTrace();
                }
                try {
                    // 程序代码
                    preModifyStudent.setBirthday(newDate);
                } catch (Exception e1) {
                    sqlflag = false;
                    //Catch 块
                    JOptionPane.showMessageDialog(frame, "生日格式有误", "提示", JOptionPane.WARNING_MESSAGE);
                }

                preModifyStudent.setAddress(address.getText().trim());
                preModifyStudent.setPhone(phone.getText().trim());
                preModifyStudent.setEmail(email.getText().trim());
                preModifyStudent.setStudentCode(studentCode.getText().trim());
                try {
                    // 程序代码
                    preModifyStudent.setStudentClass(Integer.parseInt(studentClass.getText().trim()));
                } catch (Exception e1) {
                    //Catch 块
                    sqlflag = false;
                    JOptionPane.showMessageDialog(frame, "班级只能为数字", "提示", JOptionPane.WARNING_MESSAGE);
                }


                if (sqlflag) {
                    flag = studentInfoUtils.saveStudentInfo(preModifyStudent);
                }
                if (flag == true) {
                    JOptionPane.showMessageDialog(frame, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(frame, "修改失败", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // 退出按钮
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                frame.dispose();
                LoginLogoutUtils loginLogoutUtils = new LoginLogoutUtils();
                loginLogoutUtils.logout();
            }
        });

        // 选课按钮点击事件
        chooseOkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table1.getSelectedRow() > -1) {
                    int courseId = Integer.parseInt(chooseCourseModel.getValueAt(table1.getSelectedRow(), 0).toString().trim());


                    System.out.println(chooseCourseModel.getValueAt(table1.getSelectedRow(), 5));
                    if (chooseCourseModel.getValueAt(table1.getSelectedRow(), 5) != "") {
                        chooseCourseModel.updateChoosenInfo();
                        return;
                    }
                    boolean flag = chooseCourseModel.saveChooseCourse(courseId);
                    if (flag == true) {
                        System.out.println("选课信息保存成功");

                    }

                } else {
                    JOptionPane.showMessageDialog(frame, "请在上面表格中选择一列", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        age.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                agelabel.setText("年龄：" + age.getValue());
            }
        });
        成绩可视化Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bar.isSelected()) {
                    makeBar("学生成绩条形图");
                } else {
                    makePie("学生成绩饼图");
                }

            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser("C:\\");
                int val = fc.showOpenDialog(null);    //文件打开对话框
                if (val == fc.APPROVE_OPTION) {
                    //正常选择文件
                    FilePath.setText(fc.getSelectedFile().toString());
                } else {
                    //未正常选择文件，如选择取消按钮
                    FilePath.setText("未选择文件");
                }
            }
        });
    }

    public void makePie(String title) {
        DefaultPieDataset dpd = new DefaultPieDataset(); //建立一个默认的饼图
        GradeUtils gradeUtils = new GradeUtils();
        Student student = (Student) Session.userInfo;
        List<Map<String, Object>> List = gradeUtils.getGradesByStudentId(student.getId());


        int verygood = 0;
        int good = 0;
        int normal = 0;
        int bad = 0;
        int shit = 0;
        for (Map<String, Object> x : List) {
            int score = (int) x.get("score");

            if (score > 90) {
                verygood = verygood + 1;
            } else if (score > 80) {
                good = good + 1;
            } else if (score > 70) {
                normal = normal;
            } else if (score > 60) {
                bad = bad + 1;
            } else {
                shit = shit + 1;
            }
        }
        dpd.setValue("优（90-100）", verygood);
        dpd.setValue("良（80-90）", good);
        dpd.setValue("中（70-80）", normal);
        dpd.setValue("差（60-70）", bad);
        dpd.setValue("不及格（<60）", shit);
        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
//设置标题字体
        standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
//设置图例的字体
        standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
//设置轴向的字体
        standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));
//应用主题样式
        ChartFactory.setChartTheme(standardChartTheme);

        JFreeChart chart = ChartFactory.createPieChart3D(title, dpd, true, true, false);
        PiePlot3D piePlot = (PiePlot3D) chart.getPlot();
        piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator(("{0}:({2})"), NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
        //可以查具体的API文档,第一个参数是标题，第二个参数是一个数据集，第三个参数表示是否显示Legend，第四个参数表示是否显示提示，第五个参数表示图中是否存在URL
        ChartFrame chartFrame = new ChartFrame(title, chart);
        //chart要放在Java容器组件中，ChartFrame继承自java的Jframe类。该第一个参数的数据是放在窗口左上角的，不是正中间的标题。
        chartFrame.pack(); //以合适的大小展现图形
        chartFrame.setVisible(true);//图形是否可见
    }


    public void makeBar(String title) {

        Bar3D demo = new Bar3D(title);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }


    private void updateStudentInfo(Student student) {
        studentCode.setText(student.getStudentCode().trim());
        name.setText(student.getName().trim());
        age.setValue(student.getAge());

        if (student.getSex().trim() == "男") {
            sex.setSelectedIndex(0);
        } else {
            sex.setSelectedIndex(1);
        }

        birthday.setText(student.getBirthday().toString());
        address.setText(student.getAddress());
        phone.setText(student.getPhone());
        email.setText(student.getEmail());
        nameLabel.setText(student.getName().trim());
        studentClass.setText(student.getStudentClass() + "");

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        homeStudent = new JPanel();
        homeStudent.setLayout(new GridLayoutManager(3, 6, new Insets(0, 0, 0, 0), -1, -1));
        exitButton = new JButton();
        exitButton.setText("退出");
        homeStudent.add(exitButton, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("欢迎学生");
        homeStudent.add(label1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameLabel = new JLabel();
        nameLabel.setText("张加胜");
        homeStudent.add(nameLabel, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        homeStudent.add(panel1, new GridConstraints(2, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tabbedPane1 = new JTabbedPane();
        panel1.add(tabbedPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("成绩查询", panel2);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel2.add(scrollPane1, BorderLayout.CENTER);
        scrollPane1.setBorder(BorderFactory.createTitledBorder(null, "学生成绩表", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        table2 = new JTable();
        table2.setBackground(new Color(-2034196));
        table2.setForeground(new Color(-12502992));
        scrollPane1.setViewportView(table2);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("学生选课", panel3);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel4.add(scrollPane2, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane2.setBorder(BorderFactory.createTitledBorder(null, "选课信息表", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        table1 = new JTable();
        table1.setBackground(new Color(-2034196));
        table1.setForeground(new Color(-12502992));
        scrollPane2.setViewportView(table1);
        chooseOkButton = new JButton();
        chooseOkButton.setText("确定选课");
        panel4.add(chooseOkButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel4.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("个人资料", panel5);
        modifyButton = new JButton();
        modifyButton.setText("修改");
        panel5.add(modifyButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(9, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel5.add(panel6, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("学号");
        panel6.add(label2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        studentCode = new JTextField();
        studentCode.setEditable(false);
        studentCode.setEnabled(true);
        panel6.add(studentCode, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("姓名");
        panel6.add(label3, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        name = new JTextField();
        name.setEditable(false);
        name.setEnabled(true);
        panel6.add(name, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("年龄");
        panel6.add(label4, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        age = new JSlider();
        age.setMajorTickSpacing(10);
        age.setMinorTickSpacing(1);
        age.setPaintLabels(true);
        age.setPaintTicks(true);
        age.setValue(18);
        panel6.add(age, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("生日");
        panel6.add(label5, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sex = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("男");
        defaultComboBoxModel1.addElement("女");
        sex.setModel(defaultComboBoxModel1);
        panel6.add(sex, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("性别");
        panel6.add(label6, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        birthday = new JTextField();
        birthday.setEditable(true);
        birthday.setEnabled(true);
        panel6.add(birthday, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("地址");
        panel6.add(label7, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        address = new JTextField();
        address.setText("");
        panel6.add(address, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("电话");
        panel6.add(label8, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        phone = new JTextField();
        phone.setEnabled(true);
        phone.setText("");
        panel6.add(phone, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("email");
        panel6.add(label9, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        email = new JTextField();
        email.setText("");
        panel6.add(email, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        studentClass = new JTextField();
        panel6.add(studentClass, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("班级");
        panel6.add(label10, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        agelabel = new JLabel();
        agelabel.setText("18");
        panel6.add(agelabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tip = new JLabel();
        tip.setText("更改密码请找管理员更改");
        panel5.add(tip, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        成绩可视化Button = new JButton();
        成绩可视化Button.setText("成绩可视化");
        homeStudent.add(成绩可视化Button, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bar = new JRadioButton();
        bar.setSelected(true);
        bar.setText("条形图");
        homeStudent.add(bar, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pie = new JRadioButton();
        pie.setText("饼图");
        homeStudent.add(pie, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(bar);
        buttonGroup.add(pie);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return homeStudent;
    }

    private class ChooseCourseModel extends AbstractTableModel {

        Student student = (Student) Session.userInfo;


        private ChooseCourseUtils chooseCourseUtils = new ChooseCourseUtils();
        private List<Map<String, Object>> list = getALlChooseCourse();


        String[] columnStrings = {"id", "courseName", "academicYear", "term", "name", "hasChoosen", "teacherId"};
        String[] columnShowStrings = {"编号", "课程名", "学年", "学期", "教师姓名", "是否选择"};

        public void updateChoosenInfo() {
            list = getALlChooseCourse();
            fireTableDataChanged();

        }

        public boolean saveChooseCourse(int courseId) {
            boolean flag = chooseCourseUtils.saveChoosen(student.getId(), courseId);
            updateChoosenInfo();

            return flag;
        }

        public List<Map<String, Object>> getALlChooseCourse() {
            return chooseCourseUtils.findAllChooseCourse(student.getId());
        }

        @Override
        public int getRowCount() {
            return list.size();
        }

        @Override
        public int getColumnCount() {
            return columnStrings.length - 1;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Map<String, Object> map = list.get(rowIndex);
            return map.get(columnStrings[columnIndex]);
        }

        @Override
        public String getColumnName(int column) {
            return columnShowStrings[column];
        }
    }


    private class GradesQueryModel extends AbstractTableModel {

        Student student = (Student) Session.userInfo;

        private GradeUtils gradeUtils = new GradeUtils();
        private List<Map<String, Object>> list = gradeUtils.getGradesByStudentId(student.getId());

        String[] columnStrings = {"id", "courseName", "academicYear", "term", "name", "score"};
        String[] columnShowingStrings = {"编号", "课程", "学年", "学期", "教师", "分数"};

        public List<Map<String, Object>> GetList() {
            return this.list;
        }

        @Override
        public int getRowCount() {
            return list.size();
        }

        @Override
        public int getColumnCount() {
            return columnShowingStrings.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Map<String, Object> map = list.get(rowIndex);
            return map.get(columnStrings[columnIndex]);
        }

        @Override
        public String getColumnName(int column) {
            return columnShowingStrings[column];
        }
    }
}


class Bar3D extends ApplicationFrame {
    /**
     * Creates a new demo instance.
     *
     * @param title the frame title.
     */
    public Bar3D( String title) {
        super("条形图");
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        DefaultCategoryDataset Bdataset = new DefaultCategoryDataset();
        GradeUtils gradeUtils = new GradeUtils();
        Student student = (Student) Session.userInfo;
        List<Map<String, Object>> List = gradeUtils.getGradesByStudentId(student.getId());
        for(Map<String, Object> x : List ){
            int score= (int) x.get("score");
            String courseName= (String) x.get("courseName");
            Bdataset.addValue(score, courseName, "Column 1");

        }
        Bdataset.addValue(100, "满分", "Column 1");

        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
//设置标题字体
        standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
//设置图例的字体
        standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
//设置轴向的字体
        standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));
//应用主题样式
        ChartFactory.setChartTheme(standardChartTheme);

        JFreeChart chart = ChartFactory.createBarChart3D(
                title, // chart title
                "各科目成绩", // domain axis label
                "分数", // range axis label
                Bdataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
        );
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
    }
}