package Restaurant;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderPanel extends JPanel {

    private JTextField txtOrderNo, txtCustomerID, txtOrderDate;
    private JTextArea txtOutput;

    public OrderPanel() {
        setLayout(new GridLayout(4, 2));

        JLabel lblOrderNo = new JLabel("Order No:");
        JLabel lblCustomerID = new JLabel("Customer ID:");
        JLabel lblOrderDate = new JLabel("Order Date (YYYY-MM-DD):");

        txtOrderNo = new JTextField();
        txtCustomerID = new JTextField();
        txtOrderDate = new JTextField();
        txtOutput = new JTextArea();

        JButton btnView = new JButton("View Orders");
        JButton btnUpdate = new JButton("Update Order");

        // Add components to panel
        add(lblOrderNo);
        add(txtOrderNo);
        add(lblCustomerID);
        add(txtCustomerID);
        add(lblOrderDate);
        add(txtOrderDate);
        add(btnView);
        add(btnUpdate);
        add(txtOutput);

        // View orders
        btnView.addActionListener(e -> viewOrders());
        // Update order
        btnUpdate.addActionListener(e -> updateOrder());
    }

    private void viewOrders() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM Orders";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            txtOutput.setText("");
            while (rs.next()) {
                txtOutput.append("Order No: " + rs.getInt("Order_No") +
                        ", Customer ID: " + rs.getInt("Customer_ID") +
                        ", Order Date: " + rs.getDate("Order_Date") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateOrder() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "UPDATE Orders SET Order_Date=? WHERE Order_No=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1, java.sql.Date.valueOf(txtOrderDate.getText()));
            stmt.setInt(2, Integer.parseInt(txtOrderNo.getText()));
            stmt.executeUpdate();
            txtOutput.setText("Order updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

