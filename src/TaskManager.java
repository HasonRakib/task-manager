import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.*;

public class TaskManager {
    public TaskManager() {
       DatabaseManager.initializeDatabase();
    }

     // Task-related methods
    public void createTask(String description, int assignedToUserId) {
        String sql = "INSERT INTO tasks(description, completed, assigned_to, status) VALUES(?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setString(1, description);
             pstmt.setBoolean(2, false);
             pstmt.setInt(3, assignedToUserId);
             pstmt.setString(4, "Pending");
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
                    rs.getString("description"),
                    rs.getInt("assigned_to")
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

    public Optional<Task> readTask(int id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        Task task = null;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                task = new Task(
                    rs.getInt("id"),
                    rs.getString("description"),
                    rs.getInt("assigned_to")
                );
                task.setCompleted(rs.getBoolean("completed"));
                task.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.ofNullable(task);
    }

    public boolean updateTask(int id, String newDescription, boolean completed) {
        String sql = "UPDATE tasks SET description = ?, completed = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newDescription);
            pstmt.setBoolean(2, completed);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public void assignTaskToEmployee(int taskId, int employeeId) {
        String sql = "UPDATE tasks SET assigned_to = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);
            pstmt.setInt(2, taskId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Task> readTasksByUser(int userId) {
        String sql = "SELECT * FROM tasks WHERE assigned_to = ?";
        List<Task> tasks = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setInt(1, userId);
             ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Task task = new Task(
                    rs.getInt("id"),
                    rs.getString("description"),
                    rs.getInt("assigned_to")
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

    public void updateTaskStatus(int taskId, String status) {
        String sql = "UPDATE tasks SET status = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, taskId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // User-related methods
     public void addUser(String username, String password, Role role) {
        String sql = "INSERT INTO users(username, password, role) VALUES(?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role.name());
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
                    Role.valueOf(rs.getString("role"))
                );
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
                    Role.valueOf(rs.getString("role"))
                );
                users.add(user);
            }
        } catch (SQLException e) {   
        System.out.println(e.getMessage());
        }
    return users;
}
}


