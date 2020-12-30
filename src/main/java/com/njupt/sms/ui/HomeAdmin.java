package com.njupt.sms.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.njupt.sms.Session;
import com.njupt.sms.beans.Admin;
import com.njupt.sms.utils.*;
import org.jfree.chart.*;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeAdmin {
    private JPanel homeAdmin;
    private JButton exitButton;
    private JLabel nameLabel;
    private JTabbedPane tabbedPane1;
    private JTable table1;
    private JTable table2;
    private JButton addOneRowCourseButton;
    private JButton deleteOneRowCourseButton;
    private JButton addOneRowTeacherButton1;
    private JButton deleteOneRowTeacherButton1;
    private JButton saveCourseButton;
    private JButton saveTeacherButton;
    private JTable table3;
    private JButton addOneStudentRowButton;
    private JButton saveStudentTableButton;
    private JButton deleteOneStudentRowButton;
    private JTable table4;
    private JButton cleanButton;
    private JTable table5;
    private JTable table6;
    private JButton statisticButton;
    private JTabbedPane tabbedPane2;
    private JTable course;
    private JTable studentClass;
    private JTable student;
    private JTable time;
    private JButton updateButton;
    private JButton insertButton;


    private JButton chartButton;
    private JButton pie3DchartButton;
    private JButton chart3DButton;
    private JComboBox comboBox1;
    private JFrame frame;

    private CourseModel courseModel;
    private TeacherModel teacherModel;

    private StudentInfoModel studentInfoModel;

    private ImportedGradeModel importedGradeModel;

    private CourseInfoModel courseInfoModel;
    private GradeInputModel gradeInputModel;

    private CourseStatisticModel courseStatisticModel;
    private TimeStatisticModel timeStatisticModel;
    private ClassStatisticModel classStatisticModel;
    private StudentStatisticModel studentStatisticModel;

    public static void main(String[] args) {

    }

    public HomeAdmin() {


        courseModel = new CourseModel();
        teacherModel = new TeacherModel();


        table1.setModel(courseModel);
        table2.setModel(teacherModel);


        studentInfoModel = new StudentInfoModel();
        table3.setModel(studentInfoModel);

        importedGradeModel = new ImportedGradeModel();
        table4.setModel(importedGradeModel);

        courseInfoModel = new CourseInfoModel();
        table5.setModel(courseInfoModel);

        gradeInputModel = new GradeInputModel();
        table6.setModel(gradeInputModel);

        courseStatisticModel = new CourseStatisticModel();
        timeStatisticModel = new TimeStatisticModel();
        classStatisticModel = new ClassStatisticModel();
        studentStatisticModel = new StudentStatisticModel();

//        private JTable course;
//        private JTable studentClass;
//        private JTable student;
//        private JTable time;
        course.setModel(courseStatisticModel);
        studentClass.setModel(classStatisticModel);
        time.setModel(timeStatisticModel);
        student.setModel(studentStatisticModel);

        insertButton.setToolTipText("单独添加课程，无需点击老师表格");
        saveCourseButton.setToolTipText("只有点击保存，才会提交到数据库");
        saveTeacherButton.setToolTipText("只有点击保存，才会提交到数据库");
        deleteOneRowTeacherButton1.setToolTipText("第一行不允许删除");
        addOneRowTeacherButton1.setToolTipText("从最后一行开始添加");
        addOneRowCourseButton.setToolTipText("从最后一行开始添加");
        exitButton.setToolTipText("返回登录界面");

        Admin admin = (Admin) Session.userInfo;
        nameLabel.setText(admin.getUsername());


        frame = new JFrame("HomeAdmin");
        frame.setContentPane(homeAdmin);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();

        UICommonUtils.makeFrameToCenter(frame);

        frame.setVisible(true);

        addOneRowCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(table2.getSelectedRow() > -1) || table2.getValueAt(table2.getSelectedRow(), 3) == null) {
                    JOptionPane.showMessageDialog(frame, "请在教师表中选择一项有教师姓名的行", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else {
                    Map<String, Object> map = new HashMap<String, Object>();
                    Object id = teacherModel.getValueAt(table2.getSelectedRow(), 0);
                    Object name = teacherModel.getValueAt(table2.getSelectedRow(), 3);
                    map.put("teacherId", id);
                    map.put("name", name);

                    courseModel.addRow(map);
                }

            }
        });


        addOneRowTeacherButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, Object> map = new HashMap<String, Object>();
                teacherModel.addRow(map);

            }
        });
        saveTeacherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                teacherModel.save();

            }
        });

        saveCourseButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                courseModel.save();

            }
        });
        deleteOneRowCourseButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (table1.getSelectedRow() > -1) {
                    courseModel.remove(table1.getSelectedRow());
                } else {
                    JOptionPane.showMessageDialog(frame, "请选择一行", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        deleteOneRowTeacherButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if ((table2.getSelectedRow() == 0)) {
                    JOptionPane.showMessageDialog(frame, "不允许删除默认行", "提示", JOptionPane.INFORMATION_MESSAGE);
                } else if ((table2.getSelectedRow() > -1)) {

                    teacherModel.remove(table2.getSelectedRow());
                } else {
                    JOptionPane.showMessageDialog(frame, "请选择一行", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        addOneStudentRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Map<String, Object> map = new HashMap<String, Object>();
                studentInfoModel.addRow(map);
            }
        });


        deleteOneStudentRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table3.getSelectedRow() > -1) {
                    studentInfoModel.removeStudentWithIndex(table3.getSelectedRow());

                } else {
                    JOptionPane.showMessageDialog(frame, "请选择一条记录", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        saveStudentTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studentInfoModel.saveStudentTable();

            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                frame.dispose();
                Session.userInfo = null;
            }
        });

        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table4.getSelectedRow() < 0) {
                    JOptionPane.showMessageDialog(frame, "请在课程表中选择一项", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                int courseId = Integer.parseInt(importedGradeModel.getValueAt(table4.getSelectedRow(), 0).toString().trim());
                importedGradeModel.cleanCommitStatusByCourseId(courseId);

                courseInfoModel.update();
            }
        });
        table5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (table5.getSelectedRow() < 0) {
                    return;
                }
                int courseId = Integer.parseInt(courseInfoModel.getValueAt(table5.getSelectedRow(), 0).toString().trim());
                gradeInputModel.setStudentByCourseId(courseId, false);
            }
        });


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//                private CourseStatisticModel courseStatisticModel;
//                private TimeStatisticModel timeStatisticModel;
//                private ClassStatisticModel classStatisticModel;
//                private StudentStatisticModel studentStatisticModel;

                courseStatisticModel.update();
                timeStatisticModel.update();
                classStatisticModel.update();
                studentStatisticModel.update();

            }
        });


        statisticButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tabbedPane2.getSelectedIndex();
                if (index == 0) { // 按课程查询
                    if (course.getSelectedRow() < 0) {
                        JOptionPane.showMessageDialog(frame, "请在项目中选择一行", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    String courseName = courseStatisticModel.getValueAt(course.getSelectedRow(), 0).toString();
                    Map<String, Object> map = courseStatisticModel.getData(courseName);
                    if (map.get("差") == "") {
                        JOptionPane.showMessageDialog(frame, "所选项目成绩没有记录", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    makePieChartByMap(map, "按课程(" + courseName + ")查询的统计图");

                } else if (index == 1) { // 按班级查询
                    if (studentClass.getSelectedRow() < 0) {
                        return;
                    }
                    int classCode = Integer.parseInt(classStatisticModel.getValueAt(studentClass.getSelectedRow(), 0).toString());
                    Map<String, Object> map = classStatisticModel.getData(classCode);
                    makePieChartByMap(map, "按班级(" + classCode + "班)查询的统计图");

                } else if (index == 2) {
                    // 按学生查询 通过学号
                    if (student.getSelectedRow() < 0) {
                        return;
                    }
                    String studentCode = studentStatisticModel.getValueAt(student.getSelectedRow(), 0).toString();
                    Map<String, Object> map = studentStatisticModel.getData(studentCode);
                    makePieChartByMap(map, "按学生(学号为:" + studentCode + ")查询的统计图");
                } else if (index == 3) {
                    // 按时间查询
                    if (time.getSelectedRow() < 0) {
                        return;
                    }
                    String academicQuery = timeStatisticModel.getValueAt(time.getSelectedRow(), 0).toString();
                    String termQuery = timeStatisticModel.getValueAt(time.getSelectedRow(), 1).toString();
                    Map<String, Object> map = timeStatisticModel.getData(academicQuery, termQuery);
                    makePieChartByMap(map, "按学年学期(" + academicQuery + "学年," + termQuery + "学期)查询的统计图");
                }

            }
        });

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(table2.getSelectedRow() > -1) || table2.getValueAt(table2.getSelectedRow(), 3) == null) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    Object id = 1;
                    Object name = "默认老师";
                    map.put("teacherId", id);
                    map.put("name", name);

                    courseModel.addRow(map);
                    return;
                } else {
                    Map<String, Object> map = new HashMap<String, Object>();
                    Object id = teacherModel.getValueAt(table2.getSelectedRow(), 0);
                    Object name = teacherModel.getValueAt(table2.getSelectedRow(), 3);
                    map.put("teacherId", id);
                    map.put("name", name);

                    courseModel.addRow(map);
                }

            }
        });
        chartButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tabbedPane2.getSelectedIndex();
                if (index == 0) { // 按课程查询
                    if (course.getSelectedRow() < 0) {
                        JOptionPane.showMessageDialog(frame, "请在项目中选择一行", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    String courseName = courseStatisticModel.getValueAt(course.getSelectedRow(), 0).toString();
                    Map<String, Object> map = courseStatisticModel.getData(courseName);
                    if (map.get("差") == "") {
                        JOptionPane.showMessageDialog(frame, "所选项目成绩没有记录", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    makeBarChartByMap(map, "按课程(" + courseName + ")查询的统计图");

                } else if (index == 1) { // 按班级查询
                    if (studentClass.getSelectedRow() < 0) {
                        return;
                    }
                    int classCode = Integer.parseInt(classStatisticModel.getValueAt(studentClass.getSelectedRow(), 0).toString());
                    Map<String, Object> map = classStatisticModel.getData(classCode);
                    makeBarChartByMap(map, "按班级(" + classCode + "班)查询的统计图");

                } else if (index == 2) {
                    // 按学生查询 通过学号
                    if (student.getSelectedRow() < 0) {
                        return;
                    }
                    String studentCode = studentStatisticModel.getValueAt(student.getSelectedRow(), 0).toString();
                    Map<String, Object> map = studentStatisticModel.getData(studentCode);
                    makeBarChartByMap(map, "按学生(学号为:" + studentCode + ")查询的统计图");
                } else if (index == 3) {
                    // 按时间查询
                    if (time.getSelectedRow() < 0) {
                        return;
                    }
                    String academicQuery = timeStatisticModel.getValueAt(time.getSelectedRow(), 0).toString();
                    String termQuery = timeStatisticModel.getValueAt(time.getSelectedRow(), 1).toString();
                    Map<String, Object> map = timeStatisticModel.getData(academicQuery, termQuery);
                    makeBarChartByMap(map, "按学年学期(" + academicQuery + "学年," + termQuery + "学期)查询的统计图");
                }

            }

        });

        pie3DchartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tabbedPane2.getSelectedIndex();
                if (index == 0) { // 按课程查询
                    if (course.getSelectedRow() < 0) {
                        JOptionPane.showMessageDialog(frame, "请在项目中选择一行", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    String courseName = courseStatisticModel.getValueAt(course.getSelectedRow(), 0).toString();
                    Map<String, Object> map = courseStatisticModel.getData(courseName);
                    if (map.get("差") == "") {
                        JOptionPane.showMessageDialog(frame, "所选项目成绩没有记录", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    make3DPieChartByMap(map, "按课程(" + courseName + ")查询的统计图");

                } else if (index == 1) { // 按班级查询
                    if (studentClass.getSelectedRow() < 0) {
                        return;
                    }
                    int classCode = Integer.parseInt(classStatisticModel.getValueAt(studentClass.getSelectedRow(), 0).toString());
                    Map<String, Object> map = classStatisticModel.getData(classCode);
                    make3DPieChartByMap(map, "按班级(" + classCode + "班)查询的统计图");

                } else if (index == 2) {
                    // 按学生查询 通过学号
                    if (student.getSelectedRow() < 0) {
                        return;
                    }
                    String studentCode = studentStatisticModel.getValueAt(student.getSelectedRow(), 0).toString();
                    Map<String, Object> map = studentStatisticModel.getData(studentCode);
                    make3DPieChartByMap(map, "按学生(学号为:" + studentCode + ")查询的统计图");
                } else if (index == 3) {
                    // 按时间查询
                    if (time.getSelectedRow() < 0) {
                        return;
                    }
                    String academicQuery = timeStatisticModel.getValueAt(time.getSelectedRow(), 0).toString();
                    String termQuery = timeStatisticModel.getValueAt(time.getSelectedRow(), 1).toString();
                    Map<String, Object> map = timeStatisticModel.getData(academicQuery, termQuery);
                    make3DPieChartByMap(map, "按学年学期(" + academicQuery + "学年," + termQuery + "学期)查询的统计图");
                }

            }
        });


        chart3DButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tabbedPane2.getSelectedIndex();
                if (index == 0) { // 按课程查询
                    if (course.getSelectedRow() < 0) {
                        JOptionPane.showMessageDialog(frame, "请在项目中选择一行", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    String courseName = courseStatisticModel.getValueAt(course.getSelectedRow(), 0).toString();
                    Map<String, Object> map = courseStatisticModel.getData(courseName);
                    if (map.get("差") == "") {
                        JOptionPane.showMessageDialog(frame, "所选项目成绩没有记录", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    make3DBarChartByMap(map, "按课程(" + courseName + ")查询的统计图");

                } else if (index == 1) { // 按班级查询
                    if (studentClass.getSelectedRow() < 0) {
                        return;
                    }
                    int classCode = Integer.parseInt(classStatisticModel.getValueAt(studentClass.getSelectedRow(), 0).toString());
                    Map<String, Object> map = classStatisticModel.getData(classCode);
                    make3DBarChartByMap(map, "按班级(" + classCode + "班)查询的统计图");

                } else if (index == 2) {
                    // 按学生查询 通过学号
                    if (student.getSelectedRow() < 0) {
                        return;
                    }
                    String studentCode = studentStatisticModel.getValueAt(student.getSelectedRow(), 0).toString();
                    Map<String, Object> map = studentStatisticModel.getData(studentCode);
                    make3DBarChartByMap(map, "按学生(学号为:" + studentCode + ")查询的统计图");
                } else if (index == 3) {
                    // 按时间查询
                    if (time.getSelectedRow() < 0) {
                        return;
                    }
                    String academicQuery = timeStatisticModel.getValueAt(time.getSelectedRow(), 0).toString();
                    String termQuery = timeStatisticModel.getValueAt(time.getSelectedRow(), 1).toString();
                    Map<String, Object> map = timeStatisticModel.getData(academicQuery, termQuery);
                    make3DBarChartByMap(map, "按学年学期(" + academicQuery + "学年," + termQuery + "学期)查询的统计图");
                }

            }

        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
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
        homeAdmin = new JPanel();
        homeAdmin.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        exitButton = new JButton();
        exitButton.setText("退出");
        homeAdmin.add(exitButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("欢迎管理员");
        homeAdmin.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameLabel = new JLabel();
        nameLabel.setText("张加胜");
        homeAdmin.add(nameLabel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        homeAdmin.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        tabbedPane1 = new JTabbedPane();
        homeAdmin.add(tabbedPane1, new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("课程/教师管理", panel1);
        addOneRowCourseButton = new JButton();
        addOneRowCourseButton.setText("添加一行");
        panel1.add(addOneRowCourseButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteOneRowCourseButton = new JButton();
        deleteOneRowCourseButton.setText("删除一行");
        panel1.add(deleteOneRowCourseButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addOneRowTeacherButton1 = new JButton();
        addOneRowTeacherButton1.setText("添加一行");
        panel1.add(addOneRowTeacherButton1, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteOneRowTeacherButton1 = new JButton();
        deleteOneRowTeacherButton1.setText("删除一行");
        panel1.add(deleteOneRowTeacherButton1, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveCourseButton = new JButton();
        saveCourseButton.setText("保存课程表");
        panel1.add(saveCourseButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setBorder(BorderFactory.createTitledBorder(null, "课程表", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        table1 = new JTable();
        table1.setBackground(new Color(-2034196));
        table1.setForeground(new Color(-12502992));
        scrollPane1.setViewportView(table1);
        final JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setForeground(new Color(-12502992));
        panel1.add(scrollPane2, new GridConstraints(3, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane2.setBorder(BorderFactory.createTitledBorder(null, "教师表", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        table2 = new JTable();
        table2.setBackground(new Color(-2034196));
        table2.setForeground(new Color(-12502992));
        scrollPane2.setViewportView(table2);
        insertButton = new JButton();
        insertButton.setText("单独添加课程");
        panel1.add(insertButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveTeacherButton = new JButton();
        saveTeacherButton.setText("保存教师表");
        panel1.add(saveTeacherButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("学生信息管理", panel2);
        addOneStudentRowButton = new JButton();
        addOneStudentRowButton.setText("添加一行");
        panel2.add(addOneStudentRowButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveStudentTableButton = new JButton();
        saveStudentTableButton.setText("保存学生表");
        panel2.add(saveStudentTableButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteOneStudentRowButton = new JButton();
        deleteOneStudentRowButton.setText("删除一行");
        panel2.add(deleteOneStudentRowButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        Font scrollPane3Font = this.$$$getFont$$$("Apple Chancery", Font.BOLD, 14, scrollPane3.getFont());
        if (scrollPane3Font != null) scrollPane3.setFont(scrollPane3Font);
        scrollPane3.setForeground(new Color(-12502992));
        panel2.add(scrollPane3, new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane3.setBorder(BorderFactory.createTitledBorder(null, "学生信息表", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        table3 = new JTable();
        table3.setBackground(new Color(-2034196));
        scrollPane3.setViewportView(table3);
        final JLabel label2 = new JLabel();
        label2.setForeground(new Color(-4489387));
        label2.setText("各字段请按照例子填写,如果保存不成功,检查输入,默认删除格式不正确数据。");
        panel2.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("成绩录入维护", panel3);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane4 = new JScrollPane();
        panel4.add(scrollPane4, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane4.setBorder(BorderFactory.createTitledBorder(null, "各课程成绩录入情况表", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        table4 = new JTable();
        table4.setBackground(new Color(-2034196));
        table4.setForeground(new Color(-12502992));
        scrollPane4.setViewportView(table4);
        cleanButton = new JButton();
        cleanButton.setText("清除录入或者暂存状态");
        panel4.add(cleanButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel4.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("成绩查询", panel5);
        final JSplitPane splitPane1 = new JSplitPane();
        panel5.add(splitPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPane1.setLeftComponent(panel6);
        final JScrollPane scrollPane5 = new JScrollPane();
        panel6.add(scrollPane5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(250, -1), null, null, 0, false));
        scrollPane5.setBorder(BorderFactory.createTitledBorder(null, "所有课程", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        table5 = new JTable();
        table5.setBackground(new Color(-2034196));
        table5.setForeground(new Color(-12502992));
        scrollPane5.setViewportView(table5);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPane1.setRightComponent(panel7);
        final JScrollPane scrollPane6 = new JScrollPane();
        panel7.add(scrollPane6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane6.setBorder(BorderFactory.createTitledBorder(null, "该课程学生成绩", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        table6 = new JTable();
        table6.setBackground(new Color(-2034196));
        table6.setForeground(new Color(-12502992));
        scrollPane6.setViewportView(table6);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("成绩统计", panel8);
        statisticButton = new JButton();
        statisticButton.setText("饼图");
        panel8.add(statisticButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tabbedPane2 = new JTabbedPane();
        tabbedPane2.setEnabled(true);
        tabbedPane2.setForeground(new Color(-12502992));
        panel8.add(tabbedPane2, new GridConstraints(1, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane2.addTab("按课程", panel9);
        final JScrollPane scrollPane7 = new JScrollPane();
        scrollPane7.setEnabled(true);
        panel9.add(scrollPane7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        course = new JTable();
        course.setBackground(new Color(-2034196));
        course.setEnabled(true);
        course.setForeground(new Color(-12502992));
        course.setUpdateSelectionOnSort(true);
        scrollPane7.setViewportView(course);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane2.addTab("按班级", panel10);
        final JScrollPane scrollPane8 = new JScrollPane();
        panel10.add(scrollPane8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        studentClass = new JTable();
        studentClass.setBackground(new Color(-2034196));
        studentClass.setForeground(new Color(-12502992));
        scrollPane8.setViewportView(studentClass);
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane2.addTab("按学生", panel11);
        final JScrollPane scrollPane9 = new JScrollPane();
        panel11.add(scrollPane9, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        student = new JTable();
        student.setBackground(new Color(-2034196));
        student.setForeground(new Color(-12502992));
        scrollPane9.setViewportView(student);
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane2.addTab("按时间", panel12);
        final JScrollPane scrollPane10 = new JScrollPane();
        scrollPane10.setForeground(new Color(-12502992));
        panel12.add(scrollPane10, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        time = new JTable();
        time.setBackground(new Color(-2034196));
        scrollPane10.setViewportView(time);
        updateButton = new JButton();
        updateButton.setText("更新数据");
        panel8.add(updateButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chartButton = new JButton();
        chartButton.setText("条形图");
        panel8.add(chartButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pie3DchartButton = new JButton();
        pie3DchartButton.setText("3D饼图");
        panel8.add(pie3DchartButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chart3DButton = new JButton();
        chart3DButton.setText("3D条形图");
        panel8.add(chart3DButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return homeAdmin;
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


    public class BarExample1 extends ApplicationFrame {
        /**
         * Creates a new demo instance.
         *
         * @param title the frame title.
         */
        public BarExample1(Map<String, Object> map, String title) {
            super("条形图");
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

//
            dataset.addValue(Integer.parseInt(map.get("优").toString()), "优（90-100）", "Column 1");
            dataset.addValue(Integer.parseInt(map.get("良").toString()), "良（80-90）", "Column 1");
            dataset.addValue(Integer.parseInt(map.get("中").toString()), "中（70-80）", "Column 1");
            dataset.addValue(Integer.parseInt(map.get("差").toString()), "差（60-70）", "Column 1");
            dataset.addValue(Integer.parseInt(map.get("不及格").toString()), "不及格（<60）", "Column 1");

            StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
//设置标题字体
            standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
//设置图例的字体
            standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
//设置轴向的字体
            standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));
//应用主题样式
            ChartFactory.setChartTheme(standardChartTheme);
            JFreeChart chart = ChartFactory.createBarChart(
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

        /**
         * Starting point for the demonstration application.
         *
         * @param args ignored.
         */


    }

    public class BarExample2 extends ApplicationFrame {
        /**
         * Creates a new demo instance.
         *
         * @param title the frame title.
         */
        public BarExample2(Map<String, Object> map, String title) {
            super("条形图");
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

//        dpd.setValue("良（80-90）", Integer.parseInt(map.get("良").toString()));
//        dpd.setValue("中（70-80）", Integer.parseInt(map.get("中").toString()));
//        dpd.setValue("差（60-70）", Integer.parseInt(map.get("差").toString()));
//        dpd.setValue("不及格（<60）", Integer.parseInt(map.get("不及格").toString()));
            float average_class_score = 0;
            float class_score[] = {0, 1};
            String class_name[] = {"0", "1"};

            for (int i = 0; i < class_score.length; i++) {
                System.out.println(class_score[i] + " ");
                dataset.addValue(class_score[i], class_name[i], "Column 1");
            }
            dataset.addValue(average_class_score, "全校平均成绩", "Column 1");


            JFreeChart chart = ChartFactory.createBarChart(
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

        /**
         * Starting point for the demonstration application.
         *
         * @param args ignored.
         */


    }

    public void makePieChartByMap(Map<String, Object> map, String title) {
        DefaultPieDataset dpd = new DefaultPieDataset(); //建立一个默认的饼图
        System.out.println(map);

        dpd.setValue("优（90-100）", Integer.parseInt(map.get("优").toString()));
        dpd.setValue("良（80-90）", Integer.parseInt(map.get("良").toString()));
        dpd.setValue("中（70-80）", Integer.parseInt(map.get("中").toString()));
        dpd.setValue("差（60-70）", Integer.parseInt(map.get("差").toString()));
        dpd.setValue("不及格（<60）", Integer.parseInt(map.get("不及格").toString()));
        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
//设置标题字体
        standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
//设置图例的字体
        standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
//设置轴向的字体
        standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));
//应用主题样式
        ChartFactory.setChartTheme(standardChartTheme);
        JFreeChart chart = ChartFactory.createPieChart(title, dpd, true, true, false);
        PiePlot piePlot = (PiePlot) chart.getPlot();
        piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator(("{0}:({2})"), NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
        //可以查具体的API文档,第一个参数是标题，第二个参数是一个数据集，第三个参数表示是否显示Legend，第四个参数表示是否显示提示，第五个参数表示图中是否存在URL
        ChartFrame chartFrame = new ChartFrame(title, chart);
        //chart要放在Java容器组件中，ChartFrame继承自java的Jframe类。该第一个参数的数据是放在窗口左上角的，不是正中间的标题。
        chartFrame.pack(); //以合适的大小展现图形
        chartFrame.setVisible(true);//图形是否可见
    }

    public static void make3DPieChartByMap(Map<String, Object> map, String title) {
        DefaultPieDataset dpd = new DefaultPieDataset(); //建立一个默认的饼图
        System.out.println(map);

        dpd.setValue("优（90-100）", Integer.parseInt(map.get("优").toString()));
        dpd.setValue("良（80-90）", Integer.parseInt(map.get("良").toString()));
        dpd.setValue("中（70-80）", Integer.parseInt(map.get("中").toString()));
        dpd.setValue("差（60-70）", Integer.parseInt(map.get("差").toString()));
        dpd.setValue("不及格（<60）", Integer.parseInt(map.get("不及格").toString()));
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

    public void make3DBarChartByMap(Map<String, Object> map, String title) {

        BarExample3D demo = new BarExample3D(map, title);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }


    public void makeBarChartByMap(Map<String, Object> map, String title) {


        BarExample1 demo = new BarExample1(map, title);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }


    private class TeacherModel extends AbstractTableModel {

        TeacherUtils teacherUtils = new TeacherUtils();
        List<Map<String, Object>> list = teacherUtils.findAllTeachers();

        String[] tableStrings = {"id", "username", "password", "name", "phone", "email"};
        String[] showStrings = {"编号", "用户名", "密码", "教师姓名", "电话号码(可为空)", "电子邮箱(可为空)"};


        @Override
        public int getRowCount() {
            return list.size();
        }

        @Override
        public int getColumnCount() {
            return tableStrings.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Map<String, Object> map = list.get(rowIndex);
            return map.get(tableStrings[columnIndex]);
        }


        @Override
        public String getColumnName(int column) {
            return showStrings[column];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex != 0;
        }

        public void addRow(Map<String, Object> row) {
            list.add(row);
            fireTableDataChanged();
        }

        public void save() {
            for (int i = 0; i < list.size(); i++) {
                boolean flag = teacherUtils.saveTeacher(list.get(i));
            }
            // 更新当前数据源
            list = teacherUtils.findAllTeachers();
            fireTableDataChanged();

        }

        public void remove(int rowIndex) {
            Map<String, Object> map = list.get(rowIndex);
            if (map.containsKey("id")) {
                teacherUtils.removeTeacher(Integer.parseInt(map.get("id").toString()));
            }
            list.remove(rowIndex);
            fireTableDataChanged();

        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Map<String, Object> map = list.get(rowIndex);
            map.put(tableStrings[columnIndex], aValue);
        }


    }


    /**
     * 私有内部类
     */
    private class CourseModel extends AbstractTableModel {

        CourseUtils courseUtils = new CourseUtils();
        List<Map<String, Object>> list = courseUtils.findAllCourse();

        String[] tableStrings = {"id", "courseName", "academicYear", "term", "teacherId"};
        String[] showStrings = {"编号", "课程名", "学年", "学期", "授课教师编号"};

        public void addRow(Map<String, Object> row) {
            list.add(row);
            fireTableDataChanged();
        }


        public void save() {

            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                courseUtils.saveCourse(map);
            }
            list = courseUtils.findAllCourse();
            fireTableDataChanged();

        }


        public void remove(int rowIndex) {
            Map<String, Object> map = list.get(rowIndex);
            if (map.containsKey("id")) {
                courseUtils.removeCourse(Integer.parseInt(map.get("id").toString()));
            }
            list.remove(rowIndex);
            fireTableDataChanged();

        }


        @Override
        public int getRowCount() {

            return list.size();
        }

        @Override
        public int getColumnCount() {
            return tableStrings.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Map<String, Object> map = list.get(rowIndex);
            return map.get(tableStrings[columnIndex]);

        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex != 0;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Map<String, Object> map = list.get(rowIndex);
            map.put(tableStrings[columnIndex], aValue);
        }

        @Override
        public String getColumnName(int column) {
            return showStrings[column];
        }

    }

    private class StudentInfoModel extends AbstractTableModel {

        private StudentInfoUtils studentInfoUtils = new StudentInfoUtils();
        private List<Map<String, Object>> list = getAllStudentsInfo();

        public List<Map<String, Object>> getAllStudentsInfo() {
            return studentInfoUtils.getAllStudentsInfo();
        }

        String[] columnStrings = {"id", "username", "password", "studentCode", "name", "studentClass", "age", "sex", "birthday", "address", "phone", "email"};
        String[] columnShowStrings = {"编号", "用户名", "密码", "学号", "姓名", "班级", "年龄", "性别", "生日", "地址", "电话", "邮箱"};

        public void addRow(Map<String, Object> map) {
            list.add(map);
            fireTableDataChanged();

        }

        public void saveStudentTable() {

            for (int i = 0; i < list.size(); i++) {
                studentInfoUtils.saveStudentInfoByMap(list.get(i));
            }

            list = getAllStudentsInfo();
            fireTableDataChanged();

        }

        public void removeStudentWithIndex(int index) {
            Map<String, Object> map = list.get(index);
            if (map.containsKey("id")) {
                boolean flag = studentInfoUtils.deleteStudentById(Integer.parseInt(map.get("id").toString().trim()));
                if (flag == true) {
                    list.remove(index);
                }
            } else {
                list.remove(index);
            }
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
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
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Map<String, Object> map = list.get(rowIndex);
            map.put(columnStrings[columnIndex], aValue);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex != 0;
        }

        @Override
        public String getColumnName(int column) {
            return columnShowStrings[column];
        }

    }

    private class ImportedGradeModel extends AbstractTableModel {

        private CourseUtils courseUtils = new CourseUtils();
        private List<Map<String, Object>> list = getAllCourseInfo();

        String[] columnStrings = {"id", "courseName", "academicYear", "term", "name", "commitStatus"};
        String[] columnShowingStrings = {"编号", "课程", "学年", "学期", "姓名", "提交状态"};

        public void updateTable() {
            list = getAllCourseInfo();
        }

        public void cleanCommitStatusByCourseId(int id) {
            courseUtils.clearCommitStautsByCourseId(id);

            updateTable();
            fireTableDataChanged();

        }

        private List<Map<String, Object>> getAllCourseInfo() {
            return courseUtils.findAllCourse();
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


    /**
     * 课程信息模型
     */
    private class CourseInfoModel extends AbstractTableModel {


        private CourseUtils courseUtils = new CourseUtils();
        private List<Map<String, Object>> list = getAllCourses();


        String[] columnStrings = {"id", "courseName", "academicYear", "term", "commitStatus"};
        String[] columnShowStrings = {"编号", "课程名", "所属学年", "学期", "提交状态"};


        public void update() {
            list = getAllCourses();
            fireTableDataChanged();

        }

        private List<Map<String, Object>> getAllCourses() {
            return courseUtils.findAllCourse();
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


    /**
     * 课程成绩
     */
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

            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                gradeUtils.saveGrade(map);
                courseUtils.commitCourseByCourseId(courseId);

            }
            return true;


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
            return false;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Map<String, Object> map = list.get(rowIndex);
            map.put(columnStrings[columnIndex], aValue);

        }


    }

    static class CourseStatisticModel extends AbstractTableModel {


        private StatisticUtils statisticUtils = new StatisticUtils();
        private List<Map<String, Object>> list = statisticUtils.getAllCourses();
        String[] columnStrings = {"courseName"};
        String[] columnShowStrings = {"课程"};


        Map<String, Object> getData(String courseName) {

            return statisticUtils.getCourseStatisticalByCourseName(courseName).get(0);
        }

        public void update() {
            list = statisticUtils.getAllCourses();
            fireTableDataChanged();

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
    }

    private class StudentStatisticModel extends AbstractTableModel {


        private StatisticUtils statisticUtils = new StatisticUtils();
        private List<Map<String, Object>> list = statisticUtils.getAllStudents();
        String[] columnStrings = {"studentCode", "name"};
        String[] columnShowStrings = {"学号", "姓名"};


        private Map<String, Object> getData(String studentCode) {

            return statisticUtils.getStudentStatisticalByStudentCode(studentCode).get(0);
        }

        public void update() {
            list = statisticUtils.getAllStudents();
            fireTableDataChanged();

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
    }

    private class ClassStatisticModel extends AbstractTableModel {


        private StatisticUtils statisticUtils = new StatisticUtils();
        private List<Map<String, Object>> list = statisticUtils.getAllClasses();
        String[] columnStrings = {"studentClass"};
        String[] columnShowStrings = {"班级"};


        private Map<String, Object> getData(int studentClass) {

            return statisticUtils.getClassStatisticalByClass(studentClass).get(0);
        }

        public void update() {
            list = statisticUtils.getAllClasses();
            fireTableDataChanged();

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
    }


    private class TimeStatisticModel extends AbstractTableModel {


        private StatisticUtils statisticUtils = new StatisticUtils();
        private List<Map<String, Object>> list = statisticUtils.getAllTimes();
        String[] columnStrings = {"academicYear", "term"};
        String[] columnShowStrings = {"学年", "学期"};

        private Map<String, Object> getData(String academicYear, String term) {

            return statisticUtils.getTimeStatisticalTime(academicYear, term).get(0);
        }

        public void update() {
            list = statisticUtils.getAllTimes();
            fireTableDataChanged();

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
    }


}

