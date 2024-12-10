package org.milaifontanals.clubEsportiu.vista;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.milaifontanals.clubEsportiu.model.Equip;
import org.milaifontanals.clubEsportiu.model.Temporada;
import org.milaifontanals.clubEsportiu.persistencia.GestorBDClubEsportiuException;
import org.milaifontanals.clubEsportiu.persistencia.IGestorBDClubEsportiu;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author isard
 */
public class GestioEquips extends javax.swing.JFrame {

    private IGestorBDClubEsportiu capaOracleJDBC = null;
    private Temporada tmp;
    private FinestraLogin fLogin;
    private List<Equip> llEquips;
    private int idEquip;
    private List<Temporada> llTemporades;
    private Temporada tempEscollida;

    /**
     * Creates new form GestioEquips
     */
    public GestioEquips(IGestorBDClubEsportiu capa, Temporada temp) {
        initComponents();

        this.capaOracleJDBC = capa;
//        this.tmp = temp;

        try {
            llTemporades = capaOracleJDBC.obtenirTemporades();

            for (Iterator<Temporada> iterator = llTemporades.iterator(); iterator.hasNext();) {
                Temporada next = iterator.next();
                comboTemporada.addItem(next);

            }
        } catch (GestorBDClubEsportiuException ex) {
            Logger.getLogger(GestioEquips.class.getName()).log(Level.SEVERE, null, ex);
        }

        tempEscollida = (Temporada) comboTemporada.getSelectedItem();
        cercarEquips();
        TableColumn idColumn = tableEquips.getColumnModel().getColumn(4);
        tableEquips.removeColumn(idColumn);
        tableEquips.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void cercarEquips() {
        try {
            llEquips = capaOracleJDBC.obtenirEquipsPerTemporada(tempEscollida.getAny_temp());
            omplirTaula(llEquips);
        } catch (GestorBDClubEsportiuException ex) {
            Logger.getLogger(GestioEquips.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void omplirTaula(List<Equip> equips) {
        DefaultTableModel model = (DefaultTableModel) tableEquips.getModel();
        int idAnterior = -1;

        model.setRowCount(0);

        for (Equip e : equips) {
            try {

                if (idAnterior != e.getIdCategoria()) {
                    model.addRow(new Object[]{
                        capaOracleJDBC.obtenirNomCategoriaPerId(e.getIdCategoria()), "", "", "", ""});
                }

                idAnterior = e.getIdCategoria();

                model.addRow(new Object[]{
                    e.getNom(),
                    e.getTipus(),
                    e.getIdAny(),
                    capaOracleJDBC.obtenirNomCategoriaPerId(e.getIdCategoria()),
                    e.getId()
                });
            } catch (GestorBDClubEsportiuException ex) {
                Logger.getLogger(GestioEquips.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        tableEquips.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int selectedRow = tableEquips.getSelectedRow();
                if (selectedRow != -1) {
                    int modelRow = tableEquips.convertRowIndexToModel(selectedRow); // obtener el valor de la columna oculta, en lugar de usar el índice visual de la tabla.
                    Object idValue = ((DefaultTableModel) tableEquips.getModel()).getValueAt(modelRow, 4); // Índice en el modelo

                    if (idValue == null || idValue.toString().trim().isEmpty()) {

                        JOptionPane.showMessageDialog(
                                this, "Has seleccionat una fila separadora. Si us plau, selecciona un equip vàlid.", "Selecció no vàlida",
                                JOptionPane.WARNING_MESSAGE);

                        // Deselecciona la fila para evitar confusiones
                        tableEquips.clearSelection();
                    } else {

                        idEquip = Integer.parseInt(idValue.toString());
                        System.out.println("ID seleccionat: " + idEquip);
                    }
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comboNomEquip = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        btnInsert = new javax.swing.JButton();
        btnEsborrar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        tornarEnrere = new javax.swing.JLabel();
        btnInforme = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEquips = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        comboTemporada = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestió Dels Equips");

        comboNomEquip.setMaximumRowCount(6);
        comboNomEquip.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        comboNomEquip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboNomEquipActionPerformed(evt);
            }
        });

        jLabel3.setText("Equips");

        btnInsert.setText("Inserir");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnEsborrar.setText("Esborrar");
        btnEsborrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEsborrarActionPerformed(evt);
            }
        });

        jButton3.setText("Fitxa Equip");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tornarEnrere.setForeground(new java.awt.Color(0, 153, 255));
        tornarEnrere.setText("Tornar Enrere");
        tornarEnrere.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Font font = tornarEnrere.getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        tornarEnrere.setFont(font.deriveFont(attributes));
        tornarEnrere.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tornarEnrereMouseClicked(evt);
            }
        });

