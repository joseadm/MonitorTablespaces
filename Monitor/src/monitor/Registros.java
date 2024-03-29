/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitor;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Stroke;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.table.DefaultTableModel;
import static monitor.MonitorFrame.conn;
import static monitor.MonitorFrame.countRow;
import static monitor.MonitorFrame.guardarRegistros;
import static monitor.MonitorFrame.reading;
import static monitor.MonitorFrame.system;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.Layer;

/**
 *
 * @author josealonso
 */
public class Registros extends javax.swing.JFrame {

    // Lista para guardar tablespaces
    public List<Tablespace> tables = new ArrayList<Tablespace>();
    public static List<Table> table = new ArrayList<Table>();
    static ArrayList<ArrayList<Table>> matriz;
    // Atributos para definir los tablespaces seleccionados

    static Connection conn = null;
    static Statement sta = null;
    static ResultSet res = null;
    static DefaultTableModel modelo = new DefaultTableModel();

    /**
     * Creates new form MonitorFrame
     */
    public Registros() {
        initComponents();
        this.jTable2.setModel(modelo);
        modelo.addColumn("Nombre de la tabla");
        modelo.addColumn("Tamaño (bytes)");
        modelo.addColumn("Cantidad de registros");
        modelo.addColumn("Tamaño indices (bytes)");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setTitle("Registro de Transacciones");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel1.setText("Seleccione el Tablespace para leer el historial de transaciones:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "USERS", "BSCHEMA", " " }));
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                        .addGap(27, 27, 27))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked

    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        Object selected = jComboBox1.getSelectedItem();
        eliminar();
        llenaMatriz(selected.toString());
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Registros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registros().setVisible(true);
            }
        });
    }
    
    public void eliminar(){
        modelo = (DefaultTableModel) jTable2.getModel();
        int a = jTable2.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
        modelo.removeRow(modelo.getRowCount()-1);
        }
        jTable2.setModel(modelo);
        /*int b = jTable2.getColumnCount()-1;
        for (int i = b; i >= 0; i--) {           
        modelo.removeColumn(modelo.getColumnCount()-1);*/
        
        //}
        //cargaTicket();
    }

    public static void creaMatriz(String tablespace) {
        try {
            conn = Monitor.Enlace(conn);
            ResultSet count = null;
            count = Monitor.countTables(count, tablespace);
            int x = 0;
            if (tablespace != "SYSTEM" || tablespace != "SYSAUX") {
                while (count.next()) {
                    x = Integer.valueOf(count.getObject(1).toString());
                }
            }
            reading("./" + tablespace + ".txt", x);
            count.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void llenaMatriz(String tablespace_name) {
        switch(tablespace_name) {
            case "USERS":    
        
        creaMatriz("USERS");
        Object[] tablas = new Object[4];
        Table t1 = new Table();
        for (int i = 0; i < users.getNumeroFilas(); i++) {
            for (int j = 0; j < users.getNumeroColumnas(); j++) {
                t1 = users.getMat().get(i).get(j);
                tablas[0] = t1.getName();
                tablas[1] = t1.getSize();
                tablas[2] = t1.getRegistros();
                tablas[3] = t1.getIndex();
                modelo.addRow(tablas);
            }
        }
        break;
            case "BSCHEMA":
                creaMatriz("BSCHEMA");
        Object[] tablas2 = new Object[4];
        Table t2 = new Table();
        for (int i = 0; i < bschema.getNumeroFilas(); i++) {
            for (int j = 0; j < bschema.getNumeroColumnas(); j++) {
                t2 = bschema.getMat().get(i).get(j);
                tablas2[0] = t2.getName();
                tablas2[1] = t2.getSize();
                tablas2[2] = t2.getRegistros();
                tablas2[3] = t2.getIndex();
                modelo.addRow(tablas2);
            }
        }
                break;
        }
    }
    
    

    static Matriz users;
    static Matriz bschema;
    static int countRow = 4;

    public static void reading(String archivo, int columns) {
        try {
            String cadena;
            String count = "";
            FileReader f = new FileReader(archivo);
            BufferedReader b = new BufferedReader(f);
            FileReader f1 = new FileReader("./count.txt");
            BufferedReader b1 = new BufferedReader(f1);
            count = b1.readLine();
            int count_num = Integer.parseInt(count);
            countRow = count_num;
            boolean bandera;
            Matriz mat = new Matriz();
            switch (archivo) {
                case "./USERS.txt": {
                    users = new Matriz(countRow, columns);
                    mat = users;
                    break;
                }
                case "./BSCHEMA.txt": {
                    bschema = new Matriz(countRow, columns);
                    mat = bschema;
                    break;
                }
            }
            while ((cadena = b.readLine()) != null) {
                Table t1 = new Table();

                StringTokenizer tokens = new StringTokenizer(cadena, ",");

                String name = tokens.nextToken().trim();
                String size = tokens.nextToken().trim();
                String registros = tokens.nextToken().trim();
                String index = tokens.nextToken().trim();

                if (registros == "null") {
                    registros = "0";
                }

                t1.setName(name);
                t1.setSize(Integer.parseInt(size));
                try {
                    t1.setRegistros(Integer.parseInt(registros));
                } catch (Exception e) {
                    t1.setRegistros(0);
                }

                try {
                    t1.setIndex(Integer.parseInt(index));
                } catch (Exception e1) {
                    t1.setIndex(0);
                }
                bandera = false;
                for (int i = 0; i < countRow; i++) {
                    if (bandera == true) {
                        break;
                    }
                    for (int j = 0; j < columns; j++) {
                        if (mat.getPosicion(i, j).getName() == t1.getName()) {
                            mat.agregarTabla(t1, i + 1, j);
                            bandera = true;
                            break;
                        } else {
                            if (mat.getPosicion(i, j).getName() == "") {
                                mat.agregarTabla(t1, i, j);
                                bandera = true;
                                break;
                            }
                        }
                    }
                }
            }
            switch (archivo) {
                case "./USERS.txt": {
                    users = mat;
                    break;
                }
                case "./BSCHEMA.txt": {
                    bschema = mat;
                    break;
                }
            }
            b.close();
            //System.out.println(system.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables

}
