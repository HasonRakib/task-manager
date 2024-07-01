import java.util.Optional;
import java.util.Scanner;

public class App {
    private static TaskManager taskManager = new TaskManager();
    private static Scanner scanner = new Scanner(System.in);
    private static User loggedInUser = null;


    public static void main(String[] args) {
        while (true) {
            if (loggedInUser == null) {
                login();
            } else {
                switch (loggedInUser.getRole()) {
                    case ADMIN:
                        showAdminMenu();
                        break;
                    case PROJECT_MANAGER:
                        showProjectManagerMenu();
                        break;
                    case EMPLOYEE:
                        showEmployeeMenu();
                        break;
                    default:
                        System.out.println("Unknown role.");
                        loggedInUser = null;
                }
            }
        }
    }

    private static void login() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        Optional<User> user = taskManager.authenticateUser(username, password);
        if (user.isPresent()) {
            loggedInUser = user.get();
            System.out.println("Login successful! Logged in as " + loggedInUser.getRole());
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private static void showAdminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Add Project Manager");
            System.out.println("2. Add Task");
            System.out.println("3. Logout");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addProjectManager();
                    break;
                case 2:
                    addTask();
                    break;
                case 3:
                    loggedInUser = null;
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addProjectManager() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        taskManager.addUser(username, password, Role.PROJECT_MANAGER);
        System.out.println("Project Manager added.");
    }

    private static void addTask() {
        System.out.println("Enter task description:");
        String description = scanner.nextLine();
        System.out.println("Enter Project Manager ID to assign this task:");
        int projectId = Integer.parseInt(scanner.nextLine());
        taskManager.createTask(description, projectId);
        System.out.println("Task added and assigned to Project Manager ID " + projectId);
    }

    private static void showProjectManagerMenu() {
        while (true) {
            System.out.println("\nProject Manager Menu");
            System.out.println("1. Register Employee");
            System.out.println("2. Assign Task to Employee");
            System.out.println("3. View All Tasks");
            System.out.println("4. Logout");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    registerEmployee();
                    break;
                case 2:
                    assignTaskToEmployee();
                    break;
                case 3:
                    viewAllTasks();
                    break;
                case 4:
                    loggedInUser = null;
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void registerEmployee() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        taskManager.addUser(username, password, Role.EMPLOYEE);
        System.out.println("Employee registered.");
    }

    private static void assignTaskToEmployee() {
        System.out.println("Enter task ID:");
        int taskId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter Employee ID:");
        int employeeId = Integer.parseInt(scanner.nextLine());
        taskManager.assignTaskToEmployee(taskId, employeeId);
    }

    private static void viewAllTasks() {
        System.out.println("All tasks:");
        for (Task task : taskManager.readTasks()) {
            System.out.println(task);
        }
    }

    private static void showEmployeeMenu() {
        while (true) {
            System.out.println("\nEmployee Menu");
            System.out.println("1. View My Tasks");
            System.out.println("2. Update Task Status");
            System.out.println("3. Logout");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    viewMyTasks();
                    break;
                case 2:
                    updateTaskStatus();
                    break;
                case 3:
                    loggedInUser = null;
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewMyTasks() {
        System.out.println("My tasks:");
        for (Task task : taskManager.readTasksByUser(loggedInUser.getId())) {
            System.out.println(task);
        }
    }

    private static void updateTaskStatus() {
        System.out.println("Enter task ID:");
        int taskId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter new status (Pending, Working on it, Completed):");
        String status = scanner.nextLine();
        taskManager.updateTaskStatus(taskId, status);
    }

   
}