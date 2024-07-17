import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminMenu {
    private Stage primaryStage;
    private TaskManager taskManager;
    private Main mainApp;

    public AdminMenu(Stage primaryStage, TaskManager taskManager, Main mainApp) {
        this.primaryStage = primaryStage;
        this.taskManager = taskManager;
        this.mainApp = mainApp;
    }

    public void showAdminMenu() {
        VBox adminMenuLayout = new VBox(10);
        adminMenuLayout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Button viewTasksButton = new Button("View All Tasks");
        styleButton(viewTasksButton);
        viewTasksButton.setOnAction(e -> viewAllTasks());

        Button addTaskButton = new Button("Add Task");
        styleButton(addTaskButton);
        addTaskButton.setOnAction(e -> addTask());

        Button deleteTaskButton = new Button("Delete Task");
        styleButton(deleteTaskButton);
        deleteTaskButton.setOnAction(e -> deleteTask());

        Button logoutButton = new Button("Logout");
        styleButton(logoutButton);
        logoutButton.setOnAction(e -> logout());

        adminMenuLayout.getChildren().addAll(viewTasksButton, addTaskButton, deleteTaskButton, logoutButton);

        Scene adminMenuScene = new Scene(adminMenuLayout, 400, 300);
        primaryStage.setScene(adminMenuScene);
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px; -fx-background-color: #1abc9c; -fx-text-fill: white; -fx-background-radius: 5;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px; -fx-background-color: #16a085; -fx-text-fill: white; -fx-background-radius: 5;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px; -fx-background-color: #1abc9c; -fx-text-fill: white; -fx-background-radius: 5;"));
    }

    private void viewAllTasks() {
        // Placeholder for viewing all tasks
    }

    private void addTask() {
        // Placeholder for adding a task
    }

    private void deleteTask() {
        // Placeholder for deleting a task
    }

    private void logout() {
        // Go back to the welcome screen
        mainApp.initWelcomeScene();
    }
}
