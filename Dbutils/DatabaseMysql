/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pers.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseMysql {
   private static final String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=true";
    private static final String driver = "com.mysql.jdbc.Driver";
    private Connection conn = null;
    private Statement stm = null;
    private ResultSet rs = null;

    public DatabaseMysql() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "root", "13545512858cw");
            stm = conn.createStatement();
        } catch (Exception Exception) {
            Logger.getLogger(DatabaseMysql.class.getName()).log(Level.SEVERE, null, Exception);
        }
    }

    public ResultSet query(String sql) {
        try {
            rs = stm.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public boolean update(String sql) {
        boolean b = false;
        try {
            stm.execute(sql);
            b = true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }


    public boolean isResultSetNull() {
        try {
            if (rs != null && rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
