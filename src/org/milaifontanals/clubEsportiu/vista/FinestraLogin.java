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
import org.milaifontanals.clubEsportiu.model.Temporada;
import org.milaifontanals.clubEsportiu.model.Usuari;
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
public class FinestraLogin extends javax.swing.JFrame {

    private IGestorBDClubEsportiu capaOracleJDBC = null;
    private Usuari usu = null;
   

    /**
     * Creates new form FinestraLogin
     *
     * @param con
     * @param usu
     */
    static private String nomClassePersistencia = null;
    private List<Temporada> tmp;

    /**
     * Creates new form Login
     *
     * @param usu1
     * @param capa
     */
    public FinestraLogin(Usuari usu1, IGestorBDClubEsportiu capa) {
        initComponents();
        this.usu = usu1;
        this.capaOracleJDBC = capa;
        lblInserirTemp.setVisible(false);

        try {

            // Configurar los elementos de la interfaz con los datos del usuario
            lblLogin.setText(usu.getLogin());
            lblNom.setText(usu.getNom());
//            comboTemporada.addItem(new Equip(""));
            carregarTemporades();
            System.out.println("Connexió establerta");
        } catch (GestorBDClubEsportiuException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Inicializar los componentes gráficos
    }

    private void carregarTemporades() throws GestorBDClubEsportiuException {
        comboTemporada.removeAllItems();
        tmp = capaOracleJDBC.obtenirTemporades();
        for (Iterator<Temporada> iterator = tmp.iterator(); iterator.hasNext();) {
            Temporada next = iterator.next();
            comboTemporada.addItem(next);

        }

    }

    public FinestraLogin(IGestorBDClubEsportiu capa) {
        this.capaOracleJDBC = capa;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        comboTemporada = new javax.swing.JComboBox<>();
        btnAfTemporada = new javax.swing.JButton();
        txtTemporada = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblInserirTemp = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblLogin = new javax.swing.JLabel();
        lblNom = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnEquips = new javax.swing.JButton();
        btnJugadors = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        tornarEnrere = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestió Temporades");
        setPreferredSize(new java.awt.Dimension(840, 480));
        setSize(new java.awt.Dimension(0, 0));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 102));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Temporades");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 262, -1));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Dades Usuari");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, 262, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 102), 3, true));

        comboTemporada.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        comboTemporada.setForeground(new java.awt.Color(0, 102, 102));
        comboTemporada.setMaximumRowCount(6);
        comboTemporada.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        comboTemporada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTemporadaActionPerformed(evt);
            }
        });

        btnAfTemporada.setBackground(new java.awt.Color(0, 102, 102));
        btnAfTemporada.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnAfTemporada.setForeground(new java.awt.Color(255, 255, 255));
        btnAfTemporada.setText("Afegir Temporada");
        btnAfTemporada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAfTemporadaActionPerformed(evt);
            }
        });

        txtTemporada.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtTemporada.setForeground(new java.awt.Color(0, 102, 102));
        txtTemporada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTemporadaActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Temporada: ");

        lblInserirTemp.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblInserirTemp.setForeground(new java.awt.Color(255, 0, 51));
        lblInserirTemp.setText("Error Al Inserir temporada");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblInserirTemp))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txtTemporada, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(comboTemporada, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(189, 189, 189))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btnAfTemporada, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(comboTemporada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTemporada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(btnAfTemporada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblInserirTemp)
                .addGap(24, 24, 24)
                .addComponent(jLabel7)
                .addGap(112, 112, 112))
        );

        jPanel4.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 328));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 102), 4, true));

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("Login:");

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("Nom:");

        lblLogin.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblLogin.setForeground(new java.awt.Color(0, 102, 102));
        lblLogin.setText("jLabel4");

        lblNom.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblNom.setForeground(new java.awt.Color(0, 102, 102));
        lblNom.setText("jLabel4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLogin)
                    .addComponent(lblNom))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblLogin))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblNom))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, 270, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 102), 3, true));

        btnEquips.setBackground(new java.awt.Color(0, 102, 102));
        btnEquips.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnEquips.setForeground(new java.awt.Color(255, 255, 255));
        btnEquips.setText("Gestió Equips");
        btnEquips.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEquipsActionPerformed(evt);
            }
        });

        btnJugadors.setBackground(new java.awt.Color(0, 102, 102));
        btnJugadors.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnJugadors.setForeground(new java.awt.Color(255, 255, 255));
        btnJugadors.setText("Gestió Jugadors");
        btnJugadors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJugadorsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnJugadors, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEquips, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(72, 72, 72))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(btnEquips, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(27, 27, 27)
                .addComponent(btnJugadors)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 250, 271, -1));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Escull una opció");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 220, -1, -1));

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
        jPanel4.add(tornarEnrere, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 100, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 815, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void comboTemporadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTemporadaActionPerformed

    }//GEN-LAST:event_comboTemporadaActionPerformed

    private void btnEquipsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEquipsActionPerformed
        Temporada temporada = (Temporada) comboTemporada.getSelectedItem();
        GestioEquips fGestioEquips = new GestioEquips(capaOracleJDBC, temporada,this);
        fGestioEquips.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnEquipsActionPerformed

    private void btnJugadorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJugadorsActionPerformed
        Temporada temporada = (Temporada) comboTemporada.getSelectedItem();
        GestioJugadors fGestioJugador = new GestioJugadors(capaOracleJDBC, temporada,this);
        fGestioJugador.setVisible(true);      
        this.setVisible(false);
    }//GEN-LAST:event_btnJugadorsActionPerformed

    private void btnAfTemporadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAfTemporadaActionPerformed
        List<Temporada> lltemp = null;
        int temp = Integer.parseInt(txtTemporada.getText());
        // Validar si los campos están vacíos
        if (txtTemporada.getText().isEmpty() || temp > 2030 || temp < 1900) {
            JOptionPane.showMessageDialog(this, "Error. El valor introduit es incorrecte", "Error", JOptionPane.ERROR_MESSAGE);
            lblInserirTemp.setVisible(true);
        } else {
            try {
                lltemp = capaOracleJDBC.obtenirTemporades();
                //Metodo para validar si existe una temporada con ese año
                boolean exists = lltemp.stream()
                        .anyMatch(t -> t.getAny_temp() == temp);
                if (!exists) {
                    capaOracleJDBC.afegirTemporada(new Temporada(temp));
                    capaOracleJDBC.confirmarCanvis();
                    JOptionPane.showMessageDialog(this, "Temporada afegida correctament.", "Éxit", JOptionPane.INFORMATION_MESSAGE);
                    carregarTemporades();
                } else {
                    JOptionPane.showMessageDialog(this, "Error. Aquesta temporada ja existeix", "Error", JOptionPane.ERROR_MESSAGE);

                }
            } catch (GestorBDClubEsportiuException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_btnAfTemporadaActionPerformed

    private void txtTemporadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTemporadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTemporadaActionPerformed

    private void tornarEnrereMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tornarEnrereMouseClicked
        Login log = new Login();
        log.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_tornarEnrereMouseClicked

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
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FinestraLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FinestraLogin(usu).setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAfTemporada;
    private javax.swing.JButton btnEquips;
    private javax.swing.JButton btnJugadors;
    private javax.swing.JComboBox<Temporada > comboTemporada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblInserirTemp;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblNom;
    private javax.swing.JLabel tornarEnrere;
    private javax.swing.JTextField txtTemporada;
    // End of variables declaration//GEN-END:variables
}
