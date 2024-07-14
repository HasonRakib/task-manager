import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.*;
//import java.time.LocalDate;
import java.time.LocalDate;

public class TaskManager {
    public TaskManager() {
        DatabaseManager.initializeDatabase();
    }

    // Task-related methods
    public void createTask(String description, String assignedToUserId, LocalDate dueDate) {
        String taskId = IDGenerator.generateTaskId();
        String sql = "INSERT INTO tasks(taskId, description, completed, assigned_to, status, due_date) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, taskId);
            pstmt.setString(2, description);
            pstmt.setBoolean(3, false);
            pstmt.setString(4, assignedToUserId);
            pstmt.setString(5, "Pending");
            pstmt.setString(6, dueDate.toString()); // Assuming dueDate is stored as a string in the database
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Task> readTasks() {
        String sql = "SELECT * FROM tasks";
        List<Task> tasks = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Task task = new Task(
                    rs.getInt("id"),
                    rs.getString("taskId"),
                    rs.getString("description"),
                    rs.getString("assigned_to"), 
                    null
                );
                task.setCompleted(rs.getBoolean("completed"));
                task.setStatus(rs.getString("status"));
                // Retrieve and set due date from database
                String dueDateString = rs.getString("due_date");
                if (dueDateString != null) {
                LocalDate dueDate = LocalDate.parse(dueDateString);
                task.setDueDate(dueDate);
                } 
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return tasks;
    }

    public Optional<Task> readTask(String taskId) {
        String sql = "SELECT * FROM tasks WHERE taskId = ?";
        Task task = null;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, taskId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                task = new Task(
                    rs.getInt("id"),
                    rs.getString("taskId"),
                    rs.getString("description"),
                    rs.getString("assigned_to"), null
                );
                task.setCompleted(rs.getBoolean("completed"));
                task.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.ofNullable(task);
    }

    public boolean updateTask(String taskId, String newDescription, boolean completed) {
        String sql = "UPDATE tasks SET description = ?, completed = ? WHERE taskId = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newDescription);
            pstmt.setBoolean(2, completed);
            pstmt.setString(3, taskId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean deleteTask(String taskId) {
        String sql = "DELETE FROM tasks WHERE taskId = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, taskId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean deleteUser(String userId) {
        String sql = "DELETE FROM users WHERE userId = ?";
    
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    
        return false;
    }
    

    public void assignTaskToEmployee(String taskId, String employeeId, LocalDate dueDate) {
        String sql = "UPDATE tasks SET assigned_to = ? , due_date = ? WHERE taskId = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employeeId);//come back here later
            pstmt.setDate(2, Date.valueOf(dueDate)); // Convert LocalDate to SQL Date
            pstmt.setString(3, taskId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Task> readTasksByUser(String userId) {
        String sql = "SELECT * FROM tasks WHERE assigned_to = ?";
        List<Task> tasks = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Task task = new Task(
                    rs.getInt("id"),
                    rs.getString("taskId"),
                    rs.getString("description"),
                    rs.getString("assigned_to"), null
                );
                task.setCompleted(rs.getBoolean("completed"));
                task.setStatus(rs.getString("status"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return tasks;
    }

    public void updateTaskStatus(String taskId, String status) {
        String sql = "UPDATE tasks SET status = ? WHERE taskId = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setString(2, taskId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // User-related methods
    public void addUser(String username, String password, Role role) {
        String userId;
        if (role == Role.PROJECT_MANAGER) {
            userId = IDGenerator.generateProjectManagerId();
        } else if (role == Role.EMPLOYEE) {
            userId = IDGenerator.generateEmployeeId();
        } else {
            userId = "ADMIN"; // Admin ID is fixed
        }

        String sql = "INSERT INTO users(username, password, role, userId) VALUES(?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role.name());
            pstmt.setString(4, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Optional<User> authenticateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        User user = null;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    Role.valueOf(rs.getString("role")),
                    rs.getString("userId")
                );
                System.out.println("Logged in as: " + user.getUsername() + " (ID: " + user.getUserId() + ")");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.ofNullable(user);
    }

    public List<User> getUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    Role.valueOf(rs.getString("role")),
                    rs.getString("userId")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
}
