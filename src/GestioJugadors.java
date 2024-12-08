
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
//import org.milaifontanals.clubEsportiu.jdbc.GestorBDClubEsportiuJdbc;
import org.milaifontanals.clubEsportiu.model.Categoria;
import org.milaifontanals.clubEsportiu.model.Jugador;
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
public class GestioJugadors extends javax.swing.JFrame {

    private IGestorBDClubEsportiu capaOracleJDBC = null;
//    private GestorBDClubEsportiuJdbc capaOracleJDBC = null;
    private List<Categoria> llCategories;
    private Jugador jugador;
    private List<Jugador> jugadors;
    private static final int ID_JUGADOR_INDEX = 5;
    private Temporada temp;

    /**
     * Creates new form GestioJugadors
     */
    public GestioJugadors(IGestorBDClubEsportiu capa, Temporada temporada) {
        try {
            initComponents();

            this.capaOracleJDBC = capa;
            this.temp = temporada;
            lblFormat.setVisible(false);
            lblFormatNif.setVisible(false);
            lblInserir.setVisible(false);
            lblesborrar.setVisible(false);

            llCategories = capaOracleJDBC.obtenirCategories("");
            comboCategoria.addItem(new Categoria());
            for (Iterator<Categoria> iterator = llCategories.iterator(); iterator.hasNext();) {
                Categoria next = iterator.next();
                comboCategoria.addItem(next);

            }
            TableColumn idColumn = tableJugador.getColumnModel().getColumn(5);
            tableJugador.removeColumn(idColumn);
//        TableCellRenderer renderizadorConBorde = new DefaultTableCellRenderer() {
//    @Override
//    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//
//        // Aplicar borde negro a cada celda
//        if (c instanceof JLabel) {
//            ((JLabel) c).setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//        }
//
//        return c;
//    }
//};

// Asignar el renderizador personalizado a todas las columnas de la tabla
//for (int i = 0; i < jTable1.getColumnCount(); i++) {
//    jTable1.getColumnModel().getColumn(i).setCellRenderer(renderizadorConBorde);
//}
        } catch (GestorBDClubEsportiuException ex) {
            Logger.getLogger(GestioJugadors.class.getName()).log(Level.SEVERE, null, ex);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        tornarEnrere = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNaixement = new javax.swing.JTextField();
        txtNom = new javax.swing.JTextField();
        txtNif = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        rbCognom = new javax.swing.JRadioButton();
        rbNaixement = new javax.swing.JRadioButton();
        btnModificar = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableJugador = new javax.swing.JTable();
        comboCategoria = new javax.swing.JComboBox<>();
        btnCercar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        lblFormat = new javax.swing.JLabel();
        lblFormatNif = new javax.swing.JLabel();
        lblInserir = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnEsborrar = new javax.swing.JButton();
        lblInserir1 = new javax.swing.JLabel();
        lblesborrar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestió Jugador");

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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Filtrar Per:");

        jLabel2.setText("Data Naixement: ");

        jLabel3.setText("Categoria:");

        jLabel4.setText("Nom:");

        jLabel5.setText("NIF:");

        txtNaixement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNaixementActionPerformed(evt);
            }
        });

        txtNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Ordenar per:");

        buttonGroup1.add(rbCognom);
        rbCognom.setSelected(true);
        rbCognom.setText("cognom");
        rbCognom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCognomActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbNaixement);
        rbNaixement.setText("data de naixement");
        rbNaixement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNaixementActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.setEnabled(false);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnExportar.setText("Exportar Jugadors");

        tableJugador.setAutoCreateRowSorter(true);
        tableJugador.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tableJugador.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Nom", "Sexe", "Data Naixement", "NIF", "Categoria", "ID", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableJugador);

        comboCategoria.setMaximumRowCount(6);
        comboCategoria.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        comboCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCategoriaActionPerformed(evt);
            }
        });

        btnCercar.setText("Cercar");
        btnCercar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCercarActionPerformed(evt);
            }
        });

        jLabel7.setText("dd/mm/yyyy");

        lblFormat.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        lblFormat.setForeground(new java.awt.Color(255, 0, 51));
        lblFormat.setText("Format Incorrecte");

        lblFormatNif.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        lblFormatNif.setForeground(new java.awt.Color(255, 0, 51));
        lblFormatNif.setText("NIF Incorrecte");

        lblInserir.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        lblInserir.setForeground(new java.awt.Color(255, 0, 51));
        lblInserir.setText("Escull Un Jugador");

        jButton1.setText("Inserir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnEsborrar.setText("Esborrar");
        btnEsborrar.setEnabled(false);
        btnEsborrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEsborrarActionPerformed(evt);
            }
        });

        lblInserir1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblInserir1.setForeground(new java.awt.Color(255, 0, 51));

        lblesborrar.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        lblesborrar.setForeground(new java.awt.Color(255, 0, 51));
        lblesborrar.setText("Escull Un Jugador");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(141, 141, 141)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(btnEsborrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnModificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblInserir)
                                            .addComponent(lblInserir1)
                                            .addComponent(lblesborrar))
                                        .addGap(39, 39, 39))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(rbCognom)
                                            .addComponent(rbNaixement))
                                        .addContainerGap())))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tornarEnrere, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnExportar)
                                .addGap(72, 72, 72))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(txtNom, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtNif, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblFormatNif, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtNaixement)
                                                .addComponent(btnCercar, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(12, 12, 12)
                                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(373, 373, 373)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFormat)
                                .addContainerGap())))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbCognom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbNaixement))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtNaixement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblFormat)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(75, 75, 75)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblInserir)
                                    .addComponent(btnModificar))
                                .addGap(7, 7, 7))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(lblFormatNif))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(btnCercar))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEsborrar)
                    .addComponent(lblInserir1)
                    .addComponent(lblesborrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tornarEnrere, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExportar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tornarEnrereMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tornarEnrereMouseClicked
        FinestraLogin log = new FinestraLogin(capaOracleJDBC);
        log.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_tornarEnrereMouseClicked

    private void txtNaixementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNaixementActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNaixementActionPerformed

    private void txtNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomActionPerformed

    private void rbCognomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCognomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbCognomActionPerformed

    private void rbNaixementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNaixementActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbNaixementActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
//select count(*) from membre where id_equip in (select id from equip where id_any=2024) and id_jugador=10;
//
//select * from membre m join equip e on m.id_equip=e.id where e.id_any=2024 and id_jugador=10;
//
//select * from membre m join equip e on m.id_equip=e.id;
//select * from jugador;

        try {
            // Recuperar el ID desde el modelo (columna 5 en este caso)
            int id = obtenirIDJugador();
            if (id != -1) {
                InserirModificarJugador log = new InserirModificarJugador(id, capaOracleJDBC, temp);
                log.setVisible(true);
                this.dispose();
                System.out.println("Jugador seleccionado: " + id);
            } else {
                lblInserir.setVisible(true);
                System.out.println("No se ha seleccionado ninguna fila.");

            }
        } catch (ClassCastException | ArrayIndexOutOfBoundsException e) {

            System.out.println("Error al recuperar el ID: " + e.getMessage());
        }


    }//GEN-LAST:event_btnModificarActionPerformed
    private int obtenirIDJugador() {
        int selectedRow = tableJugador.getSelectedRow();
        if (selectedRow == -1) {
            return -1; // No se seleccionó ninguna fila
        }

        int modelRow = tableJugador.convertRowIndexToModel(selectedRow);
        TableModel model = tableJugador.getModel();

        try {
            return (int) model.getValueAt(modelRow, ID_JUGADOR_INDEX); // Obtén el ID
        } catch (ClassCastException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error al recuperar el ID del jugador: " + e.getMessage());
            return -1; // Devuelve un valor inválido en caso de error
        }
    }
    private void comboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCategoriaActionPerformed

    }//GEN-LAST:event_comboCategoriaActionPerformed

    private void btnCercarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCercarActionPerformed

        Categoria categoriaSeleccionada = (Categoria) comboCategoria.getSelectedItem();
        String nomCategoria = categoriaSeleccionada.getNom();
        int idCategoria = 0;
        LocalDate data = null;
        String nomEquip = null;
        String nif = null;
        int ordreJugadors;

        if (categoriaSeleccionada != null) {
            idCategoria = categoriaSeleccionada.getId();

        }
        if (!txtNaixement.getText().isEmpty()) {
            LocalDate validaData = null;
            try {
                validaData = capaOracleJDBC.validarData(txtNaixement.getText().trim());
                lblFormat.setVisible(false);
            } catch (GestorBDClubEsportiuException ex) {
                Logger.getLogger(GestioJugadors.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (validaData == null) {
                JOptionPane.showMessageDialog(this, "La data introduïda no és vàlida. Format esperat: dd/MM/yyyy",
                        "Error de data", JOptionPane.ERROR_MESSAGE);
                lblFormat.setVisible(true);
                return; // Salir del método porque la fecha es inválida
            }
            data = validaData;

//            if (validaData == null) {
//                JOptionPane.showMessageDialog(this, "La data introduïda no és vàlida. Format esperat: dd/MM/yyyy",
//                        "Error de data", JOptionPane.ERROR_MESSAGE);
//                lblFormat.setVisible(true);
//                return; // Salir del método porque la fecha es inválida
//            }
//            
//            data = validaData;
        }
        if (!txtNom.getText().isEmpty()) {
            nomEquip = txtNom.getText().trim();
        }
        if (!txtNif.getText().isEmpty()) {
            if (txtNif.getText().trim().matches("^\\d{8}[A-Za-z]$")) {
                nif = txtNif.getText().trim();
                lblFormatNif.setVisible(false);
            } else {
                lblFormatNif.setVisible(true);
                return;
            }
        }
        ordreJugadors = rbCognom.isSelected() ? 1 : 0;

        try {
            jugadors = capaOracleJDBC.filtreJugadorCatDataNomNif(idCategoria, data != null ? java.sql.Date.valueOf(data) : null,
                    nomEquip != null ? nomEquip : null, nif, temp, ordreJugadors);

            omplirTaula(jugadors);

//            System.out.println(jugadors.toString());
//            System.out.println(data);
            btnModificar.setEnabled(true);
            btnEsborrar.setEnabled(true);
        } catch (GestorBDClubEsportiuException ex) {
            Logger.getLogger(GestioJugadors.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnCercarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int jugador = 0;
        InserirModificarJugador log = new InserirModificarJugador(jugador, capaOracleJDBC, temp);
        log.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnEsborrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEsborrarActionPerformed
        try {
            Jugador jugadorDel = new Jugador();
            int id = obtenirIDJugador();
            if (id != -1) {
                jugadorDel.setId(id);
                try {
                    if (capaOracleJDBC.jugadorSenseEquip(jugadorDel)) {
                        capaOracleJDBC.eliminarJugador(jugadorDel);
                        JOptionPane.showMessageDialog(null, "Jugador eliminat correctament");
                        //            capaOracleJDBC.confirmarCanvis();
                    } else {
                        JOptionPane.showMessageDialog(null, "Aquest jugador pertany a un equip, no és pot esborrar: ", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                } catch (GestorBDClubEsportiuException ex) {
                    Logger.getLogger(GestioJugadors.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                lblesborrar.setVisible(true);
                System.out.println("No se ha seleccionado ninguna fila.");

            }
        } catch (ClassCastException | ArrayIndexOutOfBoundsException e) {

            System.out.println("Error al recuperar el ID: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEsborrarActionPerformed

    public void omplirTaula(List<Jugador> jugadors) {
        DefaultTableModel model = (DefaultTableModel) tableJugador.getModel();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

// Formatea la fecha
        
        model.setRowCount(0);
        for (Jugador jugador : jugadors) {
            model.addRow(new Object[]{jugador.getNom(), jugador.getSexe(),jugador.getDataNaixement().format(formatter) , jugador.getIdLegal(), jugador.getNomCategoria(), jugador.getId()});
        }
    }
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
//            java.util.logging.Logger.getLogger(GestioJugadors.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(GestioJugadors.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(GestioJugadors.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(GestioJugadors.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new GestioJugadors().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCercar;
    private javax.swing.JButton btnEsborrar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnModificar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<Categoria > comboCategoria;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFormat;
    private javax.swing.JLabel lblFormatNif;
    private javax.swing.JLabel lblInserir;
    private javax.swing.JLabel lblInserir1;
    private javax.swing.JLabel lblesborrar;
    private javax.swing.JRadioButton rbCognom;
    private javax.swing.JRadioButton rbNaixement;
    private javax.swing.JTable tableJugador;
    private javax.swing.JLabel tornarEnrere;
    private javax.swing.JTextField txtNaixement;
    private javax.swing.JTextField txtNif;
    private javax.swing.JTextField txtNom;
    // End of variables declaration//GEN-END:variables
}
