
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
//import org.milaifontanals.clubEsportiu.jdbc.GestorBDClubEsportiuJdbc;
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
//    private GestorBDClubEsportiuJdbc capaOracleJDBC=null;
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
        // Guardamos el objeto Usuari recibido
        this.usu = usu1;
        this.capaOracleJDBC = capa;
        lblInserirTemp.setVisible(false);

        try {
            // Crear conexión a la base de datos (si es necesario)
//            capaOracleJDBC = new GestorBDClubEsportiuJdbc();

            // Configurar los elementos de la interfaz con los datos del usuario
            lblLogin.setText(usu.getLogin());
            lblNom.setText(usu.getNom());
//            comboTemporada.addItem(new Equip(""));
            tmp = capaOracleJDBC.obtenirTemporades();
            for (Iterator<Temporada> iterator = tmp.iterator(); iterator.hasNext();) {
                Temporada next = iterator.next();
                comboTemporada.addItem(next);

            }

            System.out.println("Connexió establerta");
        } catch (GestorBDClubEsportiuException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Inicializar los componentes gráficos
    }

    public FinestraLogin(IGestorBDClubEsportiu capa) {
        this.capaOracleJDBC=capa;
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblLogin = new javax.swing.JLabel();
        lblNom = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnEquips = new javax.swing.JButton();
        btnJugadors = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        comboTemporada = new javax.swing.JComboBox<>();
        btnEsbTemporada = new javax.swing.JButton();
        btnAfTemporada = new javax.swing.JButton();
        txtTemporada = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblInserirTemp = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tornarEnrere = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestió Temporades");
        setPreferredSize(new java.awt.Dimension(830, 480));
        setSize(new java.awt.Dimension(0, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Dades Usuari");

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Login:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Nom:");

        lblLogin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblLogin.setText("jLabel4");

        lblNom.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNom.setText("jLabel4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLogin)
                    .addComponent(lblNom))
                .addContainerGap(69, Short.MAX_VALUE))
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

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));

        btnEquips.setText("Gestió Equips");
        btnEquips.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEquipsActionPerformed(evt);
            }
        });

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

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Escull una opció");

        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));

        comboTemporada.setMaximumRowCount(6);
        comboTemporada.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        comboTemporada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTemporadaActionPerformed(evt);
            }
        });

        btnEsbTemporada.setText("Esborrar Temporada");
        btnEsbTemporada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEsbTemporadaActionPerformed(evt);
            }
        });

        btnAfTemporada.setText("Afegir Temporada");
        btnAfTemporada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAfTemporadaActionPerformed(evt);
            }
        });

        txtTemporada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTemporadaActionPerformed(evt);
            }
        });

        jLabel6.setText("Temporada: ");

        lblInserirTemp.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblInserirTemp.setForeground(new java.awt.Color(255, 0, 51));
        lblInserirTemp.setText("Error Al Inserir temporada");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(189, 189, 189))
            .addGroup(jPanel3Layout.createSequentialGroup()
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
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(comboTemporada, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAfTemporada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEsbTemporada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboTemporada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEsbTemporada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Temporades");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(tornarEnrere, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(81, 81, 81))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(26, 26, 26)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tornarEnrere, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void comboTemporadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTemporadaActionPerformed

    }//GEN-LAST:event_comboTemporadaActionPerformed

    private void btnEquipsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEquipsActionPerformed
        Temporada temporada=(Temporada) comboTemporada.getSelectedItem();
        GestioEquips fGestioEquips = new GestioEquips(capaOracleJDBC, temporada);
        fGestioEquips.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnEquipsActionPerformed

    private void btnJugadorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJugadorsActionPerformed
        Temporada temporada=(Temporada) comboTemporada.getSelectedItem();
        GestioJugadors fGestioJugador = new GestioJugadors(capaOracleJDBC,temporada);
        fGestioJugador.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnJugadorsActionPerformed

    private void btnEsbTemporadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEsbTemporadaActionPerformed

    }//GEN-LAST:event_btnEsbTemporadaActionPerformed

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
                    JOptionPane.showMessageDialog(this, "Temporada afegida correctament.", "Éxit", JOptionPane.INFORMATION_MESSAGE);
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
    private javax.swing.JButton btnEsbTemporada;
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
    private javax.swing.JLabel lblInserirTemp;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblNom;
    private javax.swing.JLabel tornarEnrere;
    private javax.swing.JTextField txtTemporada;
    // End of variables declaration//GEN-END:variables
}
