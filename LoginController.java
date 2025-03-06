import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.io.IOException;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordtextField;

    @FXML
    private Label passwordlabel;

    @FXML
    private TextField usernametextField;

    @FXML
    private Label usernamelabel;

    @FXML
    public void loginButtonHandler(ActionEvent event) throws IOException {

        // Get text from text field
        String username = usernametextField.getText();
        String password = passwordtextField.getText();  

        System.out.println(username + " " + password);

        if(DatabaseHandler.validateLogin(username, password))
        {
            System.out.println("Successful");

            // Load Home.fxml when login button is clicked
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            // Load HomeController
            Parent root = loader.load();
            HomeController homecontroller = loader.getController();
            // Pass username from textfield to displayName() method
            homecontroller.displayName(username);

            // Load stage and scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else{
            System.out.println("Unsuccessful");
        }
    }
}