import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {
    private String username;
    private String password;
    private String accountCreated;
    private String accountStatus;
    private ObservableList<User> data;

    public User(String username, String password, String accountCreated, String accountStatus) {
        this.username = username;
        this.password = password;
        this.accountCreated = accountCreated;
        this.accountStatus = accountStatus;
    }

    // Getters and setters for the fields
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(String accountCreated) {
        this.accountCreated = accountCreated;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    private void loadData() {
        data = FXCollections.observableArrayList();
        String url = "jdbc:mysql://127.0.0.1:3306/aMusic?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String password = "robcastro123.";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT Username, PasswordHash, DateSubscribed, AccountStatus FROM users")) {

            while (rs.next()) {
                String username = rs.getString("Username");
                String passwordHash = rs.getString("PasswordHash");
                String accountcreated = rs.getString("DateSubscribed");
                String accountstatus = rs.getString("AccountStatus");

                data.add(new User(username, passwordHash, accountcreated, accountstatus));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        mytable.setItems(data);
    }
}
