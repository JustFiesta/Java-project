import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConneciton {
        private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase";
        private static final String DB_USERNAME = "root";
        private static final String DB_PASSWORD = "";

        private static Connection connection = null;

        public static Connection getConnection() {
            if (connection == null) {
                try {
                    connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                    System.out.println("Connected to the database");
                } catch (SQLException e) {
                    System.out.println("Failed to connect to the database");
                    e.printStackTrace();
                }
            }
            return connection;
        }
    }