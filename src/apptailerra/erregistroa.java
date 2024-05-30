package apptailerra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class erregistroa extends JFrame {
    private JTextField tfErabiltzailea;
    private JPasswordField pfPasahitza;
    private JButton btnErregistratu;
    private Connection konexioa;

    public erregistroa(Connection konexioa) {
        this.konexioa = konexioa;
        setTitle("Erregistratu");
        setSize(684, 336);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panela = new JPanel();
        panela.setBackground(new Color(0, 128, 255));

        JLabel lblErabiltzailea = new JLabel("Erabiltzailea:");
        lblErabiltzailea.setBounds(166, 77, 87, 33);
        tfErabiltzailea = new JTextField();
        tfErabiltzailea.setBounds(221, 110, 162, 33);
        tfErabiltzailea.setBackground(new Color(192, 192, 192));
        JLabel lblPasahitza = new JLabel("Pasahitza:");
        lblPasahitza.setBounds(166, 133, 64, 33);
        pfPasahitza = new JPasswordField();
        pfPasahitza.setBounds(221, 170, 162, 33);
        pfPasahitza.setBackground(new Color(192, 192, 192));
        btnErregistratu = new JButton("Erregistratu");
        btnErregistratu.setBounds(207, 228, 209, 42);
        btnErregistratu.setBackground(new Color(1, 179, 245));

        btnErregistratu.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                String erabiltzailea = tfErabiltzailea.getText();
                String pasahitza = new String(pfPasahitza.getPassword());

                try {
                    PreparedStatement ps = konexioa.prepareStatement("INSERT INTO erabiltzaileak (usuario, contraseña) VALUES (?, ?)");
                    ps.setString(1, erabiltzailea);
                    ps.setString(2, pasahitza);
                    int rowsInserted = ps.executeUpdate();

                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(erregistroa.this, "Erabiltzailea erregistratu da!");
                        new hasiera().setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(erregistroa.this, "Erregistroak huts egin du.");
                    }
                } catch (SQLException ex) {
                    if (ex.getErrorCode() == 1062) {
                        JOptionPane.showMessageDialog(erregistroa.this, "Erabiltzaile izena jadanik existitzen da.");
                    } else {
                        ex.printStackTrace();
                    }
                }
            }
        });
        panela.setLayout(null);

        panela.add(lblErabiltzailea);
        panela.add(tfErabiltzailea);
        panela.add(lblPasahitza);
        panela.add(pfPasahitza);
        panela.add(btnErregistratu);

        getContentPane().add(panela);
    }
}


