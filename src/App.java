import java.time.LocalDate;
import java.util.List;
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
                        displayNotifications(loggedInUser); // Display notifications for admin
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

     private static void displayNotifications(User user) {
        if (user.getRole() == Role.ADMIN) {
            List<Notification> notifications = taskManager.getNotifications();
            for (Notification notification : notifications) {
                if (!notification.isRead()) {
                    System.out.println("\nNew notification: " + notification.getMessage());
                    taskManager.markNotificationAsRead(notification);
                }
            }
            try {
                Thread.sleep(60000); // 1 minute delay
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
     }

    private static boolean showAdminMenu(User user) {
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Add Project Manager");
            System.out.println("2. View All Users");
            System.out.println("3. Create Task");
            System.out.println("4. View All Tasks");
            System.out.println("5. Assign Task to Project Manager");
            System.out.println("6. Delete Project Manager");
            System.out.println("7. Delete Task");
            System.out.println("8. Logout");

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
                    deleteUser();
                    break;
                case 7:
                    deleteTask();//preapre for new changes on the admin menu
                    break;
                case 8:
                    return false; // logging out   
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
        String projectManagerId = scanner.nextLine();
        System.out.println("Enter due date (YYYY-MM-DD):");
        String dueDateString = scanner.nextLine();
        LocalDate dueDate;
        try {
            dueDate = LocalDate.parse(dueDateString); // Parse due date input
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }


        taskManager.createTask(description, projectManagerId, dueDate);
        System.out.println("Task added and assigned to Project Manager ID " + projectManagerId);
    }

    private static void viewAssignedTasks(User user) {
        for (Task task : taskManager.readTasksByUser(user.getUserId())) {
            System.out.println(task);
        }
    }

    private static boolean showProjectManagerMenu(User user) {
        while (true) {
            System.out.println("\nProject Manager Menu");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Assign Task to Employee");
            System.out.println("4. View My Tasks");
            System.out.println("5. Update Task Status");
            System.out.println("6. Delete Employee");
            System.out.println("7. Delete Task");
            System.out.println("8. Logout");

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
                    deleteUser();
                    break;
                case 7:
                    deleteTask();
                    break;
                case 8:
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
        String taskId = scanner.nextLine();
        System.out.println("Enter Employee ID:");
        String employeeId= scanner.nextLine();

        String dueDateString = scanner.nextLine();
        @SuppressWarnings("unused")
        LocalDate dueDate;
        try {
            dueDate = LocalDate.parse(dueDateString); // Parse due date input
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        taskManager.assignTaskToEmployee(taskId, employeeId, null);
        System.out.println("Task assigned to Employee ID " + employeeId);
    }

    private static void viewAllTasks() {
        System.out.println("All tasks:");
        for (Task task : taskManager.readTasks()) {
            System.out.println(task);
        }
    }

    private static boolean showEmployeeMenu(User user) {
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
        String taskId = scanner.nextLine();
        System.out.println("Enter new status (Pending, Working on it, Completed):");
        String status = scanner.nextLine();

        taskManager.updateTaskStatus(taskId, status);
        System.out.println("Task status updated.");
    }

    private static void assignTaskToProjectManager() {
        System.out.println("Enter task ID:");
        String taskId = scanner.nextLine();
        System.out.println("Enter Project Manager ID:");
        String projectManagerId = scanner.nextLine();

        System.out.print("Due Date (YYYY-MM-DD): ");
        String dueDateString = scanner.nextLine();
        @SuppressWarnings("unused")
        LocalDate dueDate;
        try {
            dueDate = LocalDate.parse(dueDateString); // Parse due date input
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        taskManager.assignTaskToEmployee(taskId, projectManagerId, null);
        System.out.println("Task assigned to Project Manager.");
    }

    private static void deleteUser() {
        System.out.println("Enter User ID to delete:");
        String userId = scanner.nextLine();
        boolean result = taskManager.deleteUser(userId);
        if (result) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("Failed to delete user.");
        }
    }
    
    private static void deleteTask() {
        System.out.println("Enter Task ID to delete:");
        String taskId = scanner.nextLine();
        boolean result = taskManager.deleteTask(taskId);
        if (result) {
            System.out.println("Task deleted successfully.");
        } else {
            System.out.println("Failed to delete task.");
        }
    }
    
}
