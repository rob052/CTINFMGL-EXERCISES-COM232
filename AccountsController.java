import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {

    @FXML
    private TableView<User> mytable;

    @FXML
    private TableColumn<User, String> usernamepoll;

    @FXML
    private TableColumn<User, String> passwordpoll;

    @FXML
    private TableColumn<User, String> accountcreatedpoll;

    @FXML
    private TableColumn<User, String> statuspoll;

    @FXML
    private Label homeLabel;

    @FXML
    private Button btnlogout;

    @FXML
    private Button btncreate;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnupdate;

    @FXML
    private TextField nametextfield;

    @FXML
    private TextField passwordtextfield;

    @FXML
    private TextField accountcreatedtextfield;

    @FXML
    private TextField accountstatustextfield;

    @FXML
    private Button returnbutton;

    private ObservableList<User> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        homeLabel.setText("User");
        initializeCol();
        loadData();
    }

    private void initializeCol() {
        usernamepoll.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordpoll.setCellValueFactory(new PropertyValueFactory<>("password"));
        accountcreatedpoll.setCellValueFactory(new PropertyValueFactory<>("accountCreated"));
        statuspoll.setCellValueFactory(new PropertyValueFactory<>("accountStatus"));
    }

    private void loadData() {
        data = FXCollections.observableArrayList();
        String url = "jdbc:mysql://127.0.0.1:3306/amusic?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
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

    public void displayName(String username) {
        homeLabel.setText("Welcome, " + username);
    }

    @FXML
    private boolean createUser(ActionEvent event) {
        String username = nametextfield.getText();
        String password = passwordtextfield.getText();
        String accountcreated = accountcreatedtextfield.getText();
        String accountstatus = accountstatustextfield.getText();

        if (username.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("empty username");
            alert.showAndWait();
            return false;
        }
        if (password.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("empty password");
            alert.showAndWait();
            return false;
        }
        if (accountcreated.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("empty account created");
            alert.showAndWait();
            return false;
        }
        if (accountstatus.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("empty account status");
            alert.showAndWait();
            return false;
        }

        // Convert date to MySQL format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(new Date());

        String url = "jdbc:mysql://127.0.0.1:3306/amusic?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String passwordDB = "robcastro123.";

        String insertQuery = "INSERT INTO users (Username, PasswordHash, DateSubscribed, AccountStatus) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, passwordDB);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, formattedDate);
            preparedStatement.setString(4, accountstatus);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        loadData(); // Refresh the table view
        return true;
    }

    @FXML
    private void updateUser(ActionEvent event) {
        String username = nametextfield.getText();
        String password = passwordtextfield.getText();
        String accountcreated = accountcreatedtextfield.getText();
        String accountstatus = accountstatustextfield.getText();

        if (username.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("empty username");
            alert.showAndWait();
            return;
        }

        // Convert date to MySQL format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(new Date());

        String url = "jdbc:mysql://127.0.0.1:3306/amusic?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String passwordDB = "robcastro123.";

        String updateQuery = "UPDATE users SET PasswordHash = ?, DateSubscribed = ?, AccountStatus = ? WHERE Username = ?";

        try (Connection connection = DriverManager.getConnection(url, user, passwordDB);
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, password);
            preparedStatement.setString(2, formattedDate);
            preparedStatement.setString(3, accountstatus);
            preparedStatement.setString(4, username);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadData(); // Refresh the table view
    }

    @FXML
    private void deleteUser(ActionEvent event) {
        String username = nametextfield.getText();

        if (username.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("empty username");
            alert.showAndWait();
            return;
        }

        String url = "jdbc:mysql://127.0.0.1:3306/amusic?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String passwordDB = "robcastro123.";

        String deleteQuery = "DELETE FROM users WHERE Username = ?";

        try (Connection connection = DriverManager.getConnection(url, user, passwordDB);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setString(1, username);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadData(); // Refresh the table view
    }

    @FXML
    private void handleLogoutButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReturnButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Other methods and logic for AccountsController
}
