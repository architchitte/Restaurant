package Restaurant;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerPanel extends JPanel {

    private JTextField txtCustomerID, txtName, txtContactNo, txtAddress;
    private JTextArea txtOutput;

    public CustomerPanel() {
        setLayout(new GridLayout(6, 2));

        JLabel lblCustomerID = new JLabel("Customer ID:");
        JLabel lblName = new JLabel("Name:");
        JLabel lblContactNo = new JLabel("Contact No:");
        JLabel lblAddress = new JLabel("Address:");

        txtCustomerID = new JTextField();
        txtName = new JTextField();
        txtContactNo = new JTextField();
        txtAddress = new JTextField();
        txtOutput = new JTextArea();

        JButton btnView = new JButton("View Customers");
        JButton btnAdd = new JButton("Add Customer");
        JButton btnUpdate = new JButton("Update Customer");

        // Add components to panel
        add(lblCustomerID);
        add(txtCustomerID);
        add(lblName);
        add(txtName);
        add(lblContactNo);
        add(txtContactNo);
        add(lblAddress);
        add(txtAddress);
        add(btnView);
        add(btnAdd);
        add(btnUpdate);
        add(txtOutput);

        // View customers
        btnView.addActionListener(e -> viewCustomers());
        // Add customer
        btnAdd.addActionListener(e -> addCustomer());
        // Update customer
        btnUpdate.addActionListener(e -> updateCustomer());
    }

    private void viewCustomers() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM Customer";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            txtOutput.setText("");
            while (rs.next()) {
                txtOutput.append("ID: " + rs.getInt("Customer_ID") +
                        ", Name: " + rs.getString("Name") +
                        ", Contact: " + rs.getString("Contact_No") +
                        ", Address: " + rs.getString("Address") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addCustomer() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "INSERT INTO Customer (Customer_ID, Name, Contact_No, Address) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(txtCustomerID.getText()));
            stmt.setString(2, txtName.getText());
            stmt.setString(3, txtContactNo.getText());
            stmt.setString(4, txtAddress.getText());
            stmt.executeUpdate();
            txtOutput.setText("Customer added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCustomer() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "UPDATE Customer SET Name=?, Contact_No=?, Address=? WHERE Customer_ID=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, txtName.getText());
            stmt.setString(2, txtContactNo.getText());
            stmt.setString(3, txtAddress.getText());
            stmt.setInt(4, Integer.parseInt(txtCustomerID.getText()));
            stmt.executeUpdate();
            txtOutput.setText("Customer updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
