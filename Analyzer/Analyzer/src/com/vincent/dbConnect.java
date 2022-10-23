package com.vincent;

import java.sql.*;

/**
 *
 * @author sqlitetutorial.net
 */

public class dbConnect {
    /**
     * Connect to a sample database
     */

    public static Boolean connect() {

        try {
            // db parameters
            String url = "jdbc:sqlite:C:\\Users\\vjlombardo\\Desktop\\Java\\Text_Data\\Analyzer\\Analyzer\\TestWord.db";
            // create a connection to the database
            Connection conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            SqlMod.setConn(conn);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
