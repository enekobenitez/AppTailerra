package apptailerra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Editatu extends JFrame {
    private JTextField[] textFields;
    private Connection connection;
    private String tableName;
    private String columnIdentifier;

    public Editatu(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;

        setTitle("Editatu - " + tableName);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panela = new JPanel();
        panela.setLayout(new GridLayout(0, 2));

        List<String> columnNames = getColumnNames(tableName);
        if (columnNames.size() == 0) {
            JOptionPane.showMessageDialog(Editatu.this, "Errorea: Ez dago erregistrorik.");
            dispose();
            return; 
        }

        textFields = new JTextField[columnNames.size()];

        columnIdentifier = columnNames.get(0);

        for (int i = 0; i < columnNames.size(); i++) {
            panela.add(new JLabel(columnNames.get(i) + ":"));
            textFields[i] = new JTextField();
            panela.add(textFields[i]);
        }

        JButton btnGorde = new JButton("Gorde");
        btnGorde.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                try {
                    StringBuilder query = new StringBuilder("UPDATE " + tableName + " SET ");
                    for (int i = 1; i < columnNames.size(); i++) { 
                        query.append(columnNames.get(i)).append(" = ?");
                        if (i < columnNames.size() - 1) {
                            query.append(", ");
                        }
                    }
                    query.append(" WHERE ").append(columnIdentifier).append(" = ?");

                    PreparedStatement ps = connection.prepareStatement(query.toString());
                    for (int i = 1; i < textFields.length; i++) {
                        ps.setString(i, textFields[i].getText());
                    }
                    ps.setString(textFields.length, textFields[0].getText()); 

                    int rowsUpdated = ps.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(Editatu.this, "Datuak eguneratu dira!");
                        dispose(); 
                    } else {
                        JOptionPane.showMessageDialog(Editatu.this, "Errorea datuak eguneratzean.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(panela, BorderLayout.CENTER);
        add(btnGorde, BorderLayout.SOUTH);
    }

    private List<String> getColumnNames(String tableName) {
        List<String> columnNames = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + tableName + " LIMIT 1");
            ResultSet rs = ps.executeQuery();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                columnNames.add(rs.getMetaData().getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnNames;
    }
}


