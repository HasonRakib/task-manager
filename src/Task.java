import java.time.LocalDate;

public class Task {
    private int id;
    private String taskId;
    private String description;
    private boolean completed;
    private String assignedTo;
    private String status;
    private LocalDate dueDate; // New field for due date
    private String comments; // New field for comments
    private String attachments; // New field for attachments


    public Task(int id, String description, boolean completed, String assignedTo, String status, LocalDate dueDate) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.assignedTo = assignedTo;
        this.status = status;
        this.dueDate = dueDate; // Initialize to null initially
    }

    public Task(int id, String taskId, String description, String assignedTo, LocalDate dueDate) {
        this.id = id;
        this.taskId = taskId;
        this.description = description;
        this.completed = false;
        this.assignedTo = assignedTo;
        this.status = "Pending";
        this.dueDate = null; // Initialize to null initially
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

     public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "Task{Task ID = " + taskId + " \ndescription= " + description + " \ncompleted= " + (completed ? "Yes" : "No") + "\nassignedTo= " + assignedTo +"\nStatus: " + status + "\nDue Date: " + dueDate + 
               "\nComments: " + comments + "\nAttachments: " + attachments;
    } 
    
}
