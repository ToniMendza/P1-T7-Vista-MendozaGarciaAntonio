package org.milaifontanals.clubEsportiu.vista;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.time.LocalDate;
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
    private List<Categoria> llCategories;

    /**
     * Creates new form GestioPlantilla
     */
    public FitxaEquip(IGestorBDClubEsportiu capa, Equip e, GestioEquips f) {
        initComponents();
        this.capaOracleJDBC = capa;
        this.equip = e;
        this.fGestioEquips = f;
        lblFormatNif.setVisible(false);
        try {
            String titolEquip = e.getNom() + "-" + obtenirTipus(e.getTipus()) + "-" + capaOracleJDBC.obtenirNomCategoriaPerId(e.getIdCategoria());
            lblCatNom.setText(titolEquip);
        } catch (GestorBDClubEsportiuException ex) {
            Logger.getLogger(FitxaEquip.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            llCategories = capaOracleJDBC.obtenirCategories("");
            comboCategoria.addItem(new Categoria());
            for (Iterator<Categoria> iterator = llCategories.iterator(); iterator.hasNext();) {
                Categoria next = iterator.next();
                comboCategoria.addItem(next);

            }
            carregarDadesJugadorsEquip();
            jugadorsDisponibles = capaOracleJDBC.obtenirJugadorsDisponiblesAmbFiltres(this.equip.getId(), this.equip.getIdAny(), null, null, 0);

            carregarDadesJugadorsSenseEquip(jugadorsDisponibles);
        } catch (GestorBDClubEsportiuException ex) {
            Logger.getLogger(FitxaEquip.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void carregarDadesJugadorsSenseEquip(List<Jugador> jugDisponibles) throws GestorBDClubEsportiuException {
        omplirTaulaJugadorsSenseEquip(jugDisponibles);

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
                j 
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

        jPanel1 = new javax.swing.JPanel();
        lblCatNom = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        tornarEnrere = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnCercar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbJugEquip = new javax.swing.JTable();
        tbJugadorsEquip = new javax.swing.JLabel();
        btnEsborrar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbJugDisponible = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        btnAfegeix = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblFormatNif = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        comboCategoria = new javax.swing.JComboBox<>();
        txtNom = new javax.swing.JTextField();
        txtNif = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestió Plantilla");
        setPreferredSize(new java.awt.Dimension(850, 510));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(820, 400));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCatNom.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        lblCatNom.setForeground(new java.awt.Color(0, 102, 102));
        lblCatNom.setText("Categoria-Nom-Tipus");
        jPanel1.add(lblCatNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, -1, -1));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Assignació dels jugadors Equip:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, -1, -1));

        tornarEnrere.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        tornarEnrere.setForeground(new java.awt.Color(0, 102, 102));
        tornarEnrere.setText("Tornar Enrere");
        tornarEnrere.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 102, 102), new java.awt.Color(0, 102, 102), null, new java.awt.Color(0, 102, 102)));
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
        jPanel1.add(tornarEnrere, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 430, -1, 25));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 102), 3, true));

        btnCercar.setBackground(new java.awt.Color(0, 102, 102));
        btnCercar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnCercar.setForeground(new java.awt.Color(255, 255, 255));
        btnCercar.setText("Cercar");
        btnCercar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCercar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCercarActionPerformed(evt);
            }
        });

        tbJugEquip.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        tbJugEquip.setForeground(new java.awt.Color(0, 102, 102));
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

        tbJugadorsEquip.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        tbJugadorsEquip.setForeground(new java.awt.Color(0, 102, 102));
        tbJugadorsEquip.setText("Jugadors  del Equip");

        btnEsborrar.setBackground(new java.awt.Color(0, 102, 102));
        btnEsborrar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnEsborrar.setForeground(new java.awt.Color(255, 255, 255));
        btnEsborrar.setText("<-Esborrar");
        btnEsborrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEsborrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEsborrarActionPerformed(evt);
            }
        });

        tbJugDisponible.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        tbJugDisponible.setForeground(new java.awt.Color(0, 102, 102));
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

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Jugadors ");

        btnAfegeix.setBackground(new java.awt.Color(0, 102, 102));
        btnAfegeix.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnAfegeix.setForeground(new java.awt.Color(255, 255, 255));
        btnAfegeix.setText("Afegeix->");
        btnAfegeix.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAfegeix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAfegeixActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("Categoria:");

        lblFormatNif.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        lblFormatNif.setForeground(new java.awt.Color(255, 0, 51));
        lblFormatNif.setText("NIF Incorrecte");

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 102));
        jLabel5.setText("Nom:");

        comboCategoria.setForeground(new java.awt.Color(0, 102, 102));
        comboCategoria.setMaximumRowCount(6);
        comboCategoria.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        comboCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCategoriaActionPerformed(evt);
            }
        });

        txtNom.setFont(new java.awt.Font("Roboto", 2, 12)); // NOI18N
        txtNom.setForeground(new java.awt.Color(0, 102, 102));
        txtNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomActionPerformed(evt);
            }
        });

        txtNif.setFont(new java.awt.Font("Roboto", 2, 12)); // NOI18N
        txtNif.setForeground(new java.awt.Color(0, 102, 102));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("NIF:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(jLabel4)
                        .addGap(387, 387, 387)
                        .addComponent(tbJugadorsEquip))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(19, 19, 19)
                                    .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(30, 30, 30)
                                            .addComponent(txtNom, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(26, 26, 26)
                                            .addComponent(txtNif, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(11, 11, 11)
                                            .addComponent(lblFormatNif, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAfegeix, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEsborrar)
                            .addComponent(btnCercar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtNif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFormatNif))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCercar)))
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(tbJugadorsEquip))
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnAfegeix)
                        .addGap(28, 28, 28)
                        .addComponent(btnEsborrar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 40, 860, 380));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, 840, 470));

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

            jugadorsDisponibles = capaOracleJDBC.obtenirJugadorsDisponiblesAmbFiltres(this.equip.getId(), this.equip.getIdAny(), null, null, 0);
            carregarDadesJugadorsSenseEquip(jugadorsDisponibles);
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

            jugadorsDisponibles = capaOracleJDBC.obtenirJugadorsDisponiblesAmbFiltres(this.equip.getId(), this.equip.getIdAny(), null, null, 0);
            carregarDadesJugadorsSenseEquip(jugadorsDisponibles);
        } catch (GestorBDClubEsportiuException ex) {
            Logger.getLogger(FitxaEquip.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnEsborrarActionPerformed

    private void btnCercarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCercarActionPerformed
        Categoria categoriaSeleccionada = (Categoria) comboCategoria.getSelectedItem();
        int idCategoria = 0;
        String nomEquip = null;
        String nif = null;
        lblFormatNif.setVisible(false);
        int ordreJugadors;

        if (categoriaSeleccionada != null) {
            idCategoria = categoriaSeleccionada.getId();

        }
        if (!txtNom.getText().isEmpty()) {
            nomEquip = txtNom.getText().trim();
        }
        if (!txtNif.getText().isEmpty()) {
            if (txtNif.getText().trim().matches("^\\d{8}[A-Za-z]$")) {
                nif = txtNif.getText().trim();
            } else {
                lblFormatNif.setVisible(true);
                return;
            }
        }
        try {
            jugadorsDisponibles = capaOracleJDBC.obtenirJugadorsDisponiblesAmbFiltres(this.equip.getId(), this.equip.getIdAny(), nomEquip, nif, idCategoria);
            carregarDadesJugadorsSenseEquip(jugadorsDisponibles);
        } catch (GestorBDClubEsportiuException ex) {
            Logger.getLogger(FitxaEquip.class.getName()).log(Level.SEVERE, null, ex);
        }

        


    }//GEN-LAST:event_btnCercarActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAfegeix;
    private javax.swing.JButton btnCercar;
    private javax.swing.JButton btnEsborrar;
    private javax.swing.JComboBox<Categoria > comboCategoria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCatNom;
    private javax.swing.JLabel lblFormatNif;
    private javax.swing.JTable tbJugDisponible;
    private javax.swing.JTable tbJugEquip;
    private javax.swing.JLabel tbJugadorsEquip;
    private javax.swing.JLabel tornarEnrere;
    private javax.swing.JTextField txtNif;
    private javax.swing.JTextField txtNom;
    // End of variables declaration//GEN-END:variables

}
