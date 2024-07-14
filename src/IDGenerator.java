public class IDGenerator {
    private static int taskCounter = 100;
    private static int projectManagerCounter = 200;
    private static int employeeCounter = 300;

    public static String generateTaskId() {
        return "TASK" + (++taskCounter);
    }

    public static String generateProjectManagerId() {
        return "PM" + (++projectManagerCounter);
    }

    public static String generateEmployeeId() {
        return "EMP" + (++employeeCounter);
    }
}
