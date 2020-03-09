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
        
        showMonth(tbCalendar);
    }
    
    
    
    final ArrayList<String> getWorkingDays(int month){
        startCal = Calendar.getInstance();
        endCal = Calendar.getInstance();
        startCal.setTime(new Date(month+"/00/2020"));
        endCal.setTime(new Date(month+1+"/00/2020"));
        
        ArrayList<String> days;
        days = new ArrayList<>();
        days.add("Worker Name");
        
        ArrayList<Integer> holidays;
        holidays = new ArrayList<>();
        
        //System.out.println("Current start day of year in month "+month+": "+startCal.get(Calendar.DAY_OF_YEAR));
        do {
          startCal.add(Calendar.DAY_OF_MONTH, 1);
          if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY
          && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
          && !holidays.contains((Integer) startCal.get(Calendar.DAY_OF_YEAR))) {
              days.add(startCal.get(Calendar.DAY_OF_MONTH)+"");
              ++workDays;
          }
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis());
        
        return days;
    }
    
    final ArrayList<String> getNotWorkingDays(int month){
        startCal = Calendar.getInstance();
        endCal = Calendar.getInstance();
        startCal.setTime(new Date(month+"/00/2020"));
        endCal.setTime(new Date(month+1+"/00/2020"));
        
        ArrayList<String> days;
        days = new ArrayList<>();
        
        do {
          startCal.add(Calendar.DAY_OF_MONTH, 1);
          if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY
          || startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
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
    
    final void showMonth(JTable table){
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
        
        ArrayList<String> days = getNotWorkingDays(Integer.parseInt(currTime.format(month_year).substring(2)));
        for(int i=0;i< dtm.getRowCount();i++){
            for(int j =0;j<days.size();j++){
                dtm.setValueAt("-", i, Integer.parseInt(days.get(j)));
            }
        }
        prepareTable(table);
        lblCurrentMonth.setText(currTime.format(month_year));
    }
    
        final void showMonth(JTable table,String month){
        String tbName ="month_"+month;
        String[] HEADER = new String[monthDays(month)+1];
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
        
        ArrayList<String> days = getNotWorkingDays(Integer.parseInt(month.substring(2)));
        for(int i=0;i< dtm.getRowCount();i++){
            for(int j =0;j<days.size();j++){
                dtm.setValueAt("-", i, Integer.parseInt(days.get(j)));
            }
        }
        prepareTable(table);
        lblCurrentMonth.setText(month);
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
        lblCurrentMonth = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();

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
        tbCalendar.setNextFocusableComponent(btnNext);
        tbCalendar.setPreferredSize(new java.awt.Dimension(1920, 64));
        tbCalendar.setRowHeight(20);
        jScrollPane1.setViewportView(tbCalendar);

        lblCurrentMonth.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCurrentMonth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCurrentMonth.setText("curent month");

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
                    .addComponent(lblCurrentMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnPrev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNext)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCurrentMonth)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        String month="";
        String date = lblCurrentMonth.getText();
        int m = Integer.parseInt(date.substring(0,2))+1;
        if(m<10)
            month="0"+m;
        else
            month = m+"";
        System.out.println(month+date.substring(2));
        showMonth(tbCalendar,month+date.substring(2));
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        String month="";
        String date = lblCurrentMonth.getText();
        int m = Integer.parseInt(date.substring(0,2))-1;
        if(m<10)
            month="0"+m;
        else
            month = m+"";
        showMonth(tbCalendar,month+date.substring(2));
    }//GEN-LAST:event_btnPrevActionPerformed

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
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCurrentMonth;
    private javax.swing.JTable tbCalendar;
    // End of variables declaration//GEN-END:variables
}
