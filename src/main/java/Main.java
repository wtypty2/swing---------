import com.github.weisj.darklaf.LafManager;
import com.njupt.sms.ui.Login;

public class Main {

    public static void main(String[] args) {
        LafManager.install();

        new Login();

        //new HomeTeacher();
//        CourseUtils courseUtils = new CourseUtils();
//        List<Map<String, Object>> list = courseUtils.findAllCourse();
//
//        JdbcUtils jdbcUtils = new JdbcUtils();
//        jdbcUtils.getConnection();
//
//        Date date = new Date();

        /*String sql = "insert into student(username,password,studentCode,name,birthday) values(?,?,?,?,?)";
        List<Object> params = new ArrayList<>();
        params.add("zhangsan");
        params.add("123");
        params.add("12001111");
        params.add("xxx");
        params.add(date);
        try {
            boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);
            System.out.println(flag);

        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }


}
