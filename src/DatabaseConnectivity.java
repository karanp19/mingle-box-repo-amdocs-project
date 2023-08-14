import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectivity {
    private static final String JDBC_URL = "jdbc:h2:~/Downloads/testing4"; // Change this URL as needed
    private static final String JDBC_USER = "username"; // Change username
    private static final String JDBC_PASSWORD = "password"; // Change password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }
}
