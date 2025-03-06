import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Profile {
    private String accountStatus;
    private String dateSubscribed;
    private String subscriptionStatus;
    private String subscriptionType;

    public Profile(String accountStatus, String dateSubscribed, String subscriptionStatus, String subscriptionType) {
        this.accountStatus = accountStatus;
        this.dateSubscribed = dateSubscribed;
        this.subscriptionStatus = subscriptionStatus;
        this.subscriptionType = subscriptionType;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getDateSubscribed() {
        return dateSubscribed;
    }

    public void setDateSubscribed(String dateSubscribed) {
        this.dateSubscribed = dateSubscribed;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
}

public class ProfileController {

    @FXML
    private TableView<Profile> tableView;

    @FXML
    private TableColumn<Profile, String> accstats;

    @FXML
    private TableColumn<Profile, String> datesub;

    @FXML
    private TableColumn<Profile, String> substats;

    @FXML
    private TableColumn<Profile, String> subtype;

    private ObservableList<Profile> accountList;

    @FXML
    public void initialize() {
        accountList = FXCollections.observableArrayList();

        // Set up the columns in the table
        accstats.setCellValueFactory(new PropertyValueFactory<>("accountStatus"));
        datesub.setCellValueFactory(new PropertyValueFactory<>("dateSubscribed"));
        substats.setCellValueFactory(new PropertyValueFactory<>("subscriptionStatus"));
        subtype.setCellValueFactory(new PropertyValueFactory<>("subscriptionType"));

        // Load data from the database
        loadDataFromDatabase();

        // Add data to the table
        tableView.setItems(accountList);
    }

    private void loadDataFromDatabase() {
        String url = "jdbc:mysql://127.0.0.1:3306/amusic?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String password = "robcastro123.";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT AccountStatus, DateSubscribed, SubscriptionStatus, SubscriptionType FROM users")) {

            accountList.clear(); // Clear the existing data

            while (resultSet.next()) {
                String accountStatus = resultSet.getString("AccountStatus");
                String dateSubscribed = resultSet.getString("DateSubscribed");
                String subscriptionStatus = resultSet.getString("SubscriptionStatus");
                String subscriptionType = resultSet.getString("SubscriptionType");

                Profile account = new Profile(accountStatus, dateSubscribed, subscriptionStatus, subscriptionType);
                accountList.add(account);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProceedButtonAction(ActionEvent event) {
        loadDataFromDatabase();
        tableView.setItems(accountList); // Refresh the table view
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
    private void handleUpdateProfileAction(ActionEvent event) {
        // Implement the logic to update the profile
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Profile");
        alert.setContentText("Profile updated successfully!");
        alert.showAndWait();
    }

    @FXML
    private void handleDeleteProfileAction(ActionEvent event) {
        // Implement the logic to delete the profile
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete Profile");
        alert.setContentText("Profile deleted successfully!");
        alert.showAndWait();
    }
}
