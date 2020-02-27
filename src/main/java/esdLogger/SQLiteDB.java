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
    
    public void createNewDatabase(String DBPath) {  
   
        try {
            Connection conn = this.connect(DBPath); 
            if (conn != null) {  
                DatabaseMetaData meta = conn.getMetaData();  
                System.out.println("The driver name is " + meta.getDriverName());  
                System.out.println("A new database has been created.");
                conn.close();
            }  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
    
    public void createNewTable(String DBPath,String tbName) {       
        // SQL statement for creating a new table  
        String sql = "CREATE TABLE IF NOT EXISTS "+tbName+" (\n"  
                + " id integer PRIMARY KEY,\n"  
                + " name text NOT NULL,\n"  
                + " capacity real\n"  
                + ");";  
        try{  
            Connection conn = this.connect(DBPath);  
            Statement stmt = conn.createStatement();  
            stmt.execute(sql);
            System.out.println("Table "+tbName+" has been created.");
            conn.close();
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }    
    
    public void insert(String DBPath ,String name, double capacity) {  
        String sql = "INSERT INTO employees(name, capacity) VALUES(?,?)";  
   
        try{  
            Connection conn = this.connect(DBPath);  
            PreparedStatement pstmt = conn.prepareStatement(sql);  
            pstmt.setString(1, name);  
            pstmt.setDouble(2, capacity);  
            pstmt.executeUpdate();
            System.out.println("A new user "+name+" has been inserted with value: "+capacity);
            conn.close();
            conn.commit();
            pstmt.close();
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
    
    public void update(String DBPath ,int id, double capacity) {  
        String sql = "UPDATE employees set capacity = "+capacity+" where ID="+id;  
   
        try{  
            Connection conn = this.connect(DBPath);
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
            System.out.println("User with "+id+" has been updated with value: "+capacity);
            conn.close();
            stmt.close();
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
    
    public void delete(String DBPath ,int id) {  
        String sql = "DELETE from employees where ID="+id;  
   
        try{  
            Connection conn = this.connect(DBPath);
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
            System.out.println("user with "+id+" has been deleted");
            conn.close();
            stmt.close();
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
    
    public void selectAll(String DBPath){  
        String sql = "SELECT * FROM employees";  
          
        try {  
            Connection conn = this.connect(DBPath);  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);  
              
            // loop through the result set  
            while (rs.next()) {  
                System.out.println(rs.getInt("id") +  "\t" +   
                                   rs.getString("name") + "\t" +  
                                   rs.getDouble("capacity"));  
            }
            conn.close();
            stmt.close();
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }
    }  
    
}
