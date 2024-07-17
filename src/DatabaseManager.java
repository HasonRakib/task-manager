import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:new_task_manager.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            // Create users table
            String usersTable = "CREATE TABLE IF NOT EXISTS users (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "username TEXT NOT NULL UNIQUE, " +
                                "password TEXT NOT NULL, " +
                                "role TEXT NOT NULL, " +
                                "userId TEXT NOT NULL UNIQUE)";
            stmt.execute(usersTable);

            // Create tasks table
            String tasksTable = "CREATE TABLE IF NOT EXISTS tasks (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "taskId TEXT NOT NULL UNIQUE, " +
                                "description TEXT NOT NULL, " +
                                "completed BOOLEAN NOT NULL CHECK (completed IN (0, 1)), " +
                                "assigned_to TEXT, " +
                                "status TEXT NOT NULL, " +
                                "FOREIGN KEY (assigned_to) REFERENCES users(userId))";
            stmt.execute(tasksTable);

             // Add new columns if they doesn't exist
            addColumnIfNotExists(stmt, "tasks", "due_date", "TEXT");
            addColumnIfNotExists(stmt, "tasks", "comments", "TEXT");
            addColumnIfNotExists(stmt, "tasks", "attachments", "TEXT");


            // Initializes the admin user( there is only one admin in this system)
            String adminUser = "INSERT OR IGNORE INTO users (username, password, role , userId) VALUES ('admin', 'admin', 'ADMIN', 'ADMIN_ID')";
            stmt.execute(adminUser);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static void addColumnIfNotExists(Statement stmt, String tableName, String columnName, String columnType) throws SQLException {
        try {
            String addColumnQuery = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " + columnType;
            stmt.execute(addColumnQuery);
        } catch (SQLException e) {
            // Ignore the error if the column already exists
            if (!e.getMessage().contains("duplicate column name")) {
                throw e;
            }
        }
    }
  
}
