/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esdLogger;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author gchaim
 */
public class CalendarJFrame extends javax.swing.JFrame {
    private final DefaultTableModel dtm;
    Calendar startCal;  
    Calendar endCal;
    int workDays = 0;
    /**
     * Creates new form CalendarJFrame
     */
    public CalendarJFrame() {
        this.dtm = new DefaultTableModel(0, 0);
        initComponents();
        
        //System.out.println(getWorkingDays(3));
        //System.out.println(getWorkingDays(4));
        
        showMonth(tbCalendar,3);
    }
    
    
    
    final ArrayList<String> getWorkingDays(int month){
        startCal = Calendar.getInstance();
        endCal = Calendar.getInstance();
        startCal.setTime(new Date(month+"/00/2020"));
        endCal.setTime(new Date(month+1+"/00/2020"));
        
        ArrayList<String> workingDays;
        workingDays = new ArrayList<>();
        workingDays.add("Worker Name");
        
        ArrayList<Integer> holidays;
        holidays = new ArrayList<>();
        
        //System.out.println("Current start day of year in month "+month+": "+startCal.get(Calendar.DAY_OF_YEAR));
        do {
          startCal.add(Calendar.DAY_OF_MONTH, 1);
          if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY
          && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
          && !holidays.contains((Integer) startCal.get(Calendar.DAY_OF_YEAR))) {
              workingDays.add(startCal.get(Calendar.DAY_OF_MONTH)+"");
              ++workDays;
          }
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis());
        
        return workingDays;
    }
    
    final ArrayList<String> getNotWorkingDays(int month){
        startCal = Calendar.getInstance();
        endCal = Calendar.getInstance();
        startCal.setTime(new Date(month+"/00/2020"));
        endCal.setTime(new Date(month+1+"/00/2020"));
        
        ArrayList<String> days;
        days = new ArrayList<>();
        
        ArrayList<Integer> holidays;
        holidays = new ArrayList<>();
        holidays.add(10);
        
        //System.out.println("Current start day of year in month "+month+": "+startCal.get(Calendar.DAY_OF_YEAR));
        do {
          startCal.add(Calendar.DAY_OF_MONTH, 1);
          if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY
          || startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
          || holidays.contains((Integer) startCal.get(Calendar.DAY_OF_YEAR))) {
              days.add(startCal.get(Calendar.DAY_OF_MONTH)+"");
              ++workDays;
          }
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis());
        
        return days;
    }
    
    final int monthDays(String month){
        YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(month.substring(2, 3)), Integer.parseInt(month.substring(0, 2)));
        return yearMonthObject.lengthOfMonth();
    }
    
    final void showMonth(JTable table, int month){
        LocalDateTime currTime = LocalDateTime.now();
        DateTimeFormatter month_year = DateTimeFormatter.ofPattern("MMYY");
        String tbName ="month_"+currTime.format(month_year);
        String[] HEADER = new String[monthDays(currTime.format(month_year))+1];
        HEADER[0]="User Name";
        for(int i=1;i<HEADER.length;i++)
            HEADER[i]=i+"";
        dtm.setColumnIdentifiers(HEADER);
        table.setModel(dtm);
        dtm.setRowCount(0);
        SQLiteDB sql = new SQLiteDB();
        sql.getMonthStatistic(tbName,HEADER.length).forEach((userData) -> {
            dtm.addRow(userData);
        });
        
        ArrayList<String> days = getNotWorkingDays(month);
        for(int i=0;i< dtm.getRowCount();i++){
            for(int j =0;j<days.size();j++){
                dtm.setValueAt("-", i, Integer.parseInt(days.get(j)));
            }
        }
        
        System.out.println(getNotWorkingDays(month));
        prepareTable(table);
        jLabel1.setText("Month: "+month);
    }
    
    static void prepareTable(JTable table){
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMinWidth(150);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int i =1 ; i <table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbCalendar = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setAlwaysOnTop(true);

        tbCalendar.setAutoCreateRowSorter(true);
        tbCalendar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbCalendar.setFillsViewportHeight(true);
        tbCalendar.setMaximumSize(new java.awt.Dimension(1920, 80));
        tbCalendar.setMinimumSize(new java.awt.Dimension(1920, 80));
        tbCalendar.setNextFocusableComponent(jButton1);
        tbCalendar.setPreferredSize(new java.awt.Dimension(1920, 64));
        tbCalendar.setRowHeight(20);
        jScrollPane1.setViewportView(tbCalendar);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("curent month");

        jButton1.setText(">>");

        jButton2.setText("<<");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CalendarJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new CalendarJFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbCalendar;
    // End of variables declaration//GEN-END:variables
}
