package utils;

import beans.Admin;
import beans.Component;

import java.util.ArrayList;
import java.util.List;

public class ComponentUtils {

    private JdbcUtils jdbcUtils;

    public ComponentUtils() {
        jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
    }


    public String addComponent(int courseID, String name, String type, Double graduateWeight, Double undergraduateWeight, Double totalScore, Double globalCurve, String comments) {
        // Default result
        String resultStr = "Fail to add component";
        String sql = "insert into components(courseID,name,type,graduateWeight,undergraduateWeight,totalScore,globalCurve,comments) values (?,?,?,?,?,?,?,?)";

        List<Object> params = new ArrayList<>();
        params.add(courseID);
        params.add(name);
        params.add(type);
        params.add(graduateWeight);
        params.add(undergraduateWeight);
        params.add(totalScore);
        params.add(globalCurve);
        params.add(comments);

        try {
            boolean addSuccessfully = jdbcUtils.updateByPreparedStatement(sql, params);
            if (addSuccessfully) {
                resultStr = "Successfully add component";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }

    public List<Component> searchAllComponents(int courseID) {
        List<Component> components = new ArrayList<>();
//        String sql = "select name,type,graduateWeight,undergraduateWeight,totalScore from components where courseID = ?";

        String sql = "select * from components where courseID = ?";
        List<Object> params = new ArrayList<>();
        params.add(courseID);

        try {
            components = jdbcUtils.findMoreRefResult(sql, params, Component.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return components;
    }

    public String deleteComponents(int componentID) {
        String resultStr = "Fail to delete component";
        String sql = "delete from components where id=?";

        List<Object> params = new ArrayList<>();
        params.add(componentID);

        try {
            jdbcUtils.updateByPreparedStatement(sql, params);
            resultStr = "Successfully delete component";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }

//    public List<Component> searchAllTypes(int courseID) {
//        List<Component> components = new ArrayList<>();
//        String sql = "select distinct type from components where courseID = ?";
//
//        List<Object> params = new ArrayList<>();
//        params.add(courseID);
//        try {
//            components = jdbcUtils.findMoreRefResult(sql, params, Component.class);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return components;
//    }


    public Component searchCertainComponent(int courseID, String name) {
        String sql = "select * from components where courseID = ? and name = ?";

        List<Object> params = new ArrayList<>();
        params.add(courseID);
        params.add(name);

        try {
            return jdbcUtils.findSimpleRefResult(sql, params, Component.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Component searchCertainComponentByID(int id) {
        String sql = "select * from components where id=?";

        List<Object> params = new ArrayList<>();
        params.add(id);
        
        try {
            return jdbcUtils.findSimpleRefResult(sql, params, Component.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean updateCertainComponent(int componentID, String name, String type, Double graduateWeight, Double undergraduateWeight, Double totalScore, String comments) {
        String sql = "update components set name=?,type=?,graduateWeight=?,undergraduateWeight=?,totalScore=?,comments=? where id=?";

        List<Object> params = new ArrayList<>();

        params.add(name);
        params.add(type);
        params.add(graduateWeight);
        params.add(undergraduateWeight);
        params.add(totalScore);
        params.add(comments);
        params.add(componentID);

        try {
            return jdbcUtils.updateByPreparedStatement(sql, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public void updateGlobalCurve(int componentID, Double curve) {
        String sql = "update components set globalCurve = ? where id = ?";

        List<Object> params = new ArrayList<>();
        params.add(curve);
        params.add(componentID);

        try {
            jdbcUtils.updateByPreparedStatement(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
