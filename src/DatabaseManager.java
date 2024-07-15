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
             String addDueDateColumn = "ALTER TABLE tasks ADD COLUMN due_date TEXT";// this is for adding the duedate column
             stmt.execute(addDueDateColumn);
             String addcommentsColumn = "ALTER TABLE tasks ADD COLUMN comments TEXT";//this is for adding a comments column
             stmt.execute(addcommentsColumn);
             String addattachmentsColumn = "ALTER TABLE tasks ADD COLUMN attachments TEXT";//this is for adding a comments column
             stmt.execute(addattachmentsColumn);


            // Initializes the admin user( there is only one admin in this system)
            String adminUser = "INSERT OR IGNORE INTO users (username, password, role , userId) VALUES ('admin', 'admin', 'ADMIN', 'ADMIN_ID')";
            stmt.execute(adminUser);

        } catch (SQLException e) {// If the error is because the column already exists, ignore it
            if (!e.getMessage().contains("duplicate column name")) {
            System.out.println(e.getMessage());
        }
    } 
  }
}
