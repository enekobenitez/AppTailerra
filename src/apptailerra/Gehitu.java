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

public class Gehitu extends JFrame {
    private JTextField[] textFields;
    private Connection connection;
    private String tableName;

    public Gehitu(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;

        setTitle("Gehitu - " + tableName);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panela = new JPanel();
        panela.setLayout(new GridLayout(0, 2));

        List<String> columnNames = getColumnNames(tableName);
        if (columnNames.size() == 0) {
            JOptionPane.showMessageDialog(Gehitu.this, "Errorea: Ez dago erregistrorik.");
            dispose();
            return;
        }

        textFields = new JTextField[columnNames.size()];

        for (int i = 0; i < columnNames.size(); i++) {
            panela.add(new JLabel(columnNames.get(i) + ":"));
            textFields[i] = new JTextField();
            panela.add(textFields[i]);
        }

        JButton btnGorde = new JButton("Gorde");
        btnGorde.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " (");
                    for (int i = 0; i < columnNames.size(); i++) {
                        query.append(columnNames.get(i));
                        if (i < columnNames.size() - 1) {
                            query.append(", ");
                        }
                    }
                    query.append(") VALUES (");
                    for (int i = 0; i < columnNames.size(); i++) {
                        query.append("?");
                        if (i < columnNames.size() - 1) {
                            query.append(", ");
                        }
                    }
                    query.append(")");

                    PreparedStatement ps = connection.prepareStatement(query.toString());
                    for (int i = 0; i < textFields.length; i++) {
                        ps.setString(i + 1, textFields[i].getText());
                    }

                    int rowsInserted = ps.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(Gehitu.this, "Datuak gehitu dira!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(Gehitu.this, "Errorea datuak gehitzean.");
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




