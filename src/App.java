import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

 class Task {
    private int id;
    private String description;
    private boolean completed;

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.completed = false;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Task{id=" + id + ", description='" + description + '\'' + ", completed=" + (completed ? "Yes" : "No")+ '}';
    }
}

class TaskManager {
    private List<Task> tasks;
    private int nextId;

    public TaskManager() {
        tasks = new ArrayList<>();
        nextId = 1;
    }

    public void createTask(String description) {
        Task task = new Task(nextId++, description);
        tasks.add(task);
    }

    public List<Task> readTasks() {
        return tasks;
    }

    public Optional<Task> readTask(int id) {
        return tasks.stream().filter(task -> task.getId() == id).findFirst();
    }

    public boolean updateTask(int id, String newDescription, boolean completed) {
        Optional<Task> optionalTask = readTask(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setDescription(newDescription);
            task.setCompleted(completed);
            return true;
        }
        return false;
    }

    public boolean deleteTask(int id) {
        return tasks.removeIf(task -> task.getId() == id);
    }
}

public class App {
    private static TaskManager taskManager = new TaskManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nTask Manager");
            System.out.println("1. Create task");
            System.out.println("2. View all tasks");
            System.out.println("3. View task by ID");
            System.out.println("4. Update task");
            System.out.println("5. Delete task");
            System.out.println("6. Exit");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    createTask();
                    break;
                case 2:
                    viewAllTasks();
                    break;
                case 3:
                    viewTaskById();
                    break;
                case 4:
                    updateTask();
                    break;
                case 5:
                    deleteTask();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createTask() {
        System.out.println("Enter task description:");
        String description = scanner.nextLine();
        taskManager.createTask(description);
        System.out.println("Task created.");
    }

    private static void viewAllTasks() {
        System.out.println("All tasks:");
        for (Task task : taskManager.readTasks()) {
            System.out.println(task);
        }
    }

    private static void viewTaskById() {
        System.out.println("Enter task ID:");
        int id = Integer.parseInt(scanner.nextLine());
        taskManager.readTask(id).ifPresentOrElse(
            task -> System.out.println(task),
            () -> System.out.println("Task not found.")
        );
    }

    private static void updateTask() {
        System.out.println("Enter task ID:");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter new task description:");
        String description = scanner.nextLine();
        System.out.println("Is the task completed? (true/false):");
        boolean completed = Boolean.parseBoolean(scanner.nextLine());

        if (taskManager.updateTask(id, description, completed)) {
            System.out.println("Task updated.");
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void deleteTask() {
        System.out.println("Enter task ID:");
        int id = Integer.parseInt(scanner.nextLine());

        if (taskManager.deleteTask(id)) {
            System.out.println("Task deleted.");
        } else {
            System.out.println("Task not found.");
        }
    }
}