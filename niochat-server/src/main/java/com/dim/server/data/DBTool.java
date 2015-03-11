/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dim.server.data;

import com.dim.server.msg.LoginMessage;
import com.dim.server.msg.RegisterMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author yanming_dai
 */
public class DBTool {

    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(DBTool.class.getName());
    private static String URI = "jdbc:mysql://172.16.7.80:3306/test";
    private static String account;
    private static String pwd;
    static Connection connection = null;
    static PreparedStatement stmt = null;
    static ResultSet rset = null;

    //初始化链接
    public static void setup(String uri, String userName, String password) {
        if (StringUtils.isNoneBlank(uri)) {
            URI = uri;
        }
        if (StringUtils.isNotBlank(userName)) {
            account = userName;
        }
        if (StringUtils.isNotBlank(password)) {
            pwd = password;
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.catching(e);
        }

        try {
            logger.info("Creating connection.");
            connection = java.sql.DriverManager.getConnection(URI, "root", "");
        } catch (SQLException e) {
            logger.catching(e);
        } finally {
            try {
                if (rset != null) {
                    rset.close();
                }
            } catch (Exception e) {
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * register business
     *
     * @param msg
     * @return
     */
    public static boolean register(RegisterMessage msg) {
        if (connection == null) {
            setup(null, null, null);
        }
        int rst = 0;
        try {
            String sql = "insert into c_user(account,c_password)values(?,?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, msg.getAccount());
            stmt.setString(2, msg.getPassword());
            rst = stmt.executeUpdate();
            logger.info("register complete successful.");
        } catch (Exception e) {
            logger.catching(e);
        }
        return rst > 0;
    }

    /**
     * login request 
     * @param request
     * @return 
     */
    public static LoginMessage login(LoginMessage request) {
        if (connection == null) {
            setup(null, null, null);
        }
        ResultSet rst = null;
        try {
            String sql = "select * from c_user where account= ? and c_password=?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, request.getAccount());
            stmt.setString(2, request.getPassword());
            rst = stmt.executeQuery();
            
            logger.info("register complete successful.");
            
            LoginMessage msg=new LoginMessage();
            while (rst.next()) {
                String act=rst.getString(1);
                msg.setAccount(act);
                String password=rst.getString(2);
                msg.setPassword(password);
            }
            return msg;
        } catch (Exception e) {
            logger.catching(e);
        }

        return null;
    }
}
