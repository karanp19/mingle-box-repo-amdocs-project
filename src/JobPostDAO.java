import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobPostDAO {
    private Connection connection;

    public JobPostDAO(Connection connection) {
        this.connection = connection;
    }

    public void createJobPost(JobPost jobPost) throws SQLException {
        String query = "INSERT INTO job_posts (description, requirements, budget, filled) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, jobPost.getDescription());
            statement.setString(2, jobPost.getRequirements());
            statement.setDouble(3, jobPost.getBudget());
            statement.setBoolean(4, jobPost.isFilled());
            statement.executeUpdate();
        }
    }

    public JobPost getJobPostById(int id) throws SQLException {
        String query = "SELECT * FROM job_posts WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    JobPost jobPost = new JobPost();
                    jobPost.setId(resultSet.getInt("id"));
                    jobPost.setDescription(resultSet.getString("description"));
                    jobPost.setRequirements(resultSet.getString("requirements"));
                    jobPost.setBudget(resultSet.getDouble("budget"));
                    jobPost.setFilled(resultSet.getBoolean("filled"));
                    return jobPost;
                }
            }
        }
        return null;
    }

    public List<JobPost> getAllJobPosts() throws SQLException {
        List<JobPost> jobPostList = new ArrayList<>();
        String query = "SELECT * FROM job_posts";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                JobPost jobPost = new JobPost();
                jobPost.setId(resultSet.getInt("id"));
                jobPost.setDescription(resultSet.getString("description"));
                jobPost.setRequirements(resultSet.getString("requirements"));
                jobPost.setBudget(resultSet.getDouble("budget"));
                jobPost.setFilled(resultSet.getBoolean("filled"));
                jobPostList.add(jobPost);
            }
        }
        return jobPostList;
    }

    public void updateJobPost(JobPost jobPost) throws SQLException {
        String query = "UPDATE job_posts SET description = ?, requirements = ?, budget = ?, filled = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, jobPost.getDescription());
            statement.setString(2, jobPost.getRequirements());
            statement.setDouble(3, jobPost.getBudget());
            statement.setBoolean(4, jobPost.isFilled());
            statement.setInt(5, jobPost.getId());
            statement.executeUpdate();
        }
    }

    public void deleteJobPost(int id) throws SQLException {
        String query = "DELETE FROM job_posts WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
