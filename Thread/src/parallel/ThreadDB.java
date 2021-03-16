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
public class ThreadDB {
    
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

    public static String[] retriveData(Connection conn) {
        String[] res = new String[]{"", "", "", "", ""};
        String sql = "SELECT id, key, value FROM data";
        
            new Thread(new Runnable() {
                int i=0;
                public void run() {
                    Statement stmt;
                    try {
                        stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);
                        while (rs.next()) {
                            res[i] = rs.getString("value");
                            /*System.out.println( res[i]);*/
                            System.out.println("DONE");
                            i++;
                        }
                        System.out.println("ssssssssssssssssssssss");
                    } catch (SQLException ex) {
                        // TODO Auto-generated catch block
                        System.out.println("ex: " + ex);

                    }

                }
            }).start();
            
        while(res[4]==""){
            System.out.println("Waiting for Data...");
        }
        
        return res;
     }
}

