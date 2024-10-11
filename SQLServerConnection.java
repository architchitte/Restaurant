package Restaurant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerConnection {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://LAPTOP-43OJD8AT\\SQLEXPRESS:3036;databaseName=RESTAURANT;integratedSecurity=true;";

        try {
            // Establish connection
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

