package apptailerra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Kendu extends JFrame {
    private JTextField tfIdentifikadorea;
    private Connection connection;
    private String tableName;

    public Kendu(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;

        setTitle("Kendu - " + tableName);
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panela = new JPanel();

        String columnIdentifier = "id";

        JLabel lblIdentifikadorea = new JLabel(columnIdentifier + ":");
        lblIdentifikadorea.setBounds(0, 0, 193, 56);
        tfIdentifikadorea = new JTextField();
        tfIdentifikadorea.setBounds(193, 0, 193, 56);
        JButton btnKendu = new JButton("Kendu");
        btnKendu.setBounds(0, 57, 193, 56);

        btnKendu.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                try {
                    String query = "DELETE FROM " + tableName + " WHERE " + columnIdentifier + " = ?";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setString(1, tfIdentifikadorea.getText());

                    int rowsDeleted = ps.executeUpdate();
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(Kendu.this, "Datuak kendu dira!");
                        
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(Kendu.this, "Errorea datuak kentzean.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        panela.setLayout(null);

        panela.add(lblIdentifikadorea);
        panela.add(tfIdentifikadorea);
        panela.add(btnKendu);

        getContentPane().add(panela);
    }
}




