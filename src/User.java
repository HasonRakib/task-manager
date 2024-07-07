
public class User {
    private int id;
    private String username;
    private String password;
    private Role role;
   

    public User(int id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        //this.id = generateIdForRole(role);
    }

   /*  private String generateIdForRole(Role role) {
        switch (role) {
            case ADMIN:
                return "ADMIN"; // No ID generation for admin
            case PROJECT_MANAGER:
                return UniqueIdGenerator.generateProjectManagerId();
            case EMPLOYEE:
                return UniqueIdGenerator.generateEmployeeId();
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }*/


    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}

enum Role {
    ADMIN,
    PROJECT_MANAGER,
    EMPLOYEE,
}
