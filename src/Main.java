import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private TaskManager taskManager = new TaskManager(); 
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Show splash screen
        showSplashScreen();
    }

    private void showSplashScreen() {
        Stage splashStage = new Stage();

        Label splashLabel = new Label("Loading...");
        splashLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        StackPane splashLayout = new StackPane(splashLabel);
        splashLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #2c3e50;");
        Scene splashScene = new Scene(splashLayout, 400, 300);

        splashStage.setScene(splashScene);
        splashStage.setTitle("Prodigy Task Manager - Splash Screen");
        splashStage.show();

        // Simulate initialization process in a separate thread
        new Thread(() -> {
            try {
                Thread.sleep(2000); // Simulate a delay for initialization
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                splashStage.close();
                primaryStage.setScene(initWelcomeScene()); // Use the initWelcomeScene method to get the scene
                primaryStage.show();
            });
        }).start();
    }

    public Scene initWelcomeScene() { // Change the return type to Scene
        primaryStage.setTitle("Prodigy Task Manager");

        VBox welcomeLayout = new VBox(10);
        welcomeLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #34495e;");

        Label welcomeLabel = new Label("Welcome to Prodigy");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button loginButton = new Button("Login");
        styleButton(loginButton);
        loginButton.setOnAction(e -> showLoginOptions());

        Button exitButton = new Button("Exit");
        styleButton(exitButton);
        exitButton.setOnAction(e -> primaryStage.close());

        welcomeLayout.getChildren().addAll(welcomeLabel, loginButton, exitButton);

        Scene welcomeScene = new Scene(welcomeLayout, 400, 300);
        // Remove this line: primaryStage.setScene(welcomeScene);

        // Apply fade-in transition
        applyFadeInTransition(welcomeLayout);

        return welcomeScene; // Return the welcome scene
    }

    private void showLoginOptions() {
        VBox loginOptionsLayout = new VBox(10);
        loginOptionsLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #34495e;");

        Label loginLabel = new Label("Login as:");
        loginLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button adminLoginButton = new Button("Admin");
        styleButton(adminLoginButton);
        adminLoginButton.setOnAction(e -> navigateToLogin("ADMIN"));

        Button pmLoginButton = new Button("Project Manager");
        styleButton(pmLoginButton);
        pmLoginButton.setOnAction(e -> navigateToLogin("PROJECT_MANAGER"));

        Button employeeLoginButton = new Button("Employee");
        styleButton(employeeLoginButton);
        employeeLoginButton.setOnAction(e -> navigateToLogin("EMPLOYEE"));

        Button backButton = new Button("Back");
        styleButton(backButton);
        backButton.setOnAction(e -> primaryStage.setScene(initWelcomeScene())); // Use the initWelcomeScene method to get the scene

        loginOptionsLayout.getChildren().addAll(loginLabel, adminLoginButton, pmLoginButton, employeeLoginButton, backButton);

        Scene loginOptionsScene = new Scene(loginOptionsLayout, 400, 300);
        primaryStage.setScene(loginOptionsScene);

        // Apply fade-in transition
        applyFadeInTransition(loginOptionsLayout);
    }

    private void navigateToLogin(String role) {
        MainLogin login = new MainLogin(primaryStage, role, taskManager);
        login.showLoginScene();
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px; -fx-background-color: #1abc9c; -fx-text-fill: white; -fx-background-radius: 5;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px; -fx-background-color: #16a085; -fx-text-fill: white; -fx-background-radius: 5;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px; -fx-background-color: #1abc9c; -fx-text-fill: white; -fx-background-radius: 5;"));
    }

    private void applyFadeInTransition(VBox layout) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), layout);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public void showAdminMenu() {
        if (primaryStage != null) {
            AdminMenu adminMenu = new AdminMenu(primaryStage, taskManager, this);
            adminMenu.showAdminMenu();
        } else {
            System.err.println("primaryStage is null when attempting to show admin menu.");
        }
    }

    public void showProjectManagerMenu() {
        if (primaryStage != null) {
            ProjectmanagerMenu projectManagerMenu = new ProjectmanagerMenu(primaryStage, taskManager, this);
            projectManagerMenu.showProjectManagerMenu();
        } else {
            System.err.println("primaryStage is null when attempting to show project manager menu.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
