public class Task {
    private int id;
    private String taskId;
    private String description;
    private boolean completed;
    private int assignedTo;
    private String status;

    public Task(int id, String description, boolean completed, int assignedTo, String status) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.assignedTo = assignedTo;
        this.status = status;
    }

    public Task(int id, String taskId, String description, int assignedTo) {
        this.id = id;
        this.taskId = taskId;
        this.description = description;
        this.completed = false;
        this.assignedTo = assignedTo;
        this.status = "Pending";
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

    public int getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

     public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{id=" + id + ", description='" + description + '\'' + ", completed=" + (completed ? "Yes" : "No") + ", assignedTo=" + assignedTo + '}';
    }
}
