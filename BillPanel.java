package Restaurant;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillPanel extends JPanel {

    private JTextField txtBillNo, txtCustomerID, txtAmount, txtBillDate;
    private JTextArea txtOutput;

    public BillPanel() {
        setLayout(new GridLayout(5, 2));

        JLabel lblBillNo = new JLabel("Bill No:");
        JLabel lblCustomerID = new JLabel("Customer ID:");
        JLabel lblAmount = new JLabel("Amount:");
        JLabel lblBillDate = new JLabel("Bill Date (YYYY-MM-DD):");

        txtBillNo = new JTextField();
        txtCustomerID = new JTextField();
        txtAmount = new JTextField();
        txtBillDate = new JTextField();
        txtOutput = new JTextArea();

        JButton btnView = new JButton("View Bills");
        JButton btnUpdate = new JButton("Update Bill");

        // Add components to panel
        add(lblBillNo);
        add(txtBillNo);
        add(lblCustomerID);
        add(txtCustomerID);
        add(lblAmount);
        add(txtAmount);
        add(lblBillDate);
        add(txtBillDate);
        add(btnView);
        add(btnUpdate);
        add(txtOutput);

        // View bills
        btnView.addActionListener(e -> viewBills());
        // Update bill
        btnUpdate.addActionListener(e -> updateBill());
    }

    private void viewBills() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM Bill";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            txtOutput.setText("");
            while (rs.next()) {
                txtOutput.append("Bill No: " + rs.getInt("Bill_No") +
                        ", Customer ID: " + rs.getInt("Customer_ID") +
                        ", Amount: " + rs.getBigDecimal("Amount") +
                        ", Bill Date: " + rs.getDate("Bill_Date") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateBill() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "UPDATE Bill SET Amount=?, Bill_Date=? WHERE Bill_No=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setBigDecimal(1, new java.math.BigDecimal(txtAmount.getText()));
            stmt.setDate(2, java.sql.Date.valueOf(txtBillDate.getText()));
            stmt.setInt(3, Integer.parseInt(txtBillNo.getText()));
            stmt.executeUpdate();
            txtOutput.setText("Bill updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
