package com.njupt.sms.ui;

import com.njupt.sms.Session;
import com.njupt.sms.beans.Teacher;
import com.njupt.sms.utils.CourseUtils;
import com.njupt.sms.utils.GradeUtils;
import com.njupt.sms.utils.TeacherUtils;
import com.njupt.sms.utils.UICommonUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HomeTeacher {
    private JPanel homeTeacher;
    private JButton exitButton;
    private JTabbedPane tabbedPane1;
    private JTable infoTable;
    private JTable table2;
    private JTable table3;
    private JButton commitButton;
    private JButton saveToDraftButton;
    private JLabel welcomeNameLabel;
    private JButton 成绩可视化Button;
    private JRadioButton bar;
    private JRadioButton pie;
    private JFrame frame;
    private TeacherInfoModel teacherInfoModel;
    private CourseInfoModel courseInfoModel;
    private GradeInputModel gradeInputModel;


    public void makeRightAble() {
        saveToDraftButton.setEnabled(true);
        commitButton.setEnabled(true);
        table3.setEnabled(true);

    }

    public void makeRightDisable() {

        saveToDraftButton.setEnabled(false);
        commitButton.setEnabled(false);
        table3.setEnabled(false);
    }

    public HomeTeacher() {

        // 添加模型
        teacherInfoModel = new TeacherInfoModel();
        infoTable.setModel(teacherInfoModel);

        courseInfoModel = new CourseInfoModel();
        table2.setModel(courseInfoModel);


        gradeInputModel = new GradeInputModel();
        table3.setModel(gradeInputModel);

        infoTable.setToolTipText("不可更改用户名和姓名，更改求助管理员");
        saveToDraftButton.setToolTipText("成绩可以暂存多次");
        commitButton.setToolTipText("提交后无法更改，请注意");
//        saveToDraftButton.setEnabled(false);
//        commitButton.setEnabled(false);
//        table3.setEnabled(false);

        frame = new JFrame("HomeTeacher");
        frame.setContentPane(homeTeacher);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();

        UICommonUtils.makeFrameToCenter(frame);
        frame.setVisible(true);


        Teacher teacher = (Teacher) Session.userInfo;
        welcomeNameLabel.setText(teacher.getName());


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                frame.dispose();
                Session.userInfo = null;
            }
        });


        table2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // System.out.println(table2.getSelectedRow());
                int courseId = Integer.parseInt(courseInfoModel.getValueAt(table2.getSelectedRow(), 0).toString().trim());
                boolean useDraft = !courseInfoModel.getValueAt(table2.getSelectedRow(), 4).toString().equals("已提交");
                if (useDraft) {
                    makeRightAble();
                } else {
                    makeRightDisable();

                }
                gradeInputModel.setStudentByCourseId(courseId, useDraft);

                super.mouseClicked(e);
            }
        });

        saveToDraftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gradeInputModel.saveToDraft();
                JOptionPane.showMessageDialog(frame, "成绩暂存成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                courseInfoModel.update();
            }
        });


        commitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                boolean flag = gradeInputModel.commitGrades();
                if (flag == true) {
                    makeRightDisable();
                    courseInfoModel.update();

                }
            }
        });
        成绩可视化Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bar.isSelected()) {

                    HomeAdmin.CourseStatisticModel courseStatisticModel = new HomeAdmin.CourseStatisticModel();
                    if (table2.getSelectedRow() > -1) {
                        Teacher teacher = (Teacher) Session.userInfo;

                        CourseUtils courseUtils = new CourseUtils();

                        List<Map<String, Object>> List = courseUtils.findAllCoursesByTeacherId(teacher.getId());
                        String courseName = (String) List.get(table2.getSelectedRow()).get("courseName");
                        Map<String, Object> map = courseStatisticModel.getData(courseName);
                        if (map.get("差") == "") {
                            JOptionPane.showMessageDialog(frame, "所选项目成绩没有记录", "提示", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        make3DBarChartByMap(map, "按课程(" + courseName + ")查询的统计图");
                        return;


                    } else {
                        JOptionPane.showMessageDialog(frame, "请选择一项课程", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;

                    }

                } else {
                    HomeAdmin.CourseStatisticModel courseStatisticModel = new HomeAdmin.CourseStatisticModel();

                    if (table2.getSelectedRow() > -1) {
                        Teacher teacher = (Teacher) Session.userInfo;

                        CourseUtils courseUtils = new CourseUtils();

                        List<Map<String, Object>> List = courseUtils.findAllCoursesByTeacherId(teacher.getId());
                        String courseName = (String) List.get(table2.getSelectedRow()).get("courseName");
                        Map<String, Object> map = courseStatisticModel.getData(courseName);
                        if (map.get("差") == "") {
                            JOptionPane.showMessageDialog(frame, "所选项目成绩没有提交", "提示", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        HomeAdmin.make3DPieChartByMap(map, "按课程(" + courseName + ")查询的统计图");
                        return;


                    } else {
                        JOptionPane.showMessageDialog(frame, "请选择一项课程", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;

                    }


                }

            }
        });
    }

    public void make3DBarChartByMap(Map<String, Object> map, String title) {

        BarExample3D demo = new BarExample3D(map, title);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        homeTeacher = new JPanel();
        homeTeacher.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 7, new Insets(0, 0, 0, 0), -1, -1));
        exitButton = new JButton();
        exitButton.setText("退出");
        homeTeacher.add(exitButton, new com.intellij.uiDesigner.core.GridConstraints(0, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        welcomeNameLabel = new JLabel();
        welcomeNameLabel.setText("张加胜");
        homeTeacher.add(welcomeNameLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("欢迎老师");
        homeTeacher.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tabbedPane1 = new JTabbedPane();
        homeTeacher.add(tabbedPane1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 7, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("成绩录入/查询", panel1);
        final JSplitPane splitPane1 = new JSplitPane();
        panel1.add(splitPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPane1.setLeftComponent(panel2);
        panel2.setBorder(BorderFactory.createTitledBorder(null, "所授课程", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel2.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(250, -1), null, null, 0, false));
        table2 = new JTable();
        table2.setBackground(new Color(-2034196));
        table2.setForeground(new Color(-12502992));
        scrollPane1.setViewportView(table2);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        splitPane1.setRightComponent(panel3);
        panel3.setBorder(BorderFactory.createTitledBorder(null, "成绩录入", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        commitButton = new JButton();
        commitButton.setText("提交");
        panel3.add(commitButton, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveToDraftButton = new JButton();
        saveToDraftButton.setText("成绩表格暂存");
        panel3.add(saveToDraftButton, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel3.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel3.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table3 = new JTable();
        table3.setBackground(new Color(-2034196));
        table3.setForeground(new Color(-12502992));
        scrollPane2.setViewportView(table3);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("教师信息管理", panel4);
        panel4.setBorder(BorderFactory.createTitledBorder(null, "教师个人信息", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JScrollPane scrollPane3 = new JScrollPane();
        scrollPane3.setForeground(new Color(-12502992));
        panel4.add(scrollPane3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        infoTable = new JTable();
        infoTable.setBackground(new Color(-2034196));
        scrollPane3.setViewportView(infoTable);
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Apple Chancery", Font.BOLD, 18, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-4496289));
        label2.setText("提示:表格中直接编辑,回车自动保存");
        panel4.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setForeground(new Color(-49619));
        label3.setText("不可更改用户名和姓名，更改求助管理员");
        panel4.add(label3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        成绩可视化Button = new JButton();
        成绩可视化Button.setText("成绩可视化");
        homeTeacher.add(成绩可视化Button, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bar = new JRadioButton();
        bar.setSelected(true);
        bar.setText("条形图");
        homeTeacher.add(bar, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pie = new JRadioButton();
        pie.setText("饼图");
        homeTeacher.add(pie, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(bar);
        buttonGroup.add(pie);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return homeTeacher;
    }

    public class BarExample3D extends ApplicationFrame {
        /**
         * Creates a new demo instance.
         *
         * @param title the frame title.
         */
        public BarExample3D(Map<String, Object> map, String title) {
            super("条形图");
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

//        dpd.setValue("良（80-90）", Integer.parseInt(map.get("良").toString()));
//        dpd.setValue("中（70-80）", Integer.parseInt(map.get("中").toString()));
//        dpd.setValue("差（60-70）", Integer.parseInt(map.get("差").toString()));
//        dpd.setValue("不及格（<60）", Integer.parseInt(map.get("不及格").toString()));
            dataset.addValue(Integer.parseInt(map.get("优").toString()), "优（90-100）", "Column 1");
            dataset.addValue(Integer.parseInt(map.get("良").toString()), "良（80-90）", "Column 1");
            dataset.addValue(Integer.parseInt(map.get("中").toString()), "中（70-80）", "Column 1");
            dataset.addValue(Integer.parseInt(map.get("差").toString()), "差（60-70）", "Column 1");
            dataset.addValue(Integer.parseInt(map.get("不及格").toString()), "不及格（<60）", "Column 1");


            JFreeChart chart = ChartFactory.createBarChart3D(
                    title, // chart title
                    "各层次成绩", // domain axis label
                    "数量", // range axis label
                    dataset, // data
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

    private class TeacherInfoModel extends AbstractTableModel {
        Teacher teacher = (Teacher) Session.userInfo;
        TeacherUtils teacherUtils = new TeacherUtils();
        Map<String, Object> map = teacherUtils.findTeacherMapById(teacher.getId());


        String[] columnStrings = {"id", "username", "password", "name", "phone", "email"};
        String[] columnShowStrings = {"编号", "用户名", "密码", "姓名", "电话", "邮箱"};

        @Override

        public int getRowCount() {
            return 1;
        }

        @Override
        public int getColumnCount() {
            return map.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return map.get(columnStrings[columnIndex]);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 2 || columnIndex == 4 || columnIndex == 5;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            super.setValueAt(aValue, rowIndex, columnIndex);
            map.put(columnStrings[columnIndex], aValue);
            save();
        }

        private void save() {
            teacherUtils.saveTeacher(map);
        }

        @Override
        public String getColumnName(int column) {
            return columnShowStrings[column];
        }
    }

    private class CourseInfoModel extends AbstractTableModel {

        Teacher teacher = (Teacher) Session.userInfo;

        private CourseUtils courseUtils = new CourseUtils();
        private List<Map<String, Object>> list = getAllCourses();


        String[] columnStrings = {"id", "courseName", "academicYear", "term", "commitStatus"};
        String[] columnShowStrings = {"编号", "课程名", "所属学年", "学期", "提交状态"};


        public void update() {
            list = getAllCourses();
            fireTableDataChanged();

        }

        private List<Map<String, Object>> getAllCourses() {
            return courseUtils.findAllCoursesByTeacherId(teacher.getId());
        }


        @Override
        public int getRowCount() {
            //System.out.println(list);
            return list.size();
        }

        @Override
        public int getColumnCount() {
            return columnStrings.length;
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

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {


            return false;
        }

    }


    private class GradeInputModel extends AbstractTableModel {

        private int courseId;


        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        private CourseUtils courseUtils = new CourseUtils();
        private GradeUtils gradeUtils = new GradeUtils();
        private List<Map<String, Object>> list = new ArrayList<>();


        String[] columnStrings = {"id", "studentCode", "name", "score", "courseId"};
        String[] columnShowStrings = {"编号", "学号", "姓名", "成绩"};

        public List<Map<String, Object>> getAllStudentByCourseId(int courseId, boolean useDraft) {
            if (useDraft == true) {
                return courseUtils.findAllStudentWithGradeDraftByCourseId(courseId);
            } else {
                return courseUtils.findAllStudentWithGradeByCourseId(courseId);
            }

        }

        public boolean commitGrades() {


            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                if (map.get("score") == null || map.get("score") == "") {
                    JOptionPane.showMessageDialog(frame, "请将成绩填写完整后再提交", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
            }
            Object[] options = {" 确定 ", " 取消 "};
            int response = JOptionPane.showConfirmDialog(null, "确定要提交吗？", "删除提示", 0);
            if (response == 0) {


                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> map = list.get(i);
                    gradeUtils.saveGrade(map);
                    courseUtils.commitCourseByCourseId(courseId);

                }
                return true;

            } else {
                return false;
            }

        }

        /**
         * 调用来设置table中的显示数据
         *
         * @param courseId
         */
        public void setStudentByCourseId(int courseId, boolean useDraft) {

            this.courseId = courseId;

            list = getAllStudentByCourseId(courseId, useDraft);
            fireTableDataChanged();

        }

        public void saveToDraft() {
            for (int i = 0; i < list.size(); i++) {
                gradeUtils.saveGradeToDraft(list.get(i));
            }

            courseUtils.draftCourseByCourseId(courseId);
            setStudentByCourseId(courseId, true);


        }


        @Override
        public int getRowCount() {
            return list.size();
        }

        @Override
        public int getColumnCount() {
            return columnShowStrings.length;
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

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == columnShowStrings.length - 1;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Map<String, Object> map = list.get(rowIndex);
            map.put(columnStrings[columnIndex], aValue);

        }


    }
}
