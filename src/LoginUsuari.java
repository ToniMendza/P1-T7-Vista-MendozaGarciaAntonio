/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import org.milaifontanals.clubEsportiu.persistencia.IGestorBDClubEsportiu;

/**
 *
 * @author isard
 */
public class LoginUsuari extends JFrame {

    private IGestorBDClubEsportiu conBD = null;
    static private String nomClassePersistencia = null;
    private JFrame f;   // Pantalla principal

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Cal passar el nom de la classe que dona la persistència com a primer argument");
            System.exit(0);
        }
        nomClassePersistencia = args[0];
        LoginUsuari mp = new LoginUsuari();
        mp.carregaLogin();

    }

    private void carregaLogin() {
        JFrame f = new JFrame("Inici de sessio");
      
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20,20));
        
        
        
        
                f.pack();
        // Per centrar la finestra a la pantalla
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        try {
            // Disseny de la interfície
            conBD = (IGestorBDClubEsportiu) Class.forName(nomClassePersistencia).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(LoginUsuari.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
