import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class Task {
    private int id;
    private String description;
    private boolean completed;
    private int assignedToUserId;

    public Task(int id, String description, int assignedToUserId) {
        this.id = id;
        this.description = description;
        this.completed = false;
        this.assignedToUserId = assignedToUserId;
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

    public int getAssignedToUserId() {
        return assignedToUserId;
    }

    public void setAssignedToUserId(int assignedToUserId) {
        this.assignedToUserId = assignedToUserId;
    }

    @Override
    public String toString() {
        return "Task{id=" + id + ", description='" + description + '\'' + ", completed=" + (completed ? "Yes" : "No") + ", assignedToUserId=" + assignedToUserId + '}';
    }
}

public class TaskManager {
    private List<Task> tasks;
    private List<User> users;
    private int nextTaskId;
    private int nextUserId;

    public TaskManager() {
        tasks = new ArrayList<>();
        users = new ArrayList<>();
        nextTaskId = 1;
        nextUserId = 1;
    }

    public void addUser(String username, String password, Role role) {
        User user = new User(nextUserId++, username, password, role);
        users.add(user);
    }

    public Optional<User> authenticateUser(String username, String password) {
        return users.stream()
            .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
            .findFirst();
    }

    public void createTask(String description, int assignedToUserId) {
        Task task = new Task(nextTaskId++, description, assignedToUserId);
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

    public void assignTaskToEmployee(int taskId, int employeeId) {
        Optional<Task> optionalTask = readTask(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setAssignedToUserId(employeeId);
            System.out.println("Task assigned to Employee ID " + employeeId);
        } else {
            System.out.println("Task not found.");
        }
    }

    public List<Task> readTasksByUser(int userId) {
        return tasks.stream().filter(task -> task.getAssignedToUserId() == userId).collect(Collectors.toList());
    }

    public void updateTaskStatus(int taskId, String status) {
        Optional<Task> optionalTask = readTask(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            switch (status.toLowerCase()) {
                case "pending":
                    task.setCompleted(false);
                    break;
                case "working on it":
                    task.setCompleted(false);
                    break;
                case "completed":
                    task.setCompleted(true);
                    break;
                default:
                    System.out.println("Invalid status. Please use 'Pending', 'Working on it', or 'Completed'.");
                    return;
            }
            System.out.println("Task status updated.");
            // Notify Project Manager and Admin about the status update (for now, just print messages)
            System.out.println("Notification: Task ID " + taskId + " status updated to " + status + ".");
        } else {
            System.out.println("Task not found.");
        }
    }
}
