package apptailerra;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Menu extends JFrame {
    private JComboBox<String> cbTablak;
    private JButton btnGehitu;
    private JButton btnKendu;
    private JButton btnEditatu;
    private JTable table;
    private Connection connection;

    public Menu(Connection connection) {
        this.connection = connection;

        setTitle("Menua");
        setSize(768, 471);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panela = new JPanel();

        cbTablak = new JComboBox<>();
        cbTablak.addItem("Aukeratu taula:");
        cbTablak.addItem("arriskuak");
        cbTablak.addItem("makinak");
        cbTablak.addItem("babes_elementuak");
        cbTablak.addItem("hondakinak");
        cbTablak.addItem("makina_babes_elementuak");
        cbTablak.addItem("produktu_kimikoak");
        

        cbTablak.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                loadTableData((String) cbTablak.getSelectedItem());
            }
        });

        btnGehitu = new JButton("Gehitu");
        btnGehitu.setBackground(new Color(0, 128, 255));
        btnKendu = new JButton("Kendu");
        btnKendu.setBackground(new Color(0, 128, 255));

        btnGehitu.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                String tableName = (String) cbTablak.getSelectedItem();
                new Gehitu(connection, tableName).setVisible(true);
            }
        });

        btnKendu.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                String tableName = (String) cbTablak.getSelectedItem();
                new Kendu(connection, tableName).setVisible(true);
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.setBounds(0, 0, 586, 21);
        topPanel.setLayout(new GridLayout(1, 4));
        topPanel.add(cbTablak);
        topPanel.add(btnGehitu);
        topPanel.add(btnKendu);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 21, 758, 417);
        panela.setLayout(null);

        panela.add(topPanel);
        panela.add(scrollPane);

        getContentPane().add(panela);
        btnEditatu = new JButton("Editatu");
        btnEditatu.setBackground(new Color(0, 128, 255));
        btnEditatu.setBounds(584, 0, 174, 21);
        panela.add(btnEditatu);
        
                btnEditatu.addActionListener(new ActionListener() {
                  
                    public void actionPerformed(ActionEvent e) {
                        String tableName = (String) cbTablak.getSelectedItem();
                        new Editatu(connection, tableName).setVisible(true);
                    }
                });
    }

    private void loadTableData(String tableName) {
        try {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            
            DefaultTableModel tableModel = new DefaultTableModel();
            table.setModel(tableModel); 

            
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getObject(i);
                }
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}











