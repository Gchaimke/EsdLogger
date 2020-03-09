/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esdLogger;

import java.sql.*;  
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
/**
 * @version  1.0
 * @since 03/03/20
 * @author gchaim
 */
public class SQLiteDB {
    String DBPath;
    SQLiteDB(){
        DBPath = "Main.db";
    }
    
    private Connection connect() {  
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
    
    /**
     * Create new table with current month and year name - monthMMYY
     * @param tbName Name of Table
     */
    public void createNewMonthTable(String tbName) {       
        // SQL statement for creating a new table  
        String sql = "CREATE TABLE IF NOT EXISTS "+tbName+" (\n"  
                + " id integer PRIMARY KEY,\n"  
                + " user_num text NOT NULL,\n"
                + " day integer NOT NULL,\n"
                + " time text NOT NULL,\n"
                + " status integer \n);";  
        try{  
            Connection conn = this.connect();  
            Statement stmt = conn.createStatement();  
            stmt.execute(sql);
            //System.out.println("Table "+tbName+" has been created.");
            conn.close();
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
    
    /**
     * Create table with all users
     */
    public void createUsersTable() {       
        // SQL statement for creating a new table  
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"  
                + " id integer PRIMARY KEY,\n"  
                + " user_num text NOT NULL UNIQUE,\n"
                + " user_name text NOT NULL UNIQUE,\n"
                + " card1 text UNIQUE,\n"
                + " card2 text);";  
        try{  
            Connection conn = this.connect();  
            Statement stmt = conn.createStatement();  
            stmt.execute(sql);
            //System.out.println("Table users has been created.");
            conn.close();
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    } 
    
    /**
     * Add new user to users table
     * @param user from user class
     * @return true if user added to database
     */
    public boolean addNewUser(User user) {  
        String sql = "INSERT INTO users(user_num, user_name, card1, card2) VALUES(?,?,?,?)";   
        try{
            Connection conn = this.connect();  
            PreparedStatement pstmt = conn.prepareStatement(sql);  
            pstmt.setString(1, user.getUser_num());  
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getCard1());
            pstmt.setString(4, user.getCard2());
            pstmt.executeUpdate();
            System.out.println("A new user "+user.getName()+" has been inserted with num: "+user.getUser_num());
            conn.close();
            conn.commit();
            pstmt.close();
            return true;
        } catch (SQLException e) {  
            System.out.println(e.getMessage());
            return false;
        }  
    }
    
    /**
     * Change the user data
     * @param set what to set like name='Adam' or user_num='1' or card1='0001'
     * @param where where to set like id=1 or name='Adam'
     * @return true if user updated
     */
    public boolean updateUser(String set, String where) {  
        String sql = "UPDATE users SET "+set+" WHERE "+where;  
   
        try{  
            Connection conn = this.connect();
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
            System.out.println("User "+where+" has been updated with value: "+set);
            conn.close();
            stmt.close();
            return true;
        } catch (SQLException e) {  
            System.out.println(e.getMessage());
            return false;
        }  
    }
    
    /**
     * Set 1 if user pass the check
     * @param user from class User
     */
    public void setUserStatus(User user) {
        LocalDateTime currTime = LocalDateTime.now();
        DateTimeFormatter month_year = DateTimeFormatter.ofPattern("MMYY");
        DateTimeFormatter day = DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");
        String tbName ="month_"+currTime.format(month_year);
        String sql = "INSERT INTO "+tbName+" (user_num, day, time, status) VALUES(?,?,?,?)";  
 
        try{  
            Connection conn = this.connect();  
            PreparedStatement pstmt = conn.prepareStatement(sql);   
            pstmt.setString(1,user.getUser_num());
            pstmt.setString(2, currTime.format(day));
            pstmt.setString(3, currTime.format(time));
            pstmt.setInt(4, 1);
            pstmt.executeUpdate();
            //System.out.println("Status of user with num "+user_num+" has been updated to: "+status);
            pstmt.close();
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
    
    /**
     * Set 1 if user pass the check
     * @param user from class User
     * @param day set for day num
     */
    public void setUserStatus(User user, int day) {
        LocalDateTime currTime = LocalDateTime.now();
        DateTimeFormatter month_year = DateTimeFormatter.ofPattern("MMYY");
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");
        String tbName ="month_"+currTime.format(month_year);
        String sql = "INSERT INTO "+tbName+" (user_num, day, time, status) VALUES(?,?,?,?)";  
 
        try{  
            Connection conn = this.connect();  
            PreparedStatement pstmt = conn.prepareStatement(sql);   
            pstmt.setString(1,user.getUser_num());
            pstmt.setString(2, day+"");
            pstmt.setString(3, currTime.format(time));
            pstmt.setInt(4, 1);
            pstmt.executeUpdate();
            //System.out.println("Status of user with num "+user_num+" has been updated to: "+status);
            pstmt.close();
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
    
    /**
     * Delete user
     * @param where where to set like id=1 or name='Adam'
     * @return true if user deleted
     */
    public boolean deleteUser(String where) {  
        String sql = "DELETE from users WHERE "+where;
        try{  
            Connection conn = this.connect();
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
            System.out.println("user "+where+" has been deleted!");
            conn.close();
            stmt.close();
            return true;
        } catch (SQLException e) {  
            System.out.println(e.getMessage());
            return false;
        }  
    }
    
    /**
     * 
     * @return list of all users as ArrayList from users table
     */
    public ArrayList<User> getAllUsers(){  
        String sql = "SELECT id,user_num,user_name,card1,card2 FROM users";  
        //User user = new User();
        ArrayList<User> users;
        users = new ArrayList<>();
        try {  
            Connection conn = this.connect();  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);  
              
            // loop through the result set  
            while (rs.next()) {
                users.add(new User(rs.getString("user_name"),rs.getString("user_num"),rs.getString("card1"),rs.getString("card2")));  
            }
            conn.close();
            stmt.close();
            return users;
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }
        return null;
    }
    
    public boolean userExists(User user){
        return getAllUsers().stream().anyMatch((cUser) -> (cUser.equals(user)));
    }
    
    public ArrayList<String> getUserStatistic(String tbName, User user){
        ArrayList<String> userStat;
        userStat = new ArrayList<>();
        String sql = "SELECT users.user_num AS user,day,time,status,user_name FROM "+tbName+
                " INNER JOIN users on users.user_num = "+tbName+".user_num" +
                " WHERE "+tbName+".user_num='"+user.getUser_num()+"' ORDER BY day ASC";  
          
        try {  
            Connection conn = this.connect();  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);  
              
            // loop through the result set 
            userStat.add(user.getName());
            while (rs.next()) {
                userStat.add(rs.getString("day")+","+rs.getString("time")+","+rs.getString("status"));                
            }
            conn.close();
            stmt.close();
            return userStat;
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }
        return null;
    }
    
    public ArrayList<String[]> getMonthStatistic(String tbName, int workingDays){
        String[] userStats = new String[workingDays];
        ArrayList<User> users = getAllUsers();
        ArrayList<String[]> allUsersStats;
        allUsersStats = new ArrayList<>();
        
        if(tableExists(tbName)){
            users.forEach((User user) -> {
                userStats[0] = user.name;
                ArrayList<String> userData = getUserStatistic(tbName, user);
                try {
                    for(int i=1;i< userData.size();i++){
                    int day =Integer.parseInt(userData.get(i).split(",")[0]);
                    if(day<userStats.length)
                        userStats[day]="V";
                    }
                } catch (NullPointerException e) {
                    System.err.println("Error:"+e.getMessage());
                }

                allUsersStats.add(Arrays.copyOf(userStats, workingDays)); //Arrays.toString(userStats)
            });
        }else{
            createNewMonthTable(tbName);
        }
        return allUsersStats;
    }
    
    public ArrayList<String> getTablesNames(){
        ArrayList<String> dbNames;
        dbNames = new ArrayList<>();
        try {
            Connection conn = this.connect();
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                dbNames.add(rs.getString(3));
            }
            return dbNames;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public boolean tableExists(String tbName){
        return getTablesNames().stream().anyMatch((name) -> (name.equals(tbName)));
    }
           
}
