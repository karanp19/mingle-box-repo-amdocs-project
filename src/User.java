public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private UserType userType;
    private String secretKey;


    public User() {
    }

    public int getUserId() {
        return id;
    }


    public enum UserType {
        buyer, coder, admin
    }

        // Constructor
        public User(int id, String username, String password, String email, UserType userType, String secretKey) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.email = email;
            this.userType = userType;
            this.secretKey = secretKey;
        }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    public String getSecretKey() {
        return secretKey;
    }

}
