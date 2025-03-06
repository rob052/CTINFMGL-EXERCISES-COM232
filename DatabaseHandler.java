import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/aMusic?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER = "root"; // Update with your MySQL username
    private static final String PASS = "robcastro123."; // Update with your MySQL password

    public static Connection getDBConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public static boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM Users WHERE Username = ? AND PasswordHash = ?";
        Connection connection = getDBConnection();
        if (connection == null) {
            System.out.println("Failed to establish a database connection.");
            return false;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if a match is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}