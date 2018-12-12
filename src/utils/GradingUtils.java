package utils;

import beans.Grade;
import beans.Student;

import java.util.ArrayList;
import java.util.List;

public class GradingUtils {
    private JdbcUtils jdbcUtils;

    public GradingUtils() {
        jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
    }


    public void insertGradeForStudent(int studentID, int componentID, Double grade, Double curve, String comments) {
//        String sql = "insert into grades(studentID,componentID,studentScore,studentCurve,comments) values (?,?,?,?,?)";
        String sql = "insert into grades(studentID,componentID,studentScore,studentCurve,comments) values (?,?,?,?,?) on duplicate key update studentScore=?,studentCurve=?,comments =?";

        List<Object> params = new ArrayList<>();
        params.add(studentID);
        params.add(componentID);
        params.add(grade);
        params.add(curve);
        params.add(comments);
        params.add(grade);
        params.add(curve);
        params.add(comments);


        try {
            jdbcUtils.updateByPreparedStatement(sql, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Grade findCertainGrade(int studentID, int componentID) {
        String sql = "select * from grades where studentID = ? and componentID = ?";
        List<Object> params = new ArrayList<>();
        params.add(studentID);
        params.add(componentID);

        try {
            return jdbcUtils.findSimpleRefResult(sql, params, Grade.class);

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
