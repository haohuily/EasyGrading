package utils;

import beans.Admin;

import java.util.ArrayList;
import java.util.List;


public class LoginUtils {

    private JdbcUtils jdbcUtils;

    public LoginUtils() {
        jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
    }

    // Login
    public String login(Admin admin) {
        // Default result
        String resultStr = "Fail to login";
        String sql;

        if (admin != null) {
            String username = admin.getUsername();
            String password = admin.getPassword();
            sql = "select * from admins where username = ? and password = ?";

            List<Object> params = new ArrayList<>();
            params.add(username);
            params.add(password);
            try {
                Admin databaseAdmin = jdbcUtils.findSimpleRefResult(sql, params, Admin.class);
                if (databaseAdmin != null) {
                    resultStr = "Successfully login";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultStr;
    }

    // Register
    public String register(Admin admin) {
        // Default result
        String resultStr = "Fail to register";
        String sql;

        if (admin != null) {
            String username = admin.getUsername();
            String password = admin.getPassword();
            sql = "insert into admins(username,password) values (?,?)";

            List<Object> params = new ArrayList<>();
            params.add(username);
            params.add(password);
            try {
                Boolean updated = jdbcUtils.updateByPreparedStatement(sql, params);
                if (updated != false) {
                    resultStr = "Registered";
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
