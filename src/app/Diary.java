package app;

public class Diary {

    private String username;
    private String password;
    private boolean isLocked;

    public Diary (String username, String password) {
        validateInputs(username, password);
        this.username = username;
        this.password = password;
        this.isLocked = true;
    }

    private void validateInputs(String username, String password) {
       if (username == null || username.isEmpty()) {
           throw new IllegalArgumentException("Username cannot be empty");
       }

       if (password == null || password.isEmpty()) {
           throw new IllegalArgumentException("Password cannot be empty");
       }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLocked() {
        return isLocked;
    }
}
