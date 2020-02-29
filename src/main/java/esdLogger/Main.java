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
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
        
        java.awt.EventQueue.invokeLater(() -> {
            String DBPath = "test.db";
            SQLiteDB sql = new SQLiteDB();
            sql.createNewDatabase(DBPath);
            sql.createNewTable(DBPath, "employees");
            sql.insert(DBPath,"Yossi",10000);
            sql.update(DBPath, 0, 25000);
            sql.delete(DBPath, 4);
            sql.selectAll(DBPath);
        });
        
    }
    
}