        btnInforme.setText("Informe");

        tableEquips.setAutoCreateRowSorter(true);
        tableEquips.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tableEquips.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nom Equip", "Tipus", "Temporada", "Categoria", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableEquips);

        jLabel6.setText("Temporada: ");

        comboTemporada.setMaximumRowCount(6);
        comboTemporada.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        comboTemporada.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboTemporadaItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboTemporada, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboNomEquip, 0, 101, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnEsborrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnInsert, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(btnInforme, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(89, 89, 89))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(tornarEnrere))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(60, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboNomEquip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnInsert))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnEsborrar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(comboTemporada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tornarEnrere)
                    .addComponent(btnInforme))
                .addGap(19, 19, 19))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void comboNomEquipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboNomEquipActionPerformed

    }//GEN-LAST:event_comboNomEquipActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        try {

            InserirEquip finserEquip = new InserirEquip(capaOracleJDBC, tempEscollida, this);
            finserEquip.setVisible(true);
            this.setVisible(false);
        } catch (GestorBDClubEsportiuException ex) {
            Logger.getLogger(GestioEquips.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (tableEquips.getSelectedRow() != -1 && idEquip != 0) {
                            Equip e = null;
                try {
                    e = capaOracleJDBC.obtenirEquipPerId(idEquip);
                    FitxaEquip f_FitxaEquip=new FitxaEquip(capaOracleJDBC,e);
                    f_FitxaEquip.setVisible(true);
                    this.dispose();
                } catch (GestorBDClubEsportiuException ex) {
                    Logger.getLogger(GestioEquips.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this, "Error en eliminar l'equip. Si us plau, prova més tard.", "Error d'eliminació", JOptionPane.ERROR_MESSAGE);

                }
        } else {
            JOptionPane.showMessageDialog(
                    this, "Has de seleccionar algún equip per gestionar la seva Fitxa.", "Selecció no vàlida", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tornarEnrereMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tornarEnrereMouseClicked
        FinestraLogin log = new FinestraLogin(capaOracleJDBC);
        log.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_tornarEnrereMouseClicked

    private void btnEsborrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEsborrarActionPerformed

        if (tableEquips.getSelectedRow() != -1 && idEquip != 0) {
            int opcion = JOptionPane.showConfirmDialog(
                    this, "El Equip podria tenir jugadors assignats. Estàs segur que vols esborrar-lo?", "Confirmació d'eliminació",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (opcion == JOptionPane.YES_OPTION) {
                Equip e = null;
                try {
                    e = capaOracleJDBC.obtenirEquipPerId(idEquip);
                    capaOracleJDBC.eliminarEquipMembre(idEquip);

                    capaOracleJDBC.eliminarUnEquip(e);
//                capaOracleJDBC.confirmarCanvis();
                    JOptionPane.showMessageDialog(this, "Equip eliminat correctament.", "Eliminació exitosa", JOptionPane.INFORMATION_MESSAGE);

                } catch (GestorBDClubEsportiuException ex) {
                    Logger.getLogger(GestioEquips.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this, "Error en eliminar l'equip. Si us plau, prova més tard.", "Error d'eliminació", JOptionPane.ERROR_MESSAGE);

                }
            }
        } else {
            JOptionPane.showMessageDialog(
                    this, "Has de seleccionar algún equip per esborrar-lo.", "Selecció no vàlida", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_btnEsborrarActionPerformed

    private void comboTemporadaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboTemporadaItemStateChanged
        tempEscollida = (Temporada) comboTemporada.getSelectedItem();
        cercarEquips();
    }//GEN-LAST:event_comboTemporadaItemStateChanged

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(GestioEquips.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(GestioEquips.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(GestioEquips.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(GestioEquips.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new GestioEquips().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEsborrar;
    private javax.swing.JButton btnInforme;
    private javax.swing.JButton btnInsert;
    private javax.swing.JComboBox<Equip> comboNomEquip;
    private javax.swing.JComboBox<Temporada > comboTemporada;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableEquips;
    private javax.swing.JLabel tornarEnrere;
    // End of variables declaration//GEN-END:variables
}