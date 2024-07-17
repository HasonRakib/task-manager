import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Optional;

public class MainLogin {
    private Stage primaryStage;
    private String role;
    private TaskManager taskManager = new TaskManager(); // Assume this is your existing TaskManager class

    public MainLogin(Stage primaryStage, String role, TaskManager taskManager) {
        this.primaryStage = primaryStage;
        this.role = role;
        this.taskManager = taskManager;
    }

    public void showLoginScene() {
        VBox loginLayout = new VBox(10);
        loginLayout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setStyle("-fx-padding: 5px;");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle("-fx-padding: 5px;");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px;");
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            Main main = new Main();
            main.start(primaryStage); // Go back to welcome screen
        });

        loginLayout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, backButton);

        Scene loginScene = new Scene(loginLayout, 400, 300);
        primaryStage.setScene(loginScene);
    }

    @SuppressWarnings("unlikely-arg-type")
    private void handleLogin(String username, String password) {
        Optional<User> user = taskManager.authenticateUser(username, password);
        if (user.isPresent() && user.get().getRole().equals(role)) {
            switch (role) {
                case "admin":
                    showAdminMenu();
                    break;
                // Add cases for other roles as needed
                default:
                    showErrorAlert("Role Error", "Invalid role specified.");
                    break;
            }
            // Add cases for PROJECT_MANAGER and EMPLOYEE as needed
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Invalid Credentials");
            alert.setContentText("The username or password you entered is incorrect.");
            alert.showAndWait();
            }
        }

        public void goBack() {
            Main mainApp = new Main();
            mainApp.start(primaryStage);
        
         }

         public void showAdminMenu() {
            // Create and show the AdminMenu
            Main mainApp = new Main();
            mainApp.showAdminMenu();
        }

    private void showErrorAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
