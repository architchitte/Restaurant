import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RestaurantDatabaseGUI extends JFrame {

    private Connection conn;

    // Constructor to set up the GUI and establish database connection
    public RestaurantDatabaseGUI() {
        // Set up main JFrame
        setTitle("Restaurant Database Management");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize database connection
        connectToDatabase();

        // Create a tabbed pane to hold different panels for different tables
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add panels for each table (Customers, Bills, Orders, etc.)
        tabbedPane.addTab("Customers", createCustomerPanel());
        tabbedPane.addTab("Orders", createOrderPanel());
        tabbedPane.addTab("Bills", createBillPanel());
        tabbedPane.addTab("Order Items", createOrderItemsPanel());
        tabbedPane.addTab("Waiters", createWaiterPanel());
        tabbedPane.addTab("Managers", createManagerPanel());
        tabbedPane.addTab("Cashiers", createCashierPanel());
        tabbedPane.addTab("Chefs", createChefPanel());

        // Add the tabbed pane to the frame
        add(tabbedPane, BorderLayout.CENTER);
    }

    // Establish connection to the database
    private void connectToDatabase() {
        try {
            String dbURL = "jdbc:mysql://LAPTOP-43OJD8AT\\SQLEXPRESS:1433;databaseName=RESTAURANT;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
            conn = DriverManager.getConnection(dbURL);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error connecting to database: " + ex.getMessage());
        }
    }

    // Panel for managing Customer table
    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(5, 2));

        // Input fields for customer
        JTextField customerIdField = new JTextField();
        JTextField customerNameField = new JTextField();
        JTextField contactNoField = new JTextField();
        JTextField addressField = new JTextField();

        // Add form fields to panel
        formPanel.add(new JLabel("Customer ID:"));
        formPanel.add(customerIdField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(customerNameField);
        formPanel.add(new JLabel("Contact No:"));
        formPanel.add(contactNoField);
        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressField);

        panel.add(formPanel, BorderLayout.CENTER);

        // Buttons for inserting and updating customers
        JPanel buttonPanel = new JPanel();
        JButton insertButton = new JButton("Insert Customer");
        JButton updateButton = new JButton("Update Customer");

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertCustomer(Integer.parseInt(customerIdField.getText()), customerNameField.getText(),
                        contactNoField.getText(), addressField.getText());
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCustomer(Integer.parseInt(customerIdField.getText()), customerNameField.getText(),
                        contactNoField.getText(), addressField.getText());
            }
        });

        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // Method to insert a new customer into the Customer table
    private void insertCustomer(int id, String name, String contact, String address) {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO Customer (Customer_ID, Name, Contact_No, Address) VALUES (?, ?, ?, ?)")) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, contact);
            pstmt.setString(4, address);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Customer inserted successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error inserting customer: " + ex.getMessage());
        }
    }

    // Method to update an existing customer in the Customer table
    private void updateCustomer(int id, String name, String contact, String address) {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE Customer SET Name = ?, Contact_No = ?, Address = ? WHERE Customer_ID = ?")) {
            pstmt.setString(1, name);
            pstmt.setString(2, contact);
            pstmt.setString(3, address);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Customer updated successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating customer: " + ex.getMessage());
        }
    }

    // Panel for managing Orders table
    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(4, 2));

        JTextField orderNoField = new JTextField();
        JTextField customerIdField = new JTextField();
        JTextField orderDateField = new JTextField();

        formPanel.add(new JLabel("Order No:"));
        formPanel.add(orderNoField);
        formPanel.add(new JLabel("Customer ID:"));
        formPanel.add(customerIdField);
        formPanel.add(new JLabel("Order Date (YYYY-MM-DD):"));
        formPanel.add(orderDateField);

        panel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton insertButton = new JButton("Insert Order");
        JButton updateButton = new JButton("Update Order");

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertOrder(Integer.parseInt(orderNoField.getText()), Integer.parseInt(customerIdField.getText()),
                        orderDateField.getText());
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOrder(Integer.parseInt(orderNoField.getText()), Integer.parseInt(customerIdField.getText()),
                        orderDateField.getText());
            }
        });

        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // Method to insert a new order into the Orders table
    private void insertOrder(int orderNo, int customerId, String orderDate) {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO Orders (Order_No, Customer_ID, Order_Date) VALUES (?, ?, ?)")) {
            pstmt.setInt(1, orderNo);
            pstmt.setInt(2, customerId);
            pstmt.setDate(3, Date.valueOf(orderDate));
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Order inserted successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error inserting order: " + ex.getMessage());
        }
    }

    // Method to update an existing order in the Orders table
    private void updateOrder(int orderNo, int customerId, String orderDate) {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE Orders SET Customer_ID = ?, Order_Date = ? WHERE Order_No = ?")) {
            pstmt.setInt(1, customerId);
            pstmt.setDate(2, Date.valueOf(orderDate));
            pstmt.setInt(3, orderNo);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Order updated successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating order: " + ex.getMessage());
        }
    }

    // Method to manage other tables
    private JPanel createBillPanel() { /* Similar to createOrderPanel */ return new JPanel(); }
    private JPanel createOrderItemsPanel() { /* Similar to createOrderPanel */ return new JPanel(); }
    private JPanel createWaiterPanel() { /* Similar to createCustomerPanel */ return new JPanel(); }
    private JPanel createManagerPanel() { /* Similar to createCustomerPanel */ return new JPanel(); }
    private JPanel createCashierPanel() { /* Similar to createCustomerPanel */ return new JPanel(); }
    private JPanel createChefPanel() { /* Similar to createCustomerPanel */ return new JPanel(); }

    // Main method to launch the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RestaurantDatabaseGUI().setVisible(true);
        });
    }
}
