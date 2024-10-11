package Restaurant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://LAPTOP-430JD8AT\\SQLEXPRESS:1433;databaseName=RESTAURANT;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";  // Replace with your DB URL

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

