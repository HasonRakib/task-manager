import java.util.Optional;
import java.util.Scanner;

public class App {
    private static TaskManager taskManager = new TaskManager();
    private static Scanner scanner = new Scanner(System.in);
   
    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Welcome to the Prodigy");
            System.out.println("1. Login");
            System.out.println("2. Exit");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    System.out.println("Exiting...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
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
            User loggedInUser = user.get();
            boolean loggedIn = true;
            while (loggedIn) {
                switch (loggedInUser.getRole()) {
                    case ADMIN:
                        loggedIn = showAdminMenu(loggedInUser);
                        break;
                    case PROJECT_MANAGER:
                        loggedIn = showProjectManagerMenu(loggedInUser);
                        break;
                    case EMPLOYEE:
                        loggedIn = showEmployeeMenu(loggedInUser);
                        break;
                    default:
                        System.out.println("Unknown role. Access denied.");
                        loggedIn = false;
                }
            }
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }
    
    private static boolean showAdminMenu(User user){
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Add Project Manager");
            System.out.println("2. View All Users");
            System.out.println("3. Create Task");
            System.out.println("4. View All Tasks");
            System.out.println("5. Assign Task to Project Manager");
            System.out.println("6. Logout");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                addUser(Role.PROJECT_MANAGER);
                    break;
                case 2:
                    viewAllUsers();
                    break;
                case 3:
                    createTask();
                    break;
                case 4:
                    viewAllTasks();
                    break;
                case 5:
                    assignTaskToProjectManager();
                    break;
                case 6:
                    return false; //logging out   
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addUser(Role role) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        taskManager.addUser(username, password, role);
        System.out.println(role.name() + " added successfully.");
    }

    private static void createTask() {
        System.out.println("Enter task description:");
        String description = scanner.nextLine();
        System.out.println("Enter Project Manager ID to assign this task:");
        int projectId = Integer.parseInt(scanner.nextLine());

        taskManager.createTask(description, projectId);
        System.out.println("Task added and assigned to Project Manager ID " + projectId);
    }

    private static void viewAssignedTasks(User user) {
        for (Task task : taskManager.readTasksByUser(user.getId())) {
            System.out.println(task);
        }
    }

    private static  boolean showProjectManagerMenu(User user) {
        while (true) {
            System.out.println("\nProject Manager Menu");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Assign Task to Employee");
            System.out.println("4. View My Tasks");
            System.out.println("5. Update Task Status");
            System.out.println("6. Logout");
    
            int choice = Integer.parseInt(scanner.nextLine());
    
            switch (choice) {
                case 1:
                addUser(Role.EMPLOYEE);
                    break;
                case 2:
                viewAllUsers();
                    break;
                case 3:
                    assignTaskToEmployee();
                    break;
                case 4:
                viewAssignedTasks(user);
                    break;
                case 5:
                    updateTaskStatus();
                    break;
                case 6:
                    return false; // logging out
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }
    }

    private static void viewAllUsers() {
        System.out.println("All users:");
        for (User user : taskManager.getUsers()) {
            System.out.println(user);
        }
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

    private static boolean showEmployeeMenu(User user)  {
        while (true) {
            System.out.println("\nEmployee Menu");
            System.out.println("1. View My Tasks");
            System.out.println("2. Update Task Status");
            System.out.println("3. Logout");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    viewAssignedTasks(user);
                    break;
                case 2:
                    updateTaskStatus();
                    break;
                case 3:
                    return false;  // Logging out
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void updateTaskStatus() {
        System.out.println("Enter task ID:");
        int taskId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter new status (Pending, Working on it, Completed):");
        String status = scanner.nextLine();

        taskManager.updateTaskStatus(taskId, status);
        System.out.println("Task status updated.");
    }

     private static void assignTaskToProjectManager() {
        System.out.println("Enter task ID:");
        int taskId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter Project Manager ID:");
        int projectManagerId = Integer.parseInt(scanner.nextLine());

        taskManager.assignTaskToEmployee(taskId, projectManagerId);
        System.out.println("Task assigned to Project Manager.");
    }

   
}