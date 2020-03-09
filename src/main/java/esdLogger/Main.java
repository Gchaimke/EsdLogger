/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esdLogger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author gchaim
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        java.awt.EventQueue.invokeLater(() -> {
            new MainJFrame().setVisible(true);
        });
                
        startSQLite();
        SQLiteTest();
    }
        
    static void startSQLite(){
        java.awt.EventQueue.invokeLater(() -> {
            SQLiteDB sql = new SQLiteDB();
            
            LocalDateTime currTime = LocalDateTime.now();
            DateTimeFormatter month_year = DateTimeFormatter.ofPattern("MMYY");
            String tbName ="month_"+currTime.format(month_year); //String tbName = "month_0320";
            
            //Create users table
            if(!sql.tableExists( "users"))
                sql.createUsersTable();
            //Create month table
            if(!sql.tableExists( tbName))
                sql.createNewMonthTable( tbName);
            
            });
    }
    
    static void SQLiteTest(){
        java.awt.EventQueue.invokeLater(() -> {
            SQLiteDB sql = new SQLiteDB();

            LocalDateTime currTime = LocalDateTime.now();
            DateTimeFormatter month_year = DateTimeFormatter.ofPattern("MMYY");
            String tbName ="month_"+currTime.format(month_year); //String tbName = "month_0320";

            User chaim =new User("Chaim Turky","4", "00004", "");
            User slava = new User("Slava Bochman","5", "00005", "");
            User max = new User("Max Sherkovky","6", "00006", "");

            if(!sql.userExists( chaim))
                sql.addNewUser(chaim );
            if(!sql.userExists( slava))
                sql.addNewUser(slava );
            if(!sql.userExists( max))
                sql.addNewUser(max );
            //sql.setUserStatus(chaim, 3);
            sql.setUserStatus(slava, 11);
            //sql.setUserStatus(max, 24);

            //System.out.println("User statistic:"+chaim.getName());
            //sql.getUserStatistic( tbName, chaim);
            //System.out.println("User statistic:"+slava.getName());
            //sql.getUserStatistic( tbName, slava);
            //System.out.println("User statistic:"+max.getName());
            //sql.getUserStatistic( tbName, max);

            //System.out.println("Month statistic:"+tbName);
            //sql.getMonthStatistic( tbName);
            //System.out.println(sql.getUserStatistic(tbName,slava));
            System.out.println(sql.getTablesNames());
        });
    }
}
