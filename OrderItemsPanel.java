package Restaurant;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemsPanel extends JPanel {

    private JTextField txtOrderNo, txtItemNo, txtQuantity;
    private JTextArea txtOutput;

    public OrderItemsPanel() {
        setLayout(new GridLayout(4, 2));

        JLabel lblOrderNo = new JLabel("Order No:");
        JLabel lblItemNo = new JLabel("Item No:");
        JLabel lblQuantity = new JLabel("Quantity:");

        txtOrderNo = new JTextField();
        txtItemNo = new JTextField();
        txtQuantity = new JTextField();
        txtOutput = new JTextArea();

        JButton btnView = new JButton("View Order Items");
        JButton btnAdd = new JButton("Add Order Item");
        JButton btnUpdate = new JButton("Update Order Item");

        add(lblOrderNo);
        add(txtOrderNo);
        add(lblItemNo);
        add(txtItemNo);
        add(lblQuantity);
        add(txtQuantity);
        add(btnView);
        add(btnAdd);
        add(btnUpdate);
        add(txtOutput);

        btnView.addActionListener(e -> viewOrderItems());
        btnAdd.addActionListener(e -> addOrderItem());
        btnUpdate.addActionListener(e -> updateOrderItem());
    }

    private void viewOrderItems() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM OrderItems";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            txtOutput.setText("");
            while (rs.next()) {
                txtOutput.append("Order No: " + rs.getInt("Order_No") +
                        ", Item No: " + rs.getInt("Item_No") +
                        ", Quantity: " + rs.getInt("Quantity") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addOrderItem() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "INSERT INTO OrderItems (Order_No, Item_No, Quantity) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(txtOrderNo.getText()));
            stmt.setInt(2, Integer.parseInt(txtItemNo.getText()));
            stmt.setInt(3, Integer.parseInt(txtQuantity.getText()));
            stmt.executeUpdate();
            txtOutput.setText("Order item added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateOrderItem() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "UPDATE OrderItems SET Quantity=? WHERE Order_No=? AND Item_No=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(txtQuantity.getText()));
            stmt.setInt(2, Integer.parseInt(txtOrderNo.getText()));
            stmt.setInt(3, Integer.parseInt(txtItemNo.getText()));
            stmt.executeUpdate();
            txtOutput.setText("Order item updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
