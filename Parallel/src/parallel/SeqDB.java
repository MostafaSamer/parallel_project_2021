/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallel;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mosta
 */
public class SeqDB {
    
    public static Connection connectDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\sqlite\\ParallelProject.sqlite");
            return conn;
        } catch (Exception ex) {
            System.out.println("ex: " + ex);
            return null;
        }
    }
    
    public static void storeData(Connection conn, String[] header, String[] data){
        try{
            // Create Table
            String sql = "CREATE TABLE IF NOT EXISTS data (\n"
                    + "	id integer PRIMARY KEY,\n"
                    + "	key text NOT NULL,\n"
                    + "	value text\n"
                    + ");";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            // Delete Old Data
            String sqlDelete = "DELETE FROM data";
            PreparedStatement pstmtD = conn.prepareStatement(sqlDelete);
            pstmtD.executeUpdate();
            for (int i = 0; i < data.length; i++) {
                // Insert
                String sqlInsert = "INSERT INTO data(key, value) VALUES(?,?)";
                PreparedStatement pstmt = connectDB().prepareStatement(sqlInsert);
                System.out.println(i);
                pstmt.setString(1, header[i]);
                pstmt.setString(2, data[i]);
                pstmt.executeUpdate();
            }
        } catch(Exception ex) {
            System.out.println("ex: " + ex);
        }
    }
    
    public static String[] retriveData(Connection conn){
        String[] res = new String[]{"", "", "", "", ""};
        String sql = "SELECT id, key, value FROM data";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            for (int i = 0; rs.next(); i++) {
                res[i] = rs.getString("value");
            }
            return res;
            
        } catch(Exception ex) {
            System.out.println("ex: " + ex);
            return null;
        }
    }
    
}

