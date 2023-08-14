import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static int userIdCounter = 1;
    private static int coderIdCounter = 1;
    private static int jobIdCounter = 1;
    private static int messageIdCounter = 1;
    private static int paymentIdCounter = 1;
    private int bidIdCounter = 1000;

    private Connection connection;
    private Scanner scanner;

    public Main(Connection connection) {
        this.connection = connection;
        this.scanner = new Scanner(System.in);
    }

    public void startMarketplace() {
        System.out.println("Welcome to the Marketplace!");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Login");
            System.out.println("2. Register as Buyer");
            System.out.println("3. Register as Coder");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1 -> login();
                case 2 -> register(User.UserType.buyer);
                case 3 -> register(User.UserType.coder);
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void login() {
        System.out.println("Login");
        System.out.print("Username: ");
        String username = scanner.nextLine();

        try {
            UserDAO userDAO = new UserDAO(connection);
            User currentUser = userDAO.getCurrentUserByUsername(connection, username);

            if (currentUser != null) {
                System.out.print("Password: ");
                String password = scanner.nextLine();
                System.out.print("Secret Key: ");
                String secretKey = scanner.nextLine();

                if (validateLogin(currentUser, password, secretKey)) {
                    System.out.println("\nHello, " + currentUser.getUsername() + "!");
                    handleUserOptions(currentUser);
                } else {
                    System.out.println("Login failed. Invalid credentials.");
                }
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean validateLogin(User user, String password, String secretKey) {
        User.UserType userType = user.getUserType();

        if (userType == User.UserType.admin && "karan".equals(secretKey)) {
            return user.getPassword().equals(password);
        } else if (userType == User.UserType.buyer && "phatnani".equals(secretKey)) {
            return user.getPassword().equals(password);
        } else if (userType == User.UserType.coder && secretKey.isEmpty()) {
            return user.getPassword().equals(password);
        }

        return false;
    }

    private void register(User.UserType userType) {
        System.out.println("User Registration");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        try {
            if (userType == User.UserType.coder) {
                System.out.print("Skills: ");
                String skills = scanner.nextLine();
                System.out.print("Experience Years: ");
                int experienceYears = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                System.out.print("Available (true/false): ");
                boolean available = scanner.nextBoolean();
                scanner.nextLine(); // Consume the newline character

                Coder coder = new Coder(coderIdCounter++, username, skills, experienceYears, available);
                CoderDAO coderDAO = new CoderDAO(connection);
                coderDAO.createCoder(coder);
            } else {
                System.out.print("Secret Key: ");
                String secretKey = scanner.nextLine();

                User user = new User(userIdCounter++, username, password, email, userType, secretKey);
                UserDAO userDAO = new UserDAO(connection);
                userDAO.createUser(user, connection);
            }

            System.out.println("Registration completed successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleUserOptions(User currentUser) {
        while (true) {
            System.out.println("\nUser Menu:");
            switch (currentUser.getUserType()) {
                case admin -> {
                    System.out.println("1. Create User");
                    System.out.println("2. Create Coder");
                    System.out.println("3. Post Job");
                    System.out.println("4. Browse Coders");
                    System.out.println("5. Bid for Coder");
                    System.out.println("6. Send Message");
                    System.out.println("7. Make Payment");
                    System.out.println("8. Update User");
                    System.out.println("9. Delete User");
                    System.out.println("10. Update Coder");
                    System.out.println("11. Delete Coder");
                    System.out.println("12. Change Payment Status");
                    System.out.println("13. Finalize Bids");
                    System.out.println("14. Get Bids by User ID");
                    System.out.println("15. Update Bid");
                    System.out.println("16. Delete Bid");
                    System.out.println("17. Get Users by Type");
                    System.out.println("18. Get Users by Username");
                    System.out.println("19. Get All Users");
                    System.out.println("20. Update Users");
                    System.out.println("21. Delete Users");
                    System.out.println("22. Get User by ID");
                    System.out.println("23. Get JobPost by ID");
                    System.out.println("24. Get All JobPosts");
                    System.out.println("25. Update JobPosts");
                    System.out.println("26. Delete JobPosts");
                    System.out.println("27. Get Messages Between Users");
                    System.out.println("28. Create Payment");
                    System.out.println("29. Get Payment by ID");
                    System.out.println("30. Update Payment");
                    System.out.println("31. Get Payments by User ID");
                    System.out.println("32. Logout");
                }
                case coder -> {
                    System.out.println("1. Browse Coders");
                    System.out.println("2. View Bids by User ID");
                    System.out.println("3. Change Bid Status");
                    System.out.println("4. Logout");
                }
                case buyer -> {
                    System.out.println("1. Browse Coders");
                    System.out.println("2. Bid for Coder");
                    System.out.println("3. Make Payment");
                    System.out.println("4. View Bids by User ID");
                    System.out.println("5. Change Bid Status");
                    System.out.println("6. Logout");
                }
                default -> {
                    System.out.println("Invalid user type. Exiting...");
                    return;
                }
            }

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            try {
                switch (currentUser.getUserType()) {
                    case admin -> handleAdminOptions(choice);
                    case coder -> handleCoderOptions(choice);
                    case buyer -> handleBuyerOptions(choice);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private void handleAdminOptions(int choice) throws SQLException {
        switch (choice) {
            case 1 -> createUser();
            case 2 -> createCoder();
            case 3 -> postJob();
            case 4 -> readCoders();
            case 5 -> createBid();
            case 6 -> sendMessage();
            case 7 -> makePayment();
            case 8 -> updateUser();
            case 9 -> deleteUser();
            case 10 -> updateCoder();
            case 11 -> deleteCoder();
            case 12 -> changePaymentStatus();
            case 13 -> calculateMaximumBids();
            case 14 -> getBidsByUserId();
            case 15 -> updateBid();
            case 16 -> deleteBid();
            case 17 -> getUsersByType();
            case 18 -> getUsersByUsername();
            case 19 -> getAllUsers();
            case 20 -> updateUsers();
            case 21 -> deleteUsers();
            case 22 -> getUserById();
            case 23 -> getJobPostById();
            case 24 -> getAllJobPosts();
            case 25 -> updateJobPosts();
            case 26 -> deleteJobPosts();
            case 27 -> getMessagesBetweenUsers();
            case 28 -> createPayment();
            case 29 -> getPaymentById();
            case 30 -> updatePayment();
            case 31 -> getPaymentsByUserId();
            case 32 -> {
                System.out.println("Logging out...");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }


    private void handleCoderOptions(int choice) throws SQLException {
        switch (choice) {
            case 1:
                readCoders();
                break;
            case 2:
                System.out.println("Logging out...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void handleBuyerOptions(int choice) throws SQLException {
        switch (choice) {
            case 1 -> readCoders();
            case 2 -> createBid();
            case 3 -> makePayment();
            case 4 -> {
                System.out.println("Logging out...");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }
    private void createUser() throws SQLException {
        System.out.println("Create User");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Secret Key (Leave blank if Coder, 'phatnani' for Buyer, 'karan' for Admin): ");
        String secretKey = scanner.nextLine();

        User.UserType userType;

        if (secretKey.equalsIgnoreCase("phatnani")) {
            userType = User.UserType.buyer;
        } else if (secretKey.equalsIgnoreCase("karan")) {
            userType = User.UserType.admin;
        } else {
            userType = User.UserType.coder;
        }

        User user = new User(userIdCounter++, username, password, email, userType, secretKey);
        UserDAO userDAO = new UserDAO(connection);
        userDAO.createUser(user,connection);

        System.out.println("User created successfully!");
    }

    private void createCoder() throws SQLException {
        System.out.println("Create Coder");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Skills: ");
        String skills = scanner.nextLine();
        System.out.print("Experience Years: ");
        int experienceYears = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Available (true/false): ");
        boolean available = scanner.nextBoolean();

        Coder coder = new Coder(coderIdCounter++, username, skills, experienceYears, available);
        CoderDAO coderDAO = new CoderDAO(connection);
        coderDAO.createCoder(coder);

        System.out.println("Coder created successfully!");
    }

    private void postJob() throws SQLException {
        System.out.println("Post Job");
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Requirements: ");
        String requirements = scanner.nextLine();
        System.out.print("Budget: ");
        double budget = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Filled (true/false): ");
        boolean filled = scanner.nextBoolean();
        scanner.nextLine(); // Consume the newline character

        JobPost jobPost = new JobPost(jobIdCounter++, description, requirements, budget, filled);
        JobPostDAO jobPostDAO = new JobPostDAO(connection);
        jobPostDAO.createJobPost(jobPost);

        System.out.println("Job posted successfully!");
    }

    private void changeBidStatus() throws SQLException {
        System.out.print("Enter Bid ID to change status: ");
        int bidId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter new status (accepted/rejected): ");
        String newStatus = scanner.nextLine();

        BidDAO bidDAO = new BidDAO(connection);
        bidDAO.updateBidStatus(bidId, newStatus);

        System.out.println("Bid status updated successfully!");
    }


    private void sendMessage() throws SQLException {
        System.out.println("Send Message");
        System.out.print("Sender ID: ");
        int senderId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Receiver ID: ");
        int receiverId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Message Content: ");
        String content = scanner.nextLine();

        Date timestamp = new Date(); // Current timestamp

        Message message = new Message(messageIdCounter++, senderId, receiverId, content, timestamp);
        MessageDAO messageDAO = new MessageDAO(connection);
        messageDAO.createMessage(message);

        System.out.println("Message sent successfully!");
    }

    private void changePaymentStatus() throws SQLException {
        System.out.print("Enter Payment ID to change status: ");
        int paymentId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        PaymentDAO paymentDAO = new PaymentDAO(connection);
        Payment payment = paymentDAO.getPaymentById(paymentId);

        if (payment != null) {
            payment.setStatus(true);
            paymentDAO.updatePayment(payment);
            System.out.println("Payment status changed to TRUE.");
        } else {
            System.out.println("Payment not found.");
        }
    }

    private void updateUser() throws SQLException {
        System.out.println("Update User");
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        UserDAO userDAO = new UserDAO(connection);
        User user = userDAO.getUserById(userId);

        if (user != null) {
            System.out.print("New Username: ");
            String newUsername = scanner.nextLine();
            System.out.print("New Password: ");
            String newPassword = scanner.nextLine();
            System.out.print("New Email: ");
            String newEmail = scanner.nextLine();

            user.setUsername(newUsername);
            user.setPassword(newPassword);
            user.setEmail(newEmail);

            userDAO.updateUser(user);
            System.out.println("User updated successfully!");
        } else {
            System.out.println("User not found.");
        }
    }

    private void deleteUser() throws SQLException {
        System.out.print("Enter User ID to delete: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        UserDAO userDAO = new UserDAO(connection);
        User user = userDAO.getUserById(userId);

        if (user != null) {
            userDAO.deleteUser(userId);
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("User not found.");
        }
    }

    private void calculateMaximumBids() throws SQLException {
        System.out.print("Enter Coder ID: ");
        int coderId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        BidDAO bidDAO = new BidDAO(connection);
        double maxBid = bidDAO.calculateFinalBids(coderId);

        if (maxBid > 0.0) {
            System.out.println("Maximum bid is: " + maxBid);
        } else {
            System.out.println("No bids found for the specified coder ID.");
        }
    }

    private void updateCoder() throws SQLException {
        System.out.println("Update Coder");
        System.out.print("Enter Coder ID: ");
        int coderId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        CoderDAO coderDAO = new CoderDAO(connection);
        Coder coder = coderDAO.getCoderById(coderId);

        if (coder != null) {
            System.out.print("New Username: ");
            String newUsername = scanner.nextLine();
            System.out.print("New Skills: ");
            String newSkills = scanner.nextLine();
            System.out.print("New Experience Years: ");
            int newExperienceYears = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            System.out.print("New Availability (true/false): ");
            boolean newAvailability = scanner.nextBoolean();

            coder.setUsername(newUsername);
            coder.setSkills(newSkills);
            coder.setExperienceYears(newExperienceYears);
            coder.setAvailable(newAvailability);

            coderDAO.updateCoder(coder);
            System.out.println("Coder updated successfully!");
        } else {
            System.out.println("Coder not found.");
        }
    }

    private void deleteCoder() throws SQLException {
        System.out.print("Enter Coder ID to delete: ");
        int coderId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        CoderDAO coderDAO = new CoderDAO(connection);
        Coder coder = coderDAO.getCoderById(coderId);

        if (coder != null) {
            coderDAO.deleteCoder(coderId);
            System.out.println("Coder deleted successfully!");
        } else {
            System.out.println("Coder not found.");
        }
    }

    private void makePayment() throws SQLException {
        System.out.println("Make Payment");
        System.out.print("User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        Date timestamp = new Date(); // Current timestamp
        Payment payment = new Payment(paymentIdCounter++, userId, amount, timestamp, false);

        PaymentDAO paymentDAO = new PaymentDAO(connection);
        paymentDAO.createPayment(payment);

        System.out.println("Payment created successfully!");
    }
    private void readCoders() throws SQLException {
        CoderDAO coderDAO = new CoderDAO(connection);
        List<Coder> coders = coderDAO.getAllCoders();

        System.out.println("List of Coders:");
        for (Coder coder : coders) {
            System.out.println("ID: " + coder.getId());
            System.out.println("Username: " + coder.getUsername());
            System.out.println("Skills: " + coder.getSkills());
            System.out.println("Experience Years: " + coder.getExperienceYears());
            System.out.println("Available: " + coder.isAvailable());
            System.out.println();
        }
    }


    private void createBid() throws SQLException {
        System.out.println("Create Bid");
        System.out.print("User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Coder ID: ");
        int coderId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Bid Amount: ");
        double bidAmount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        Date bidDate = new Date();

        Bid bid = new Bid(bidIdCounter++, userId, coderId, bidAmount, bidDate, "NA"); // Use bidIdCounter
        BidDAO bidDAO = new BidDAO(connection);
        bidDAO.createBid(bid);

        System.out.println("Bid created successfully!");
    }


    private void getBidsByUserId() throws SQLException {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        BidDAO bidDAO = new BidDAO(connection);
        List<Bid> bids = bidDAO.getBidsByUserId(userId);

        System.out.println("Bids for User ID " + userId + ":");
        for (Bid bid : bids) {
            System.out.println("Coder ID: " + bid.getCoderId());
            System.out.println("Bid Amount: " + bid.getBidAmount());
            System.out.println("Status: " + bid.getStatus());
            System.out.println();
        }
    }

    private void updateBid() throws SQLException {
        System.out.print("Enter Bid ID to update: ");
        int bidId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        BidDAO bidDAO = new BidDAO(connection);
        Bid bid = bidDAO.getBidById(bidId);

        if (bid != null) {
            System.out.print("Enter new Bid Amount: ");
            double newBidAmount = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            bid.setBidAmount(newBidAmount);
            bidDAO.updateBid(bid);

            System.out.println("Bid updated successfully!");
        } else {
            System.out.println("Bid not found.");
        }
    }

    private void deleteBid() throws SQLException {
        System.out.print("Enter Bid ID to delete: ");
        int bidId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        BidDAO bidDAO = new BidDAO(connection);
        Bid bid = bidDAO.getBidById(bidId);

        if (bid != null) {
            bidDAO.deleteBid(bidId);
            System.out.println("Bid deleted successfully!");
        } else {
            System.out.println("Bid not found.");
        }
    }

    private void getUsersByType() throws SQLException {
        System.out.print("Enter User Type (buyer/coder/admin): ");
        String userType = scanner.nextLine();

        UserDAO userDAO = new UserDAO(connection);
        List<User> users = userDAO.getUsersByType(User.UserType.valueOf(userType));

        System.out.println("Users of type " + userType + ":");
        for (User user : users) {
            System.out.println("User ID: " + user.getUserId());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("User Type: " + user.getUserType());
            System.out.println();
        }
    }

    private void getUsersByUsername() throws SQLException {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        UserDAO userDAO = new UserDAO(connection);
        List<User> users = userDAO.getUsersByUsername(username);

        System.out.println("Users with username " + username + ":");
        for (User user : users) {
            System.out.println("User ID: " + user.getUserId());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("User Type: " + user.getUserType());
            System.out.println();
        }
    }

    private void getAllUsers() throws SQLException {
        UserDAO userDAO = new UserDAO(connection);
        List<User> users = userDAO.getAllUsers();

        System.out.println("List of Users:");
        for (User user : users) {
            System.out.println("User ID: " + user.getUserId());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("User Type: " + user.getUserType());
            System.out.println();
        }
    }

    private void updateUsers() throws SQLException {
        System.out.print("Enter User ID to update: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        UserDAO userDAO = new UserDAO(connection);
        User user = userDAO.getUserById(userId);

        if (user != null) {
            System.out.print("New Username: ");
            String newUsername = scanner.nextLine();
            System.out.print("New Password: ");
            String newPassword = scanner.nextLine();
            System.out.print("New Email: ");
            String newEmail = scanner.nextLine();

            user.setUsername(newUsername);
            user.setPassword(newPassword);
            user.setEmail(newEmail);

            userDAO.updateUser(user);
            System.out.println("User updated successfully!");
        } else {
            System.out.println("User not found.");
        }
    }

    private void deleteUsers() throws SQLException {
        System.out.print("Enter User ID to delete: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        UserDAO userDAO = new UserDAO(connection);
        User user = userDAO.getUserById(userId);

        if (user != null) {
            userDAO.deleteUser(userId);
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("User not found.");
        }
    }

    private void getUserById() throws SQLException {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        UserDAO userDAO = new UserDAO(connection);
        User user = userDAO.getUserById(userId);

        if (user != null) {
            System.out.println("User ID: " + user.getUserId());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("User Type: " + user.getUserType());
        } else {
            System.out.println("User not found.");
        }
    }

    private void getJobPostById() throws SQLException {
        System.out.print("Enter Job Post ID: ");
        int jobPostId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        JobPostDAO jobPostDAO = new JobPostDAO(connection);
        JobPost jobPost = jobPostDAO.getJobPostById(jobPostId);

        if (jobPost != null) {
            System.out.println("Job Post ID: " + jobPost.getJobId());
            System.out.println("Description: " + jobPost.getDescription());
            System.out.println("Requirements: " + jobPost.getRequirements());
            System.out.println("Budget: " + jobPost.getBudget());
            System.out.println("Filled: " + jobPost.isFilled());
        } else {
            System.out.println("Job Post not found.");
        }
    }

    private void getAllJobPosts() throws SQLException {
        JobPostDAO jobPostDAO = new JobPostDAO(connection);
        List<JobPost> jobPosts = jobPostDAO.getAllJobPosts();

        System.out.println("List of Job Posts:");
        for (JobPost jobPost : jobPosts) {
            System.out.println("Job Post ID: " + jobPost.getJobId());
            System.out.println("Description: " + jobPost.getDescription());
            System.out.println("Requirements: " + jobPost.getRequirements());
            System.out.println("Budget: " + jobPost.getBudget());
            System.out.println("Filled: " + jobPost.isFilled());
            System.out.println();
        }
    }

    private void updateJobPosts() throws SQLException {
        System.out.print("Enter Job Post ID to update: ");
        int jobPostId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        JobPostDAO jobPostDAO = new JobPostDAO(connection);
        JobPost jobPost = jobPostDAO.getJobPostById(jobPostId);

        if (jobPost != null) {
            System.out.print("New Description: ");
            String newDescription = scanner.nextLine();
            System.out.print("New Requirements: ");
            String newRequirements = scanner.nextLine();
            System.out.print("New Budget: ");
            double newBudget = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character
            System.out.print("New Filled (true/false): ");
            boolean newFilled = scanner.nextBoolean();

            jobPost.setDescription(newDescription);
            jobPost.setRequirements(newRequirements);
            jobPost.setBudget(newBudget);
            jobPost.setFilled(newFilled);

            jobPostDAO.updateJobPost(jobPost);
            System.out.println("Job Post updated successfully!");
        } else {
            System.out.println("Job Post not found.");
        }
    }

    private void deleteJobPosts() throws SQLException {
        System.out.print("Enter Job Post ID to delete: ");
        int jobPostId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        JobPostDAO jobPostDAO = new JobPostDAO(connection);
        JobPost jobPost = jobPostDAO.getJobPostById(jobPostId);

        if (jobPost != null) {
            jobPostDAO.deleteJobPost(jobPostId);
            System.out.println("Job Post deleted successfully!");
        } else {
            System.out.println("Job Post not found.");
        }
    }

    private void getMessagesBetweenUsers() throws SQLException {
        System.out.print("Enter Sender ID: ");
        int senderId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter Receiver ID: ");
        int receiverId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        MessageDAO messageDAO = new MessageDAO(connection);
        List<Message> messages = messageDAO.getMessagesBetweenUsers(senderId, receiverId);

        System.out.println("Messages between Sender ID " + senderId + " and Receiver ID " + receiverId + ":");
        for (Message message : messages) {
            System.out.println("Message ID: " + message.getMessageId());
            System.out.println("Sender ID: " + message.getSenderId());
            System.out.println("Receiver ID: " + message.getReceiverId());
            System.out.println("Content: " + message.getContent());
            System.out.println("Timestamp: " + message.getTimestamp());
            System.out.println();
        }
    }

    private void createPayment() throws SQLException {
        System.out.println("Create Payment");
        System.out.print("User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        Date timestamp = new Date(); // Current timestamp
        Payment payment = new Payment(paymentIdCounter++, userId, amount, timestamp, false);

        PaymentDAO paymentDAO = new PaymentDAO(connection);
        paymentDAO.createPayment(payment);

        System.out.println("Payment created successfully!");
    }

    private void getPaymentById() throws SQLException {
        System.out.print("Enter Payment ID: ");
        int paymentId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        PaymentDAO paymentDAO = new PaymentDAO(connection);
        Payment payment = paymentDAO.getPaymentById(paymentId);

        if (payment != null) {
            System.out.println("Payment ID: " + payment.getPaymentId());
            System.out.println("User ID: " + payment.getUserId());
            System.out.println("Amount: " + payment.getAmount());
            System.out.println("Timestamp: " + payment.getTimestamp());
            System.out.println("Status: " + (payment.isStatus() ? "Paid" : "Pending"));
        } else {
            System.out.println("Payment not found.");
        }
    }

    private void updatePayment() throws SQLException {
        System.out.print("Enter Payment ID to update: ");
        int paymentId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        PaymentDAO paymentDAO = new PaymentDAO(connection);
        Payment payment = paymentDAO.getPaymentById(paymentId);

        if (payment != null) {
            System.out.print("Enter new Payment Amount: ");
            double newAmount = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            payment.setAmount(newAmount);
            paymentDAO.updatePayment(payment);

            System.out.println("Payment updated successfully!");
        } else {
            System.out.println("Payment not found.");
        }
    }

    private void getPaymentsByUserId() throws SQLException {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        PaymentDAO paymentDAO = new PaymentDAO(connection);
        List<Payment> payments = paymentDAO.getPaymentsByUserId(userId);

        System.out.println("Payments for User ID " + userId + ":");
        for (Payment payment : payments) {
            System.out.println("Payment ID: " + payment.getPaymentId());
            System.out.println("Amount: " + payment.getAmount());
            System.out.println("Timestamp: " + payment.getTimestamp());
            System.out.println("Status: " + (payment.isStatus() ? "Paid" : "Pending"));
            System.out.println();
        }
    }

    public static void main(String[] args) {
        try {
            // Establish a database connection
            Connection connection = DatabaseConnectivity.getConnection();

//            // Drop tables if they exist (for demonstration purposes)
//            DatabaseInitializer.dropTables(connection);
//
//            // Create tables and insert initial data
//            DatabaseInitializer.createTables(connection);
//            DatabaseInitializer.insertInitialData(connection);

            // Start the marketplace
            Main marketplace = new Main(connection);
            marketplace.startMarketplace();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
