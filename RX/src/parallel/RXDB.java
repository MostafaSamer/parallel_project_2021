/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallel;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import static parallel.RXAPI.results;

/**
 *
 * @author mosta
 */
public class RXDB {
    
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
    
    public static void storeData(Connection conn, String[] header, ArrayList<String> data){
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
            // Inssert
            Flowable.fromIterable(data)
                .parallel()
                .concatMap(url -> Flowable.just(url)
                        .subscribeOn(Schedulers.newThread())
                        .map(w -> addData(w))
                )
                .sequential()
                .subscribe(w-> System.out.println(w));
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
    
    public static String addData(String data) throws SQLException {
        System.out.println("Data: " + data);
        // Insert
        String sqlInsert = "INSERT INTO data(key, value) VALUES(?,?)";
        PreparedStatement pstmt = connectDB().prepareStatement(sqlInsert);
        pstmt.setString(1, "TEMP KEY");
        pstmt.setString(2, data);
        pstmt.executeUpdate();
        return "";
    }
    
}

