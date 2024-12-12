package org.milaifontanals.clubEsportiu.vista;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import org.milaifontanals.clubEsportiu.model.Categoria;
import org.milaifontanals.clubEsportiu.model.Equip;
import org.milaifontanals.clubEsportiu.model.ExceptionClub;
import org.milaifontanals.clubEsportiu.model.Jugador;
import org.milaifontanals.clubEsportiu.model.Membre;
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
public class FitxaEquip extends javax.swing.JFrame {

    private IGestorBDClubEsportiu capaOracleJDBC = null;
    private Equip equip = null;
    private int idJugador;
    private Jugador jugadorNou;
    private List<Jugador> jugadoresEquipo;
    private List<Jugador> jugadorsDisponibles;
    private GestioEquips fGestioEquips;

    /**
     * Creates new form GestioPlantilla
     */
    public FitxaEquip(IGestorBDClubEsportiu capa, Equip e, GestioEquips f) {
        initComponents();
        this.capaOracleJDBC = capa;
        this.equip = e;
        this.fGestioEquips = f;
        try {
            String titolEquip = e.getNom() + "-" + obtenirTipus(e.getTipus()) + "-" + capaOracleJDBC.obtenirNomCategoriaPerId(e.getIdCategoria());
            lblCatNom.setText(titolEquip);
        } catch (GestorBDClubEsportiuException ex) {
            Logger.getLogger(FitxaEquip.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            carregarDadesJugadorsEquip();
            carregarDadesJugadorsSenseEquip();
        } catch (GestorBDClubEsportiuException ex) {
            Logger.getLogger(FitxaEquip.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void carregarDadesJugadorsSenseEquip() throws GestorBDClubEsportiuException {
        jugadorsDisponibles = capaOracleJDBC.obtenirJugadorsDisponibles(this.equip.getId(), this.equip.getIdAny());
        omplirTaulaJugadorsSenseEquip(jugadorsDisponibles);

        // Verificar que la columna 3 aún existe antes de intentar eliminarla
        if (tbJugDisponible.getColumnModel().getColumnCount() > 3) {
            TableColumn idColumn = tbJugDisponible.getColumnModel().getColumn(3);
            tbJugDisponible.removeColumn(idColumn);
        }

        tbJugDisponible.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void carregarDadesJugadorsEquip() throws GestorBDClubEsportiuException {
        jugadoresEquipo = capaOracleJDBC.obtenirJugadorsPerIdEquip(this.equip.getId());
        omplirTaula(jugadoresEquipo);

        // Verifica si la columna ya ha sido eliminada antes de intentarlo nuevamente
        if (tbJugEquip.getColumnModel().getColumnCount() > 3) {
            TableColumn idColumn = tbJugEquip.getColumnModel().getColumn(3);
            tbJugEquip.removeColumn(idColumn);
        }

        tbJugEquip.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void omplirTaula(List<Jugador> jugadors) {
        DefaultTableModel model = (DefaultTableModel) tbJugEquip.getModel();

        model.setRowCount(0); // Limpiar la tabla

        for (Jugador j : jugadors) {
            model.addRow(new Object[]{
                j.getIdLegal(),
                j.getNom(),
                j.getTitularitat().trim(),
                j // Guardar el objeto completo
            });
        }
    }

    private Jugador obtenirJugadorEquipActual() {
        int selectedRow = tbJugEquip.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = tbJugEquip.convertRowIndexToModel(selectedRow);
            Object jugadorObject = ((DefaultTableModel) tbJugEquip.getModel()).getValueAt(modelRow, 3);

            if (jugadorObject instanceof Jugador) {
                return (Jugador) jugadorObject;
            }
        }
        System.out.println("Cap jugador seleccionat.");
        return null;
    }

    private void omplirTaulaJugadorsSenseEquip(List<Jugador> jugadors) {
        DefaultTableModel model = (DefaultTableModel) tbJugDisponible.getModel();

        model.setRowCount(0);

        for (Jugador j : jugadors) {
            model.addRow(new Object[]{
                j.getIdLegal(),
                j.getNom(),
                j.getNomCategoria(),
                j // Guardar el objeto completo
            });
        }
    }

    private Jugador obtenirJugadorNou() {
        int selectedRow = tbJugDisponible.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = tbJugDisponible.convertRowIndexToModel(selectedRow);
            Object jugadorObject = ((DefaultTableModel) tbJugDisponible.getModel()).getValueAt(modelRow, 3);

            if (jugadorObject instanceof Jugador) {
                return (Jugador) jugadorObject;
            }
        }
        System.out.println("Cap jugador seleccionat.");
        return null;
    }

    private String obtenirTipus(Character tipus) {
        String tipusText;
        switch (tipus) {
            case 'D':
                tipusText = "FEMENI";
                break;
            case 'H':
                tipusText = "MASCULI";
                break;
            case 'M':
                tipusText = "MIX";
                break;
            default:
                tipusText = "desconegut";
        }
        return tipusText;
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
        tbJugEquip = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbJugDisponible = new javax.swing.JTable();
        tornarEnrere = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblCatNom = new javax.swing.JLabel();
        tbJugadorsEquip = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnAfegeix = new javax.swing.JButton();
        btnEsborrar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtNom = new javax.swing.JTextField();
        txtNif = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboCategoria = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestió Plantilla");
        setPreferredSize(new java.awt.Dimension(820, 480));

        tbJugEquip.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "NIF", "Nom", "Tital o Convidat", "ID"
            }
        ));
        jScrollPane1.setViewportView(tbJugEquip);

        tbJugDisponible.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "NIF", "Nom", "Categoria", "ID"
            }
        ));
        jScrollPane2.setViewportView(tbJugDisponible);

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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("Assignació dels jugadors Equip:");

        lblCatNom.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblCatNom.setText("Categoria-Nom-Tipus");

        tbJugadorsEquip.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tbJugadorsEquip.setText("Jugadors  del Equip");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Jugadors ");

        btnAfegeix.setText("Afegeix->");
        btnAfegeix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAfegeixActionPerformed(evt);
            }
        });

        btnEsborrar.setText("<-Esborrar");
        btnEsborrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEsborrarActionPerformed(evt);
            }
        });

        jLabel5.setText("Nom:");

        txtNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomActionPerformed(evt);
            }
        });

        jLabel6.setText("NIF:");

        jLabel3.setText("Categoria:");

        comboCategoria.setMaximumRowCount(6);
        comboCategoria.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        comboCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCategoriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(jLabel4))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEsborrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAfegeix, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(tbJugadorsEquip)
                        .addGap(101, 101, 101)))
                .addGap(24, 24, 24))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(tornarEnrere))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(lblCatNom))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNom, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtNif, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(156, 156, 156)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblCatNom))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbJugadorsEquip)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAfegeix)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEsborrar)
                        .addGap(105, 105, 105)))
                .addComponent(tornarEnrere, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tornarEnrereMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tornarEnrereMouseClicked
        this.fGestioEquips.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_tornarEnrereMouseClicked

    private void btnAfegeixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAfegeixActionPerformed
        Jugador j = obtenirJugadorNou();
        Membre membresEquip;
        if (j == null) {
            JOptionPane.showMessageDialog(this, "No has seleccionat cap jugador per afegir l'equip",
                    "Error de selecció de jugadors", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (j.getSexe() != equip.getTipus() && equip.getTipus() != 'M') {
            JOptionPane.showMessageDialog(this, "No hi ha concordança del sexe del jugador amb la categoria",
                    "Error verifica la concordança del sexe", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            //Esto podria mejorarse
            Categoria cat = capaOracleJDBC.obtenirCategoriaPerId(equip.getIdCategoria());
            if (!validaCategoriaJugador(cat, j.getDataNaixement())) {
                JOptionPane.showMessageDialog(this, "L'equip seleccionats sempre ha de ser de la mateixa categoria que el jugador o alternativament una superior",
                        "Error categoria jugador", JOptionPane.ERROR_MESSAGE);
                return;
            }
            membresEquip = new Membre("TITULAR", equip.getId(), j.getId());
            capaOracleJDBC.afegirMembre(membresEquip);
            capaOracleJDBC.confirmarCanvis();
            JOptionPane.showMessageDialog(null, "El jugador s'ha incorporat a l'equip correctament.");
            carregarDadesJugadorsEquip();
            carregarDadesJugadorsSenseEquip();
        } catch (GestorBDClubEsportiuException ex) {
            Logger.getLogger(FitxaEquip.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExceptionClub ex) {
            Logger.getLogger(FitxaEquip.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnAfegeixActionPerformed
    private boolean validaCategoriaJugador(Categoria cat, LocalDate jData) {
        int edatJugador = equip.getIdAny() - jData.getYear();

        if (edatJugador >= cat.getEdatMin() && edatJugador <= cat.getEdatMax()) {
            return true;
        }

        if (edatJugador < cat.getEdatMin()) {
            return true;
        }

        return false;

    }
    private void txtNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomActionPerformed

    private void comboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCategoriaActionPerformed

    }//GEN-LAST:event_comboCategoriaActionPerformed

    private void btnEsborrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEsborrarActionPerformed
        Jugador j = obtenirJugadorEquipActual();
        Membre mEquip;
        if (j == null) {
            JOptionPane.showMessageDialog(this, "No has seleccionat cap jugador per afegir l'equip",
                    "Error de selecció de jugadors", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            mEquip = capaOracleJDBC.obtenirUnMembre(equip.getId(), j.getId());
            capaOracleJDBC.eliminarMembre(mEquip);
            capaOracleJDBC.confirmarCanvis();
            JOptionPane.showMessageDialog(null, "Jugador eliminat del equip correctament.");
            carregarDadesJugadorsEquip();
            carregarDadesJugadorsSenseEquip();
        } catch (GestorBDClubEsportiuException ex) {
            Logger.getLogger(FitxaEquip.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnEsborrarActionPerformed

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
//            java.util.logging.Logger.getLogger(FitxaEquip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FitxaEquip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FitxaEquip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FitxaEquip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FitxaEquip().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAfegeix;
    private javax.swing.JButton btnEsborrar;
    private javax.swing.JComboBox<Categoria > comboCategoria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCatNom;
    private javax.swing.JTable tbJugDisponible;
    private javax.swing.JTable tbJugEquip;
    private javax.swing.JLabel tbJugadorsEquip;
    private javax.swing.JLabel tornarEnrere;
    private javax.swing.JTextField txtNif;
    private javax.swing.JTextField txtNom;
    // End of variables declaration//GEN-END:variables

//    private void CargarJugadores() throws GestorBDClubEsportiuException {
//        jugadoresEquipo = capaOracleJDBC.obtenirJugadorsPerIdEquip(this.equip.getId());
//        omplirTaula(jugadoresEquipo);
//
////        for (Jugador j : jugadores) {
////            j.setTitular(capaOracleJDBC.esTitular(j.getId(), this.equip.getId()));
////            j.setCategoria(this.equip.getIdAny());
////        }
////        
////        
////        List<Jugador> jugadores = capaOracleJDBC.obtenirJugadorsNoIdEquip(this.equip.getId());
//    }
//    public void omplirTaula(List<Jugador> jugadors) {
//        DefaultTableModel model = (DefaultTableModel) tbJugEquip.getModel();
//
//        model.setRowCount(0);
//
//        for (Jugador j : jugadors) {
//            model.addRow(new Object[]{
//                j.getIdLegal(),
//                j.getNom(),
//                j.getTitularitat(),
//                j.getId()
//            });
//        }
//        tbJugEquip.getSelectionModel().addListSelectionListener(event -> {
//            if (!event.getValueIsAdjusting()) {
//                int selectedRow = tbJugEquip.getSelectedRow();
//                if (selectedRow != -1) {
//                    int modelRow = tbJugEquip.convertRowIndexToModel(selectedRow); // obtener el valor de la columna oculta, en lugar de usar el índice visual de la tabla.
//                    Object idValue = ((DefaultTableModel) tbJugEquip.getModel()).getValueAt(modelRow, 4); // Índice en el modelo
//
//                    idJugador = Integer.parseInt(idValue.toString());
//                    System.out.println("ID seleccionat: " + idJugador);
//
//                }
//            }
//        });
//    }
}
