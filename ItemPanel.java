package Restaurant;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemPanel extends JPanel {

    private JTextField txtItemNo, txtDescription, txtPrice;
    private JTextArea txtOutput;

    public ItemPanel() {
        setLayout(new GridLayout(4, 2));

        JLabel lblItemNo = new JLabel("Item No:");
        JLabel lblDescription = new JLabel("Description:");
        JLabel lblPrice = new JLabel("Price:");

        txtItemNo = new JTextField();
        txtDescription = new JTextField();
        txtPrice = new JTextField();
        txtOutput = new JTextArea();

        JButton btnView = new JButton("View Items");
        JButton btnAdd = new JButton("Add Item");

        // Add components to panel
        add(lblItemNo);
        add(txtItemNo);
        add(lblDescription);
        add(txtDescription);
        add(lblPrice);
        add(txtPrice);
        add(btnView);
        add(btnAdd);
        add(txtOutput);

        // View items
        btnView.addActionListener(e -> viewItems());
        // Add new item
        btnAdd.addActionListener(e -> addItem());
    }

    private void viewItems() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM Item";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            txtOutput.setText("");
            while (rs.next()) {
                txtOutput.append("Item No: " + rs.getInt("Item_No") +
                        ", Description: " + rs.getString("Description") +
                        ", Price: " + rs.getBigDecimal("Price") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addItem() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "INSERT INTO Item (Item_No, Description, Price) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(txtItemNo.getText()));
            stmt.setString(2, txtDescription.getText());
            stmt.setBigDecimal(3, new java.math.BigDecimal(txtPrice.getText()));
            stmt.executeUpdate();
            txtOutput.setText("Item added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
