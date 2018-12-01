package utils;

import beans.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseUtils {

    private JdbcUtils jdbcUtils;

    public CourseUtils() {
        jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
    }

    // Add course;
    public String addCourse(Course course) {
        // Default result
        String resultStr = "Fail to add course";
        String sql;

        if (course != null) {
            String num = course.getNum();
            String name = course.getName();
            String section = course.getSection();
            String semester = course.getSemester();
            int year = course.getYear();

            sql = "insert into courses(num,name,section,semester,year) values (?,?,?,?,?)";

            List<Object> params = new ArrayList<>();
            params.add(num);
            params.add(name);
            params.add(section);
            params.add(semester);
            params.add(year);

            try {
                boolean addSuccessfully = jdbcUtils.updateByPreparedStatement(sql, params);
                if (addSuccessfully) {
                    resultStr = "Successfully add course";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultStr;
    }


    // TODO: import course from template

    // View all courses
    public List<Course> viewAllCourse() {
        String sql = "select * from courses";
        try {
            List<Course> findAll = jdbcUtils.findMoreRefResult(sql, null, Course.class);
            if (findAll != null) {
                return findAll;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // View certain course
    public Course viewCertainCourse(int courseID) {
        Course course = new Course();
        String sql = "select * from course where courseID = ?";

        return course;
    }


    public String updateCourse(Course course) {
        String resultStr = "Fail to update course";

        return resultStr;
    }

    public String deleteCourse(int courseID) {
        String resultStr = "Fail to remove course";

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
