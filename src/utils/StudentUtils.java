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
    public String addStudent(Student student, int courseID) {
        // Default result
        String resultStr = "Fail to add student";
        String sql;
        int studentID = 0;

        if (student != null) {
            String BUID = student.getBUID();
            String name = student.getName();
            String stand = student.getStand();



            // add to students table
            List<Object> params = new ArrayList<>();
            params.add(BUID);
            params.add(name);
            params.add(stand);
            try {
                boolean addSuccessfully = jdbcUtils.updateByPreparedStatement("insert into students(BUID,name,stand) values (?,?,?)", params);
                if (addSuccessfully) {
                    resultStr = "Successfully add student";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            // find studentID
            List<Object> params2 = new ArrayList<>();
            params2.add(BUID);
            try {
                studentID = jdbcUtils.findSimpleRefResult("select * from students where BUID = ?", params2, Student.class).getId();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // add to enrollments table
            List<Object> params3 = new ArrayList<>();
            params3.add(studentID);
            params3.add(courseID);
            try {

                jdbcUtils.updateByPreparedStatement("insert into enrollments(studentID, courseID) values (?,?)", params3);
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
