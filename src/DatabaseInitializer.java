import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

        public static void dropTables(Connection connection) throws SQLException {
            // Drop payments table


                Statement statement = connection.createStatement();

                // Drop dependent tables first

                // Drop foreign key constraints first
//                String dropForeignKeyConstraint = "alter table coders drop constraint CONSTRAINT_100D";
//                // Execute the drop foreign key statement
//
//                String dropCodersTable = "DROP TABLE IF EXISTS coders";
//                statement.executeUpdate(dropForeignKeyConstraint);
                statement.execute("DROP TABLE IF EXISTS bids");
                statement.execute("DROP TABLE IF EXISTS job_posts");
                statement.execute("DROP TABLE IF EXISTS messages");
                statement.execute("DROP TABLE IF EXISTS payments");
                statement.execute("DROP TABLE IF EXISTS coders");
            String dropPaymentsTable = "DROP TABLE IF EXISTS payments";
            connection.createStatement().executeUpdate(dropPaymentsTable);

            String dropBidsTable = "DROP TABLE IF EXISTS bids";
            connection.createStatement().executeUpdate(dropBidsTable);
            // Drop job_posts table
            String dropJobPostTable = "DROP TABLE IF EXISTS job_posts";
            connection.createStatement().executeUpdate(dropJobPostTable);

            // Drop coders table
            String dropCoderTable = "DROP TABLE IF EXISTS coders";
            connection.createStatement().executeUpdate(dropCoderTable);

            // Drop users table
            String dropUserTable = "DROP TABLE IF EXISTS users";
            connection.createStatement().executeUpdate(dropUserTable);

            System.out.println("Tables dropped if they existed.");
        }

    public static void createTables(Connection connection) throws SQLException {
        // Create users table
        String createUserTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(255) NOT NULL," +
                "password VARCHAR(255) NOT NULL," +
                "email VARCHAR(255) NOT NULL," +
                "user_type ENUM('buyer', 'coder', 'admin') NOT NULL," +
                "secret_key VARCHAR(255)" +
                ")";
        connection.createStatement().executeUpdate(createUserTable);
// Create messages table
        String createMessagesTable = "CREATE TABLE IF NOT EXISTS messages (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "sender_id INT NOT NULL," +
                "receiver_id INT NOT NULL," +
                "content VARCHAR(255) NOT NULL," +
                "timestamp TIMESTAMP NOT NULL," +
                "FOREIGN KEY (sender_id) REFERENCES users(id)," +
                "FOREIGN KEY (receiver_id) REFERENCES users(id)" +
                ")";
        connection.createStatement().executeUpdate(createMessagesTable);
        // Create coders table
        String createCoderTable = "CREATE TABLE IF NOT EXISTS coders (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(255) NOT NULL," +
                "skills VARCHAR(255) NOT NULL," +
                "experience_years INT NOT NULL," +
                "available BOOLEAN NOT NULL," +
                ")";

        connection.createStatement().executeUpdate(createCoderTable);

        // Create job_posts table
        String createJobPostTable = "CREATE TABLE IF NOT EXISTS job_posts (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "description VARCHAR(255) NOT NULL," +
                "requirements VARCHAR(255) NOT NULL," +
                "budget DOUBLE NOT NULL," +
                "filled BOOLEAN NOT NULL" +
                ")";
        connection.createStatement().executeUpdate(createJobPostTable);

        // Create payments table
        String createPaymentsTable = "CREATE TABLE IF NOT EXISTS payments (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "user_id INT NOT NULL," +
                "amount DOUBLE NOT NULL," +
                "timestamp TIMESTAMP NOT NULL," +
                "status BOOLEAN NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES users(id)" +
                ")";
        connection.createStatement().executeUpdate(createPaymentsTable);

        // Create bids table
        String createBidsTable = "CREATE TABLE IF NOT EXISTS bids ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "user_id INT,"
                + "coder_id INT,"
                + "bid_amount DOUBLE,"
                + "bid_date TIMESTAMP,"
                + "status VARCHAR(20),"
                + "FOREIGN KEY (user_id) REFERENCES users(id),"
                + "FOREIGN KEY (coder_id) REFERENCES coders(id)"
                + ")";

        connection.createStatement().executeUpdate(createBidsTable);


        System.out.println("Tables created if they didn't exist.");
    }

    public static void insertInitialData(Connection connection) throws SQLException {
        // Insert sample Indian values into users table
        String insertUser1 = "INSERT INTO users (username, password, email, user_type, secret_key) " +
                "VALUES ('karanp19', 'karanp19', 'karanp19@gmail.com', 'admin', 'karan')";
        String insertUser2 = "INSERT INTO users (username, password, email, user_type, secret_key) " +
                "VALUES ('neha', 'password2', 'neha@example.com', 'coder', 'phatnani')";
        String insertUser3 = "INSERT INTO users (username, password, email, user_type, secret_key) " +
                "VALUES ('john', 'password3', 'john@example.com', 'buyer', NULL)";
        // Insert sample Indian values into coders table
        String insertCoder1 = "INSERT INTO coders (username, skills, experience_years, available) VALUES ('akash', 'Java,Python', 3, true)";
        String insertCoder2 = "INSERT INTO coders (username, skills, experience_years, available) VALUES ('isha', 'JavaScript,HTML,CSS', 2, false)";

        // Insert more sample data as needed

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertUser1);
            statement.executeUpdate(insertUser2);
            statement.executeUpdate(insertUser3);
            statement.executeUpdate(insertCoder1);
            statement.executeUpdate(insertCoder2);
            // Execute more insert statements
        }

        System.out.println("Initial data inserted into the database.");
    }

}
