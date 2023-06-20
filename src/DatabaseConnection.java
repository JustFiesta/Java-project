import java.sql.*;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/java_projekt";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private Connection connection = null;

    public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//            System.out.println("Połączenie z bazą danych zostało ustanowione.");
        } catch (SQLException e) {
            System.out.println("Błąd podczas nawiązywania połączenia z bazą danych: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
//                System.out.println("Połączenie z bazą danych zostało zamknięte.");
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas zamykania połączenia z bazą danych: " + e.getMessage());
        }
    }
}
