public class Notification {
    private String message;
    private boolean read;

    public Notification (String message) {
        this.message = message;
        this.read = false;// by default, the messages are not read
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read =read;
    }

    @Override
    public String toString() {
        return message;
    }
}