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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        java.awt.EventQueue.invokeLater(() -> {
            new MainJFrame().setVisible(true);
        });
                
        //SQLiteTest();
        //UserTest();
        
    }
    
    static void UserTest(){
        User user = new User("Chaim", "77", "112233", "");
        System.out.println(user.toString());
        user.setUser_num("79");
        user.setCard2("00000");
        user.setName("Yossi");
        System.out.println(user.getName()+" "+user.getUser_num()+" "+user.card1);
        System.out.println(user.toString());
    }
    
    static void SQLiteTest(){
        java.awt.EventQueue.invokeLater(() -> {
            String DBPath = "test.db";
            String month_year = "month0320";
            String user = "77";
            SQLiteDB sql = new SQLiteDB();
            sql.createUsersTable(DBPath);
            sql.addNewUser(DBPath,  user , "חיים", "01234", "012345");            
            sql.setUserStatus(DBPath, month_year,user, 1);
            sql.setUserStatus(DBPath, month_year,user, 0);
            sql.setUserStatus(DBPath, month_year,user, 1);
            System.out.println("User statistic:"+user);
            sql.getUserStatistic(DBPath, month_year, user);
            System.out.println("Month statistic:"+month_year);
            sql.getMonthStatistic(DBPath, month_year);

            System.out.println("All Users:");
            sql.getAllUsers(DBPath);
        });
    }
}
