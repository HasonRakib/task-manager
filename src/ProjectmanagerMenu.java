import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProjectmanagerMenu {
    private Stage primaryStage;
    private TaskManager taskManager;
    private Main main;

    public ProjectmanagerMenu(Stage primaryStage, TaskManager taskManager, Main main) {
        this.primaryStage = primaryStage;
        this.taskManager = taskManager;
        this.main = main;
    }

    public void showProjectManagerMenu() {
        VBox pmLayout = new VBox(10);
        pmLayout.setPadding(new Insets(20));
        pmLayout.setStyle("-fx-background-color: #3498db;");

        Button viewProjectsButton = new Button("View Projects");
        viewProjectsButton.setOnAction(e -> {
            // Replace with your logic to view projects
            System.out.println("Viewing projects...");
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            primaryStage.setScene(main.initWelcomeScene());
        });

        pmLayout.getChildren().addAll(viewProjectsButton, logoutButton);

        Scene pmScene = new Scene(pmLayout, 400, 300);
        primaryStage.setScene(pmScene);
        primaryStage.setTitle("Project Manager Menu");
    }
}
