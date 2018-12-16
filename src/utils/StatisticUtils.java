package utils;

import beans.Statistic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatisticUtils {
    private JdbcUtils jdbcUtils;

    public StatisticUtils() {
        jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
    }


    public void insertStatistic(int studentID, int componentID, Double curvedGrade) {
        String sql = "insert into statistics(studentID,componentID,curvedGrade) values (?,?,?)";

        List<Object> params = new ArrayList<>();
        params.add(studentID);
        params.add(componentID);
        params.add(curvedGrade);

        try {
            jdbcUtils.updateByPreparedStatement(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Statistic searchCertainStatistic(int studentID, int componentID) {
        String sql = "select * from statistics where studentID=? and componentID=?";

        List<Object> params = new ArrayList<>();
        params.add(studentID);
        params.add(componentID);


        try {
            return jdbcUtils.findSimpleRefResult(sql, params, Statistic.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateStatistic(int id, Double curvedGrade) {
        String sql = "update statistics set curvedGrade=? where id=?";

        List<Object> params = new ArrayList<>();
        params.add(curvedGrade);
        params.add(id);


        try {
            jdbcUtils.updateByPreparedStatement(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Map<String, Object> AVG(int courseID, int componentID) {
        String sql = "select avg(curvedGrade) from statistics, components where components.courseID=? and statistics.componentID=?";

        List<Object> params = new ArrayList<>();
        params.add(courseID);
        params.add(componentID);

        try {
            return jdbcUtils.findSimpleResult(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> MAX(int courseID, int componentID) {
        String sql = "select max(curvedGrade) from statistics, components where components.courseID=? and statistics.componentID=?";

        List<Object> params = new ArrayList<>();
        params.add(courseID);
        params.add(componentID);

        try {
            return jdbcUtils.findSimpleResult(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> MIN(int courseID, int componentID) {
        String sql = "select min(curvedGrade) from statistics, components where components.courseID=? and statistics.componentID=?";

        List<Object> params = new ArrayList<>();
        params.add(courseID);
        params.add(componentID);

        try {
            return jdbcUtils.findSimpleResult(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Map<String, Object>> All(int courseID, int componentID) {
        String sql = "select curvedGrade from statistics, components where components.courseID=? and statistics.componentID=?";

        List<Object> params = new ArrayList<>();
        params.add(courseID);
        params.add(componentID);

        try {
            return jdbcUtils.findMoreResult(sql, params);
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
