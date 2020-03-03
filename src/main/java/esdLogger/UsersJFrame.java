/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esdLogger;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author gchaim
 */
public final class UsersJFrame extends javax.swing.JFrame {
    private static JDialog dialog;
    private final DefaultTableModel dtm;
    private static final String HEADER[] = new String[] {"כרטיס 2","כרטיס 1","שם","מספר עובד"};
    /**
     * Creates new form UsersJFrame
     */
    public UsersJFrame() {
        this.dtm = new DefaultTableModel(0, 0);
        initComponents();
        showUsers();
        tbUsers.getModel().addTableModelListener((TableModelEvent e) -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            TableModel model = (TableModel)e.getSource();
            String columnName = model.getColumnName(column);
            Object data = model.getValueAt(row, column);
            JFrame f= new JFrame();
            f.setAlwaysOnTop(true);
            dialog  =new JDialog(f,"Edit User",true);
            dialog.setLayout(new FlowLayout());
            JButton button = new JButton("OK");
            button.addActionListener((ActionEvent e1) -> {
                UsersJFrame.dialog.setVisible(false);
            });
            dialog.add(new JLabel(columnName+" "+data));
            dialog.add(button);
            dialog.setSize(300,300);
            dialog.setVisible(true);
        });
        
    }
    
    void showUsers(){
        dtm.setColumnIdentifiers(HEADER);
        tbUsers.setModel(dtm);
        dtm.setRowCount(0);
        String DBPath = "test.db";
        SQLiteDB sql = new SQLiteDB();
        sql.getAllUsers(DBPath).forEach((user) -> {
            dtm.addRow(new Object[]{user.getCard2(),user.getCard1(),user.getName(),user.getUser_num()});
        });
        alignTable();
    }
    
    void alignTable(){
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int i =0 ; i <4;i++){
            tbUsers.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
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

        spMonth = new javax.swing.JScrollPane();
        tbUsers = new javax.swing.JTable();

        setAlwaysOnTop(true);

        spMonth.setBackground(new java.awt.Color(255, 255, 255));

        tbUsers.setAutoCreateRowSorter(true);
        tbUsers.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tbUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "כרטיס 2", "כרטיס 1", "שם", "מספר עובד"
            }
        ));
        tbUsers.setAlignmentX(0.0F);
        tbUsers.setAlignmentY(0.0F);
        tbUsers.setMaximumSize(new java.awt.Dimension(500, 480));
        tbUsers.setMinimumSize(new java.awt.Dimension(300, 500));
        tbUsers.setOpaque(false);
        tbUsers.setRowHeight(25);
        tbUsers.setShowVerticalLines(false);
        tbUsers.getTableHeader().setReorderingAllowed(false);
        tbUsers.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbUsersPropertyChange(evt);
            }
        });
        spMonth.setViewportView(tbUsers);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spMonth, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spMonth, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbUsersPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbUsersPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tbUsersPropertyChange

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
            java.util.logging.Logger.getLogger(UsersJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new UsersJFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane spMonth;
    private javax.swing.JTable tbUsers;
    // End of variables declaration//GEN-END:variables

}