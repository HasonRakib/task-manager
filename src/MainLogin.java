import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainLogin {

    private Stage primaryStage;
    private Role role;
    private TaskManager taskManager;

    public MainLogin(Stage primaryStage,String roleString, TaskManager taskManager) {
        this.primaryStage = primaryStage;
        this.role = Role.valueOf(roleString);
        this.taskManager = taskManager;
    }

    public void showLoginScene() {
        primaryStage.setTitle("Login");

        VBox loginLayout = new VBox(10);
        loginLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #34495e;");

        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px; -fx-background-color: #1abc9c; -fx-text-fill: white; -fx-background-radius: 5;");
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px; -fx-background-color: #16a085; -fx-text-fill: white; -fx-background-radius: 5;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px; -fx-background-color: #1abc9c; -fx-text-fill: white; -fx-background-radius: 5;"));

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            taskManager.authenticateUser(username, password).ifPresentOrElse(user -> {
                System.out.println("Expected role: " + role); // Debug statement
                System.out.println("Actual role: " + user.getRole()); // Debug statement
                if (user.getRole() == role) {
                    Platform.runLater(() -> {
                        if (role == Role.ADMIN) {
                            Main mainApp = new Main();
                            mainApp.showAdminMenu();
                        } else if (role == Role.PROJECT_MANAGER) {
                            Main mainApp = new Main();
                            mainApp.showProjectManagerMenu();
                        } else if (role == Role.EMPLOYEE) {
                            // Navigate to Employee menu
                        }
                    });
                } else {
                    // Show error message for incorrect role
                    System.out.println("Incorrect role");
                }
            }, () -> {
                // Show error message for incorrect credentials
                System.out.println("Incorrect credentials");
            });
        });

        loginLayout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton);

        Scene loginScene = new Scene(loginLayout, 400, 300);
        primaryStage.setScene(loginScene);
    }
}
