package Restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApp extends JFrame {

    public MainApp() {
        setTitle("Restaurant Management System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Card Layout for different pages
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Add different pages
        mainPanel.add(new CustomerPanel(), "Customer");
        mainPanel.add(new BillPanel(), "Bill");
        mainPanel.add(new OrderPanel(), "Order");
        mainPanel.add(new ItemPanel(), "Item");
        mainPanel.add(new ManagerPanel(), "Manager");
        mainPanel.add(new OrderItemsPanel(), "OrderItems");

        // Create navigation buttons
        JPanel navPanel = new JPanel();
        JButton btnCustomer = new JButton("Customer");
        JButton btnBill = new JButton("Bill");
        JButton btnOrder = new JButton("Order");
        JButton btnItem = new JButton("Item");
        JButton btnManager = new JButton("Manager");
        JButton btnOrderItems = new JButton("Order Items");

        // Add action listeners to buttons to switch between cards
        btnCustomer.addActionListener(e -> cardLayout.show(mainPanel, "Customer"));
        btnBill.addActionListener(e -> cardLayout.show(mainPanel, "Bill"));
        btnOrder.addActionListener(e -> cardLayout.show(mainPanel, "Order"));
        btnItem.addActionListener(e -> cardLayout.show(mainPanel, "Item"));
        btnManager.addActionListener(e -> cardLayout.show(mainPanel, "Manager"));
        btnOrderItems.addActionListener(e -> cardLayout.show(mainPanel, "OrderItems"));

        navPanel.add(btnCustomer);
        navPanel.add(btnBill);
        navPanel.add(btnOrder);
        navPanel.add(btnItem);
        navPanel.add(btnManager);
        navPanel.add(btnOrderItems);

        // Set layout and add components
        setLayout(new BorderLayout());
        add(navPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApp app = new MainApp();
            app.setVisible(true);
        });
    }
}

