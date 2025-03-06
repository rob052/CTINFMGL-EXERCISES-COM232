import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private Label homeLabel;

    @FXML
    private MenuButton menuButton;

    @FXML
    private Button addmusicbutton;

    @FXML
    private Button updatemusicbutton;

    @FXML
    private Button deletemusicbutton;

    @FXML
    private TextField artisttextlabel1;

    @FXML
    private TextField titletextlabel1;

    @FXML
    private TextField titletextlabel2;

    @FXML
    private TextField artisttextlabel2;

    @FXML
    private TextField artisttextfield3;

    @FXML
    private TextField titletextlabel3;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize() {
        // Initialize the MenuButton with its items and their actions
        if (menuButton.getItems().isEmpty()) {
            MenuItem profileItem = new MenuItem("Profile");
            MenuItem settingsItem = new MenuItem("Settings");
            MenuItem transactionsItem = new MenuItem("Transactions");
            MenuItem musicItem = new MenuItem("Music");
            MenuItem logoutItem = new MenuItem("Logout");

            profileItem.setOnAction(this::handleProfileAction);
            settingsItem.setOnAction(this::handleSettingsAction);
            transactionsItem.setOnAction(this::handleTransactionsAction);
            musicItem.setOnAction(this::handleMusicAction);
            logoutItem.setOnAction(this::handleLogoutAction);

            menuButton.getItems().addAll(profileItem, settingsItem, transactionsItem, musicItem, logoutItem);
        }
    }

    @FXML
    private void handleProfileAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Profile.fxml"));
            Stage stage = (Stage) menuButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSettingsAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Accounts.fxml"));
            Stage stage = (Stage) menuButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTransactionsAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Transactions.fxml"));
            Stage stage = (Stage) menuButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMusicAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Music.fxml"));
            Stage stage = (Stage) menuButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogoutAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) menuButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
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

    @FXML
    private void handleAddMusicAction(ActionEvent event) {
        String title = titletextlabel1.getText();
        String artist = artisttextlabel1.getText();

        String url = "jdbc:mysql://127.0.0.1:3306/amusic?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String password = "robcastro123.";

        String insertQuery = "INSERT INTO music (title, artist) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, artist);

            preparedStatement.executeUpdate();

            System.out.println("Music added successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateMusicAction(ActionEvent event) {
        String title = titletextlabel2.getText();
        String artist = artisttextlabel2.getText();

        String url = "jdbc:mysql://127.0.0.1:3306/amusic?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String password = "robcastro123.";

        String updateQuery = "UPDATE music SET artist = ? WHERE title = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, artist);
            preparedStatement.setString(2, title);

            preparedStatement.executeUpdate();

            System.out.println("Music updated successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteMusicAction(ActionEvent event) {
        String title = titletextlabel3.getText();
        String artist = artisttextfield3.getText();

        String url = "jdbc:mysql://127.0.0.1:3306/amusic?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String password = "robcastro123.";

        String deleteQuery = "DELETE FROM music WHERE title = ? AND artist = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, artist);

            preparedStatement.executeUpdate();

            System.out.println("Music deleted successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to display the username
    public void displayName(String username) {
        homeLabel.setText("Welcome, " + username);
    }
}
