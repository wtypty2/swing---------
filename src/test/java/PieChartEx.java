////import org.jfree.chart.ChartFactory;
////import org.jfree.chart.ChartPanel;
////import org.jfree.chart.JFreeChart;
////import org.jfree.data.category.DefaultCategoryDataset;
////import org.jfree.data.general.DefaultPieDataset;
////
////import javax.swing.*;
////import java.awt.*;
////import java.util.List;
////import java.util.Map;
////
////public class PieChartEx extends JFrame {
////
////    public PieChartEx() {
////
////        initUI();
////    }
////
////    private void initUI() {
////
////        DefaultPieDataset dataset = createDataset();
////
////        JFreeChart chart = createChart(dataset);
////        ChartPanel chartPanel = new ChartPanel(chart);
////        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
////        chartPanel.setBackground(Color.white);
////        add(chartPanel);
////
////        pack();
////        setTitle("Pie chart");
////        setLocationRelativeTo(null);
////        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////    }
////
////    private DefaultPieDataset createDataset() {
////
////        var dataset = new DefaultPieDataset();
////        dataset.setValue("Apache", 52);
////        dataset.setValue("Nginx", 31);
////        dataset.setValue("IIS", 12);
////        dataset.setValue("LiteSpeed", 2);
////        dataset.setValue("Google server", 1);
////        dataset.setValue("Others", 2);
////
////        return dataset;
////    }
////
////    private JFreeChart createChart(DefaultPieDataset dataset) {
////
////        JFreeChart pieChart = ChartFactory.createPieChart(
////                "Web servers market share",
////                dataset,
////                false, true, false);
////
////        return pieChart;
////    }
////
////    public static void main(String[] args) {
////
////        EventQueue.invokeLater(() -> {
////
////            var ex = new PieChartEx();
////            ex.setVisible(true);
////        });
////    }
////}
////    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
////    List<Map<String, Object>>list= getGradesByStudentId()
////    for(int x : list ){
////              int score=x.get("score")
////              String courseName=x.get("courseName")
////              dataset.addValue(Integer.parseInt(score.toString()), "courseName", "Column 1");
////
////              }
////int verygood=0;
////            int good=0;
////            int normal=0;
////            int bad=0;
////            int shit=0;
////            for(int x : list ){
////            int score=x.get("score")
////            if(score>90){verygood=verygood+1}
////            else if(score>80){good=good+1}
////            else if(score>70){normal=normal1}
////            else if(score>60){bad=bad+1}
////            else{shit=shit+1}
////            }
////
////
////
////
////
////            dpd.setValue("优（90-100）",verygood );
////            dpd.setValue("良（80-90）", good);
////            dpd.setValue("中（70-80）", normal);
////            dpd.setValue("差（60-70）", bad);
////            dpd.setValue("不及格（<60）", shi
////            t);
////
////            Student student = (Student) Session.userInfo;
////
////
////
////public void makePieChartByMap(Map<String, Object> map, String title) {
////        DefaultPieDataset dpd = new DefaultPieDataset(); //建立一个默认的饼图
////        GradeUtils gradeUtils = new GradeUtils();
////        Student student = (Student) Session.userInfo;
////        List<Map<String, Object>> List = gradeUtils.getGradesByStudentId(student.getId());
////
////
////        int verygood=0;
////        int good=0;
////        int normal=0;
////        int bad=0;
////        int shit=0;
////        for(int x : List ){
////        int score=x.get("score")
////        if(score>90){verygood=verygood+1}
////        else if(score>80){good=good+1}
////        else if(score>70){normal=normal1}
////        else if(score>60){bad=bad+1}
////        else{shit=shit+1}
////        }
////        dpd.setValue("优（90-100）",verygood );
////        dpd.setValue("良（80-90）", good);
////        dpd.setValue("中（70-80）", normal);
////        dpd.setValue("差（60-70）", bad);
////        dpd.setValue("不及格（<60）", shit);
////
////
////        JFreeChart chart = ChartFactory.createPieChart(title, dpd, true, true, false);
////        PiePlot piePlot = (PiePlot) chart.getPlot();
////        piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator(("{0}:({2})"), NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
////        //可以查具体的API文档,第一个参数是标题，第二个参数是一个数据集，第三个参数表示是否显示Legend，第四个参数表示是否显示提示，第五个参数表示图中是否存在URL
////        ChartFrame chartFrame = new ChartFrame(title, chart);
////        //chart要放在Java容器组件中，ChartFrame继承自java的Jframe类。该第一个参数的数据是放在窗口左上角的，不是正中间的标题。
////        chartFrame.pack(); //以合适的大小展现图形
////        chartFrame.setVisible(true);//图形是否可见
////        }
//
//
//
// GradeUtils gradeUtils = new GradeUtils();
//         Student student = (Student) Session.userInfo;
//         List<Map<String, Object>> List = gradeUtils.getGradesByStudentId(student.getId());
//        for(Map<String, Object> x : List ){
//        int score= (int) x.get("score");
//        String courseName= (String) x.get("courseName");
//        Bdataset.addValue(score, courseName, "Column 1");
//
//        }