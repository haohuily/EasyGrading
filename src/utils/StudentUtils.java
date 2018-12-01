package utils;

import beans.Course;
import beans.Grade;
import beans.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentUtils {
    private JdbcUtils jdbcUtils;

    public StudentUtils() {
        jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
    }

    // Add course;
    public String addStudent(Student student) {
        // Default result
        String resultStr = "Fail to add student";
        String sql;

        if (student != null) {
            String BUID = student.getBUID();
            String name = student.getName();
            String stand = student.getStand();

            sql = "insert into students(BUID,name,stand) values (?,?,?)";

            List<Object> params = new ArrayList<>();
            params.add(BUID);
            params.add(name);
            params.add(stand);

            try {
                boolean addSuccessfully = jdbcUtils.updateByPreparedStatement(sql, params);
                if (addSuccessfully) {
                    resultStr = "Successfully add student";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultStr;
    }





    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (jdbcUtils != null) {
            jdbcUtils.releaseConn();
            jdbcUtils = null;
        }

        System.out.println(this.getClass().toString() + "destroyed.");
    }

}
