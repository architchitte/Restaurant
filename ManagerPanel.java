package Restaurant;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerPanel extends JPanel {

    private JTextField txtManagerID, txtName, txtContactNo;
    private JTextArea txtOutput;

    public ManagerPanel() {
        setLayout(new GridLayout(4, 2));

        JLabel lblManagerID = new JLabel("Manager ID:");
        JLabel lblName = new JLabel("Name:");
        JLabel lblContactNo = new JLabel("Contact No:");

        txtManagerID = new JTextField();
        txtName = new JTextField();
        txtContactNo = new JTextField();
        txtOutput = new JTextArea();

        JButton btnView = new JButton("View Managers");
        JButton btnAdd = new JButton("Add Manager");
        JButton btnUpdate = new JButton("Update Manager");

        add(lblManagerID);
        add(txtManagerID);
        add(lblName);
        add(txtName);
        add(lblContactNo);
        add(txtContactNo);
        add(btnView);
        add(btnAdd);
        add(btnUpdate);
        add(txtOutput);

        btnView.addActionListener(e -> viewManagers());
        btnAdd.addActionListener(e -> addManager());
        btnUpdate.addActionListener(e -> updateManager());
    }

    private void viewManagers() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM Manager";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            txtOutput.setText("");
            while (rs.next()) {
                txtOutput.append("Manager ID: " + rs.getInt("Manager_ID") +
                        ", Name: " + rs.getString("Name") +
                        ", Contact No: " + rs.getString("Contact_No") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addManager() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "INSERT INTO Manager (Manager_ID, Name, Contact_No) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(txtManagerID.getText()));
            stmt.setString(2, txtName.getText());
            stmt.setString(3, txtContactNo.getText());
            stmt.executeUpdate();
            txtOutput.setText("Manager added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateManager() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "UPDATE Manager SET Name=?, Contact_No=? WHERE Manager_ID=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, txtName.getText());
            stmt.setString(2, txtContactNo.getText());
            stmt.setInt(3, Integer.parseInt(txtManagerID.getText()));
            stmt.executeUpdate();
            txtOutput.setText("Manager updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
