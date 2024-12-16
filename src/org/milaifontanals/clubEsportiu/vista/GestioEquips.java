package org.milaifontanals.clubEsportiu.vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
//        this.tmp = temp;
        this.fLogin = f;

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
        TableColumn idColumn = tableEquips.getColumnModel().getColumn(3);
        tableEquips.removeColumn(idColumn);
        tableEquips.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        
        connectarJasperServer();

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

    private void informeJRS(Temporada codiProducte) throws IOException {
        int BUFFER_SIZE = 4096;
        String url = urlJRS + "ClubEsportiu4_1/Fitxaequip.pdf"
                + "?tmp=" + codiProducte;      // Emplenem el paràmetre "codi" de l'informe
        // Si hi ha més paràmetres a passar, cal concatenar-los com "&" com:
        // + "&nomParametre=valor&nomParametre=valor..."
        // L'extensió del fitxer (.pdf) indica que es vol en format .pdf
        // Altres formats possibles: .html, .xls, .rtf, .csv, .ods, .odt, .txt
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        String autenticacio = Base64.getEncoder().encodeToString((userJRS + ":" + passwordJRS).getBytes());
        con.setRequestProperty("Authorization", "Basic " + autenticacio);
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {

            //Hem de decidir quin nom volem per l'informe que acabem de generar
            String fileName;

            // En cas que vulguem crear un nom, possiblement a partir dels paràmetres
            // o d'altra informació, hem d'emplenar "filename" com correspongui.
            // Per exemple, en el cas que ens ocupa, podem fer:
            fileName = "FitxaProducte-" + codiProducte + ".pdf";
            // També hi podríem incorporar la descripció del  producte...
            // ATENCIÓ: En aquest cas, cal incorporar a "filename" l'extensió
            // que ha de coincidir amb el format indicat en la URL!!!

            // En cas que vulguem usar el nom de fitxer indicat a la URL,
            // comentem l'assignació anterior i descomentem les línies següents:
//            String disposition = con.getHeaderField("Content-Disposition");
//            String contentType = con.getContentType();
//            int contentLength = con.getContentLength();
////            System.out.println("Content-Type = " + contentType);
////            System.out.println("Content-Disposition = " + disposition);
////            System.out.println("Content-Length = " + contentLength);
//
//            if (disposition != null) {
//                // Obtenir el nom del fitxer a partir de la capçalera (Content-Disposition)
//                int index = disposition.indexOf("filename=");
//                if (index > 0) {
//                    fileName = disposition.substring(index + 10,
//                            disposition.length() - 1);
//                }
//            } else {
//                // Obtenir el nom del fitxer de dins la url indicada
//                int posArguments = url.lastIndexOf("?");
//                if (posArguments == -1) { // No hi ha arguments
//                    fileName = url.substring(url.lastIndexOf("/") + 1,
//                            url.length());
//                } else { // Hi ha arguments i cal eliminar-los per obtenir el nom del fitxer
//                    fileName = url.substring(url.lastIndexOf("/") + 1, posArguments);
//                }
//            }
            // Una vegada decidit el nom, seguim:
            // Obrim InputStream des de HTTP connection
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

    protected void cercarEquips() {
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
                    cell.setBackground(new Color(230, 230, 255)); // Color azul claro para categoría
                    cell.setFont(cell.getFont().deriveFont(Font.BOLD));
                } else {
                    cell.setBackground(Color.WHITE);
                    cell.setFont(cell.getFont().deriveFont(Font.PLAIN));
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
        txtInfo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestió Dels Equips");
        setPreferredSize(new java.awt.Dimension(840, 440));

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
        btnInforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformeActionPerformed(evt);
            }
        });

        tableEquips.setAutoCreateRowSorter(true);
        tableEquips.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
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
                        .addGap(99, 99, 99)
                        .addComponent(txtInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEsborrar)
                            .addComponent(txtInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(comboTemporada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
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
    }//GEN-LAST:event_jButton3ActionPerformed

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
        tempEscollida = (Temporada) comboTemporada.getSelectedItem();
        cercarEquips();
    }//GEN-LAST:event_comboTemporadaItemStateChanged

    private void btnInformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformeActionPerformed
        try {
            informeJRS(tempEscollida);
        } catch (IOException ex) {
            Logger.getLogger(GestioEquips.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnInformeActionPerformed

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
    private javax.swing.JTextField txtInfo;
    // End of variables declaration//GEN-END:variables
}
