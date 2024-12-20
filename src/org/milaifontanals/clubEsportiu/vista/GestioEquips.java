package org.milaifontanals.clubEsportiu.vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.milaifontanals.clubEsportiu.model.Categoria;
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
    private List<Categoria> llCategories;
    private Temporada tempEscollida = null;
    private Equip equipEscollit = null;
    private Categoria catEscollida = null;
    // Variables per dades de connexió amb JRS
    private String urlJRS;
    private String userJRS;
    private String passwordJRS;

    /**
     * Creates new form GestioEquips
     */
    public GestioEquips(IGestorBDClubEsportiu capa, Temporada temp, FinestraLogin f) {
        initComponents();

        this.capaOracleJDBC = capa;
        this.fLogin = f;

        try {
            llTemporades = capaOracleJDBC.obtenirTemporades();
            comboTemporada.addItem(null);
            int anyActual = LocalDate.now().getYear();
            int indexActual = 0;

            for (int i = 0; i < llTemporades.size(); i++) {
                Temporada next = llTemporades.get(i);
                comboTemporada.addItem(next);

                if (next.getAny_temp() == anyActual) {
                    indexActual = i + 1;
                }
            }
            comboTemporada.setSelectedIndex(indexActual);
            tempEscollida = (Temporada) comboTemporada.getSelectedItem();

            llEquips = capaOracleJDBC.obtenirEquipsPerTempEquiCat(0, null, 0);
            comboNomEquip.addItem(new Equip());
            for (Iterator<Equip> iterator = llEquips.iterator(); iterator.hasNext();) {
                Equip next = iterator.next();
                comboNomEquip.addItem(next);

            }

            llCategories = capaOracleJDBC.obtenirCategories("");
            comboCategoria.addItem(new Categoria());
            for (Iterator<Categoria> iterator = llCategories.iterator(); iterator.hasNext();) {
                Categoria next = iterator.next();
                comboCategoria.addItem(next);

            }
        } catch (GestorBDClubEsportiuException ex) {
            Logger.getLogger(GestioEquips.class.getName()).log(Level.SEVERE, null, ex);
        }
        cercarEquips();
        TableColumn idColumn = tableEquips.getColumnModel().getColumn(3);
        tableEquips.removeColumn(idColumn);
        tableEquips.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        connectarJasperServer();

    }

    protected void cercarEquips() {
        try {
            int tmp = (tempEscollida != null) ? tempEscollida.getAny_temp() : 0;
            int idCategoria = (catEscollida != null) ? catEscollida.getId() : 0;
            String nomEquip = (equipEscollit != null) ? equipEscollit.getNom() : "";

            llEquips = capaOracleJDBC.obtenirEquipsPerTempEquiCat(tmp, nomEquip, idCategoria);
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
                    e.getId()
                });
            } catch (GestorBDClubEsportiuException ex) {
                Logger.getLogger(GestioEquips.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        tableEquips.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (table.getValueAt(row, 1) == null || table.getValueAt(row, 1).toString().trim().isEmpty()) {
                    cell.setBackground(new Color(0, 102, 102, 220));
                    cell.setFont(cell.getFont().deriveFont(Font.BOLD));
                    cell.setForeground(Color.WHITE);
                } else {

                    cell.setBackground(Color.WHITE);
                    cell.setFont(cell.getFont().deriveFont(Font.PLAIN));
                    cell.setForeground(Color.BLACK);
                }

                if (isSelected) {
                    cell.setBackground(new Color(184, 207, 229));
                }

                return cell;
            }
        });

        tableEquips.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int selectedRow = tableEquips.getSelectedRow();
                if (selectedRow != -1) {
                    int modelRow = tableEquips.convertRowIndexToModel(selectedRow);
                    Object idValue = ((DefaultTableModel) tableEquips.getModel()).getValueAt(modelRow, 3);

                    if (idValue == null || idValue.toString().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(
                                this, "Has seleccionat una fila separadora. Si us plau, selecciona un equip vàlid.", "Selecció no vàlida",
                                JOptionPane.WARNING_MESSAGE);
                        tableEquips.clearSelection();
                    } else {
                        idEquip = Integer.parseInt(idValue.toString());
                    }
                }
            }
        });
    }

    private void connectarJasperServer() {
        if (capaOracleJDBC != null) {
            String fitxerConfigJRS = "informesJRS.properties";
            try {
                Properties props = new Properties();
                props.load(new FileReader(fitxerConfigJRS));
                String[] claus = {"url", "user", "password"};
                String[] valors = new String[3];

                for (int i = 0; i < claus.length; i++) {
                    valors[i] = props.getProperty(claus[i]);
                    if (valors[i] == null || valors[i].isEmpty()) {
                        txtInfo.setText(txtInfo.getText() + "\nNo es troba clau " + valors[i] + " en fitxer " + fitxerConfigJRS);
                    }
                }
                urlJRS = valors[0];
                userJRS = valors[1];
                passwordJRS = valors[2];
                txtInfo.setText(txtInfo.getText() + "\nParàmetres per connectar amb JRS recuperats.");
            } catch (FileNotFoundException ex) {
                txtInfo.setText(txtInfo.getText() + "\nNo es troba fitxer " + fitxerConfigJRS + " - No es podrà executar cap informe");
            } catch (IOException ex) {
                txtInfo.setText(txtInfo.getText() + "\n" + infoError(ex) + " - Probablement no es podrà executar cap informe");
            }
        }
    }

    private void informeJRS(Temporada tmpEscollida, Equip equiEscollit, Categoria catEscollida) throws IOException {
        int BUFFER_SIZE = 4096;
        String url = urlJRS + "ClubEsportiuFinal.pdf";
        boolean hasParams = false;
        if (tmpEscollida != null) {
            url += (hasParams ? "&" : "?") + "tmp=" + tmpEscollida.getAny_temp();
            hasParams = true;
        }
        if (catEscollida.getNom() != null) {
            url += (hasParams ? "&" : "?") + "catId=" + catEscollida.getId();
            hasParams = true;
        }
        if (equiEscollit.getNom() != null) {
            url += (hasParams ? "&" : "?") + "equipId=" + equiEscollit.getId();
        }

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        String autenticacio = Base64.getEncoder().encodeToString((userJRS + ":" + passwordJRS).getBytes());
        con.setRequestProperty("Authorization", "Basic " + autenticacio);
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {

            //Hem de decidir quin nom volem per l'informe que acabem de generar
            String fileName = "Informe";
            if (tmpEscollida != null) {
                fileName += "-Temporada" + tmpEscollida.getAny_temp();
            }
            if (catEscollida != null && catEscollida.getNom() != null) {
                fileName += "-Categoria" + catEscollida.getNom();
            }
            if (equiEscollit != null && equiEscollit.getNom() != null) {
                fileName += "-Equip" + equiEscollit.getNom();
            }
            fileName += ".pdf";

            InputStream inputStream = con.getInputStream();
            // Obrim OutputStream per enregistrar el fitxer
            FileOutputStream outputStream = new FileOutputStream(fileName);

            // Llegim de inputStrem i escrivima outputStream, byte a byte:
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();

            txtInfo.setText("Fitxer " + fileName + " descarregat");
            // Intentem obrir-lo en alguna aplicació del SO
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(new File(fileName));
                } catch (IOException ex) {
                    txtInfo.setText("No hi ha aplicacions disponibles per obrir el fitxer");
                }
            }


        } else {
            txtInfo.setText("Mètode 'GET' : " + url);
            txtInfo.setText(txtInfo.getText() + "\nCodi resposta: " + responseCode);
            txtInfo.setText(txtInfo.getText() + "\nCap fitxer a descarregar");
        }
        con.disconnect();
    }

    private String infoError(Throwable ex) {
        String aux;
        String info = ex.getMessage();
        if (info != null) {
            info += "\n";
        }
        while (ex.getCause() != null) {
            aux = ex.getCause().getMessage();
            if (aux != null) {
                aux += "\n";
            }
            info = info + aux;
            ex = ex.getCause();
        }
        return info;
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comboCategoria = new javax.swing.JComboBox<>();
        comboTemporada = new javax.swing.JComboBox<>();
        comboNomEquip = new javax.swing.JComboBox<>();
        btnInsert = new javax.swing.JButton();
        btnEsborrar = new javax.swing.JButton();
        btnFitxaEquip = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEquips = new javax.swing.JTable();
        txtInfo = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnInforme = new javax.swing.JButton();
        tornarEnrere = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestió Dels Equips");
        setPreferredSize(new java.awt.Dimension(825, 500));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 480));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 102));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Gestio Dels Equips");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 23, 262, -1));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("Equips:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 49, -1));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("Temporada: ");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Categoria:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 61, -1));

        comboCategoria.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        comboCategoria.setForeground(new java.awt.Color(0, 102, 102));
        comboCategoria.setMaximumRowCount(6);
        comboCategoria.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        comboCategoria.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboCategoriaItemStateChanged(evt);
            }
        });
        comboCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCategoriaActionPerformed(evt);
            }
        });
        jPanel1.add(comboCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 101, -1));

        comboTemporada.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        comboTemporada.setForeground(new java.awt.Color(0, 102, 102));
        comboTemporada.setMaximumRowCount(6);
        comboTemporada.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        comboTemporada.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboTemporadaItemStateChanged(evt);
            }
        });
        comboTemporada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTemporadaActionPerformed(evt);
            }
        });
        jPanel1.add(comboTemporada, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 101, -1));

        comboNomEquip.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        comboNomEquip.setForeground(new java.awt.Color(0, 102, 102));
        comboNomEquip.setMaximumRowCount(6);
        comboNomEquip.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        comboNomEquip.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboNomEquipItemStateChanged(evt);
            }
        });
        comboNomEquip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboNomEquipActionPerformed(evt);
            }
        });
        jPanel1.add(comboNomEquip, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 101, -1));

        btnInsert.setBackground(new java.awt.Color(0, 102, 102));
        btnInsert.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnInsert.setForeground(new java.awt.Color(255, 255, 255));
        btnInsert.setText("Crear");
        btnInsert.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });
        jPanel1.add(btnInsert, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 90, 148, -1));

        btnEsborrar.setBackground(new java.awt.Color(0, 102, 102));
        btnEsborrar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnEsborrar.setForeground(new java.awt.Color(255, 255, 255));
        btnEsborrar.setText("Esborrar");
        btnEsborrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEsborrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEsborrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEsborrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, 148, -1));

        btnFitxaEquip.setBackground(new java.awt.Color(0, 102, 102));
        btnFitxaEquip.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnFitxaEquip.setForeground(new java.awt.Color(255, 255, 255));
        btnFitxaEquip.setText("Fitxa Equip");
        btnFitxaEquip.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFitxaEquip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFitxaEquipActionPerformed(evt);
            }
        });
        jPanel1.add(btnFitxaEquip, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 170, 148, -1));

        tableEquips.setAutoCreateRowSorter(true);
        tableEquips.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tableEquips.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        tableEquips.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nom Equip", "Tipus", "Temporada", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableEquips);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 733, 198));

        txtInfo.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jPanel1.add(txtInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 420, 224, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 102), 4, true));

        btnInforme.setBackground(new java.awt.Color(0, 102, 102));
        btnInforme.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnInforme.setForeground(new java.awt.Color(255, 255, 255));
        btnInforme.setText("Informe");
        btnInforme.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformeActionPerformed(evt);
            }
        });

        tornarEnrere.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        tornarEnrere.setForeground(new java.awt.Color(0, 102, 102));
        tornarEnrere.setText("Tornar Enrere");
        tornarEnrere.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 102, 102), new java.awt.Color(0, 102, 102), null, new java.awt.Color(0, 102, 102)));
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(tornarEnrere)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 458, Short.MAX_VALUE)
                .addComponent(btnInforme, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(415, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInforme)
                    .addComponent(tornarEnrere))
                .addGap(15, 15, 15))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 460));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
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

    private void btnFitxaEquipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFitxaEquipActionPerformed
        if (tableEquips.getSelectedRow() != -1 && idEquip != 0) {
            Equip e = null;
            try {
                e = capaOracleJDBC.obtenirEquipPerId(idEquip);
                FitxaEquip f_FitxaEquip = new FitxaEquip(capaOracleJDBC, e, this);
                f_FitxaEquip.setVisible(true);
                this.setVisible(false);
            } catch (GestorBDClubEsportiuException ex) {
                Logger.getLogger(GestioEquips.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Error en eliminar l'equip. Si us plau, prova més tard.", "Error d'eliminació", JOptionPane.ERROR_MESSAGE);

            }
        } else {
            JOptionPane.showMessageDialog(
                    this, "Has de seleccionar algún equip per gestionar la seva Fitxa.", "Selecció no vàlida", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnFitxaEquipActionPerformed

    private void tornarEnrereMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tornarEnrereMouseClicked
        FinestraLogin log = new FinestraLogin(capaOracleJDBC);
        log.setVisible(true);
        fLogin.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_tornarEnrereMouseClicked

    private void btnEsborrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEsborrarActionPerformed
        Equip e = null;
        if (tableEquips.getSelectedRow() != -1 && idEquip != 0) {
            try {
                if (capaOracleJDBC.equipSenseJugadors(idEquip)) {
                    e = capaOracleJDBC.obtenirEquipPerId(idEquip);

                    capaOracleJDBC.eliminarUnEquip(e);
                    JOptionPane.showMessageDialog(this, "Equip sense Jugadors eliminat correctament.", "Eliminació exitosa", JOptionPane.INFORMATION_MESSAGE);

                    capaOracleJDBC.confirmarCanvis();
                    cercarEquips();
                } else {
                    int opcion = JOptionPane.showConfirmDialog(
                            this, "El Equip té jugadors assignats. Estàs segur que vols esborrar-lo?", "Confirmació d'eliminació",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (opcion == JOptionPane.YES_OPTION) {

                        try {
                            e = capaOracleJDBC.obtenirEquipPerId(idEquip);
                            capaOracleJDBC.eliminarEquipMembre(idEquip);

                            capaOracleJDBC.eliminarUnEquip(e);
                            capaOracleJDBC.confirmarCanvis();
                            JOptionPane.showMessageDialog(this, "Equip eliminat correctament.", "Eliminació exitosa", JOptionPane.INFORMATION_MESSAGE);
                            cercarEquips();
                        } catch (GestorBDClubEsportiuException ex) {
                            Logger.getLogger(GestioEquips.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(this, "Error en eliminar l'equip. Si us plau, prova més tard.", "Error d'eliminació", JOptionPane.ERROR_MESSAGE);

                        }
                    }
                }

            } catch (GestorBDClubEsportiuException ex) {
                Logger.getLogger(GestioEquips.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(
                    this, "Has de seleccionar algún equip per esborrar-lo.", "Selecció no vàlida", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_btnEsborrarActionPerformed

    private void comboTemporadaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboTemporadaItemStateChanged

    }//GEN-LAST:event_comboTemporadaItemStateChanged

    private void btnInformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformeActionPerformed
        try {
            informeJRS(tempEscollida, equipEscollit, catEscollida);
        } catch (IOException ex) {
            Logger.getLogger(GestioEquips.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnInformeActionPerformed

    private void comboTemporadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTemporadaActionPerformed
        tempEscollida = (Temporada) comboTemporada.getSelectedItem();
        cercarEquips();
    }//GEN-LAST:event_comboTemporadaActionPerformed

    private void comboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCategoriaActionPerformed

    }//GEN-LAST:event_comboCategoriaActionPerformed

    private void comboCategoriaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboCategoriaItemStateChanged
        catEscollida = (Categoria) comboCategoria.getSelectedItem();
        cercarEquips();
    }//GEN-LAST:event_comboCategoriaItemStateChanged

    private void comboNomEquipItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboNomEquipItemStateChanged
        equipEscollit = (Equip) comboNomEquip.getSelectedItem();
        cercarEquips();
    }//GEN-LAST:event_comboNomEquipItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEsborrar;
    private javax.swing.JButton btnFitxaEquip;
    private javax.swing.JButton btnInforme;
    private javax.swing.JButton btnInsert;
    private javax.swing.JComboBox<Categoria > comboCategoria;
    private javax.swing.JComboBox<Equip> comboNomEquip;
    private javax.swing.JComboBox<Temporada > comboTemporada;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableEquips;
    private javax.swing.JLabel tornarEnrere;
    private javax.swing.JTextField txtInfo;
    // End of variables declaration//GEN-END:variables
}
