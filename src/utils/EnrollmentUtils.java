package utils;

import beans.Course;
import beans.Student;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentUtils {
    private JdbcUtils jdbcUtils;

    public EnrollmentUtils() {
        jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
    }


    public List<Student> searchAllStudent(int courseID) {
        String sql = "select students.id, BUID, name, stand from students, enrollments where students.id = enrollments.studentID and courseID = ?";
        List<Object> params = new ArrayList<>();
        params.add(courseID);

        try {
            return jdbcUtils.findMoreRefResult(sql, params, Student.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
