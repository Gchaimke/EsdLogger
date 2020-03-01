/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esdLogger;

/**
 *
 * @author gchaim
 */
import java.sql.*;  
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SQLiteDB {
    
    private Connection connect(String DBPath) {  
        // SQLite connection string  
        String url = "jdbc:sqlite:"+DBPath;  
        Connection conn = null;  
        try {  
            conn = DriverManager.getConnection(url);  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
        return conn;  
    }
    
    public void createNewMonthTable(String DBPath,String tbName) {       
        // SQL statement for creating a new table  
        String sql = "CREATE TABLE IF NOT EXISTS "+tbName+" (\n"  
                + " id integer PRIMARY KEY,\n"  
                + " user_num text NOT NULL,\n"
                + " day integer NOT NULL,\n"
                + " time text NOT NULL,\n"
                + " status integer \n);";  
        try{  
            Connection conn = this.connect(DBPath);  
            Statement stmt = conn.createStatement();  
            stmt.execute(sql);
            //System.out.println("Table "+tbName+" has been created.");
            conn.close();
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
    
    public void setUserStatus(String DBPath ,String month,String user_num, int status) {
        LocalDateTime currTime = LocalDateTime.now();
        DateTimeFormatter month_year = DateTimeFormatter.ofPattern("MMYY");
        DateTimeFormatter day = DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");
        String tbName ="month"+currTime.format(month_year);
        createNewMonthTable(DBPath, tbName);
        String sql = "INSERT INTO "+month+" (user_num, day, time, status) VALUES(?,?,?,?)";  
 
        try{  
            Connection conn = this.connect(DBPath);  
            PreparedStatement pstmt = conn.prepareStatement(sql);   
            pstmt.setString(1,user_num);
            pstmt.setString(2, currTime.format(day));
            pstmt.setString(3, currTime.format(time));
            pstmt.setInt(4, status);
            pstmt.executeUpdate();
            //System.out.println("Status of user with num "+user_num+" has been updated to: "+status);
            pstmt.close();
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
    
    public void createUsersTable(String DBPath) {       
        // SQL statement for creating a new table  
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"  
                + " id integer PRIMARY KEY,\n"  
                + " user_num text NOT NULL UNIQUE,\n"
                + " user_name text NOT NULL UNIQUE,\n"
                + " card1 text UNIQUE,\n"
                + " card2 text);";  
        try{  
            Connection conn = this.connect(DBPath);  
            Statement stmt = conn.createStatement();  
            stmt.execute(sql);
            //System.out.println("Table users has been created.");
            conn.close();
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    } 
    
    public void addNewUser(String DBPath ,String user_num, String user_name, String card1, String card2) {  
        String sql = "INSERT INTO users(user_num, user_name, card1, card2) VALUES(?,?,?,?)";  
 
        try{
            Connection conn = this.connect(DBPath);  
            PreparedStatement pstmt = conn.prepareStatement(sql);  
            pstmt.setString(1, user_num);  
            pstmt.setString(2, user_name);
            pstmt.setString(3, card1);
            pstmt.setString(4, card2);
            pstmt.executeUpdate();
            System.out.println("A new user "+user_name+" has been inserted with num: "+user_num);
            conn.close();
            conn.commit();
            pstmt.close();
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
    
    public void updateUser(String DBPath , String set, String where) {  
        String sql = "UPDATE users SET "+set+" WHERE "+where;  
   
        try{  
            Connection conn = this.connect(DBPath);
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
            System.out.println("User "+where+" has been updated with value: "+set);
            conn.close();
            stmt.close();
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
    
    public void deleteUser(String DBPath ,String where) {  
        String sql = "DELETE from users WHERE "+where;
        try{  
            Connection conn = this.connect(DBPath);
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
            System.out.println("user "+where+" has been deleted");
            conn.close();
            stmt.close();
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
    
    public void getAllUsers(String DBPath){  
        String sql = "SELECT id,user_num,user_name,card1,card2 FROM users";  
          
        try {  
            Connection conn = this.connect(DBPath);  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);  
              
            // loop through the result set  
            while (rs.next()) {  
                System.out.println(rs.getInt("id") +  "\t" +   
                                   rs.getString("user_num") + "\t" +
                                   rs.getString("user_name") + "\t" +
                                   rs.getString("card1") + "\t" +
                                   rs.getString("card2"));  
            }
            conn.close();
            stmt.close();
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }
    }
    
    public void getUserStatistic(String DBPath, String tbName, String user){  
        String sql = "SELECT users.user_num AS user,day,time,status,user_name FROM "+tbName+
                " INNER JOIN users on users.user_num = "+tbName+".user_num" +
                " WHERE "+tbName+".user_num='"+user+"'";  
          
        try {  
            Connection conn = this.connect(DBPath);  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);  
              
            // loop through the result set  
            while (rs.next()) {  
                System.out.println(rs.getString("user") + "\t" +
                                   rs.getString("user_name") + "\t" +
                                   rs.getString("day") + "\t" +
                                   rs.getString("time") + "\t" +
                                   rs.getString("status"));  
            }
            conn.close();
            stmt.close();
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }
    }
    
    public void getMonthStatistic(String DBPath, String tbName){  
        String sql = "SELECT users.user_num AS user,day,time,status,user_name FROM "+tbName+
                " INNER JOIN users on users.user_num = "+tbName+".user_num";  
        try {  
            Connection conn = this.connect(DBPath);  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);  
              
            // loop through the result set  
            while (rs.next()) {  
                System.out.println(rs.getString("user_name") + "\t" +
                                   rs.getString("user") + "\t" +
                                   rs.getString("day") + "\t" +
                                   rs.getString("time") + "\t" +
                                   rs.getString("status"));  
            }
            conn.close();
            stmt.close();
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }
    }
           
}
