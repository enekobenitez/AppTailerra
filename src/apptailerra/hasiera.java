package apptailerra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class hasiera extends JFrame {
    private JTextField tfErabiltzailea;
    private JPasswordField pfPasahitza;
    private JButton btnSaioaHasi;
    private JButton btnErregistratu;
    private Connection connection;

    public hasiera() {
        setTitle("Saioa Hasi");
        setSize(855, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panela = new JPanel();
        panela.setBackground(new Color(0, 128, 255));
        getContentPane().add(panela);
        panela.setLayout(null);

        JLabel lblErabiltzailea = new JLabel("Erabiltzailea:");
        lblErabiltzailea.setBounds(247, 116, 77, 31);
        panela.add(lblErabiltzailea);
        tfErabiltzailea = new JTextField();
        tfErabiltzailea.setBounds(299, 157, 170, 38);
        tfErabiltzailea.setBackground(new Color(192, 192, 192));
        panela.add(tfErabiltzailea);

        JLabel lblPasahitza = new JLabel("Pasahitza:");
        lblPasahitza.setBounds(247, 205, 77, 31);
        panela.add(lblPasahitza);
        pfPasahitza = new JPasswordField();
        pfPasahitza.setBounds(299, 246, 170, 38);
        pfPasahitza.setBackground(new Color(192, 192, 192));
        panela.add(pfPasahitza);

        btnSaioaHasi = new JButton("Saioa Hasi");
        btnSaioaHasi.setBounds(290, 315, 211, 38);
        btnSaioaHasi.setForeground(new Color(0, 0, 0));
        btnSaioaHasi.setBackground(new Color(131, 183, 254));
        panela.add(btnSaioaHasi);

        btnErregistratu = new JButton("Erregistratu");
        btnErregistratu.setBounds(620, 10, 211, 38);
        btnErregistratu.setForeground(new Color(0, 0, 0));
        btnErregistratu.setBackground(new Color(131, 183, 254));
        panela.add(btnErregistratu);

        btnSaioaHasi.addActionListener(new SaioaHasiEkintza());
        btnErregistratu.addActionListener(new ErregistratuEkintza());

        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mekanizatua", "root", "1WMG2023");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class SaioaHasiEkintza implements ActionListener {
        
        public void actionPerformed(ActionEvent e) {
            String erabiltzailea = tfErabiltzailea.getText();
            String pasahitza = new String(pfPasahitza.getPassword());

            try {
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM erabiltzaileak WHERE usuario=? AND contraseña=?");
                ps.setString(1, erabiltzailea);
                ps.setString(2, pasahitza);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(hasiera.this, "Saioa hasi da!");
                    new Menu(connection).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(hasiera.this, "Erabiltzailea edo pasahitza okerra da.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class ErregistratuEkintza implements ActionListener {
        
        public void actionPerformed(ActionEvent e) {
            new erregistroa(connection).setVisible(true);
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new hasiera().setVisible(true);
        });
    }
}






        




