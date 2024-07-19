import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminMenu {
    private Stage primaryStage;
    //private TaskManager taskManager;
    private Main main;

    public AdminMenu(Stage primaryStage, TaskManager taskManager, Main main) {
        this.primaryStage = primaryStage;
       // this.taskManager = taskManager;
        this.main = main;
    }

    public void showAdminMenu() {
        VBox adminLayout = new VBox(10);
        adminLayout.setPadding(new Insets(20));
        adminLayout.setStyle("-fx-background-color: #3498db;");

        Button viewTasksButton = new Button("View Tasks");
        viewTasksButton.setOnAction(e -> {
            // Replace with your logic to view tasks
            System.out.println("Viewing tasks...");
        });

        Button manageUsersButton = new Button("Manage Users");
        manageUsersButton.setOnAction(e -> {
            // Replace with your logic to manage users
            System.out.println("Managing users...");
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            primaryStage.setScene(main.initWelcomeScene()); // Use the initWelcomeScene method to get the scene
        });

        adminLayout.getChildren().addAll(viewTasksButton, manageUsersButton, logoutButton);

        Scene adminScene = new Scene(adminLayout, 400, 300);
        primaryStage.setScene(adminScene);
        primaryStage.setTitle("Admin Menu");
    }
}
