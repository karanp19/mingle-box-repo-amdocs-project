import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoderDAO {
    private Connection connection;

    public CoderDAO(Connection connection) {
        this.connection = connection;
    }

    public void createCoder(Coder coder) throws SQLException {
        String query = "INSERT INTO coders (username, skills, experience_years, available) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, coder.getUsername());
            statement.setString(2, coder.getSkills());
            statement.setInt(3, coder.getExperienceYears());
            statement.setBoolean(4, coder.isAvailable());
            statement.executeUpdate();
        }
    }

    public Coder getCoderByUsername(String username) throws SQLException {
        String query = "SELECT * FROM coders WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Coder coder = new Coder();
                    coder.setId(resultSet.getInt("id"));
                    coder.setUsername(resultSet.getString("username"));
                    coder.setSkills(resultSet.getString("skills"));
                    coder.setExperienceYears(resultSet.getInt("experience_years"));
                    coder.setAvailable(resultSet.getBoolean("available"));
                    return coder;
                }
            }
        }
        return null;
    }

    public List<Coder> getAllCoders() throws SQLException {
        List<Coder> coderList = new ArrayList<>();
        String query = "SELECT * FROM coders";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Coder coder = new Coder();
                coder.setId(resultSet.getInt("id"));
                coder.setUsername(resultSet.getString("username"));
                coder.setSkills(resultSet.getString("skills"));
                coder.setExperienceYears(resultSet.getInt("experience_years"));
                coder.setAvailable(resultSet.getBoolean("available"));
                coderList.add(coder);
            }
        }
        return coderList;
    }

    public void updateCoder(Coder coder) throws SQLException {
        String query = "UPDATE coders SET username = ?, skills = ?, experience_years = ?, available = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, coder.getUsername());
            statement.setString(2, coder.getSkills());
            statement.setInt(3, coder.getExperienceYears());
            statement.setBoolean(4, coder.isAvailable());
            statement.setInt(5, coder.getId());
            statement.executeUpdate();
        }
    }
    public Coder getCoderById(int coderId) throws SQLException {
        String query = "SELECT * FROM coders WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, coderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Coder coder = new Coder();
                    coder.setId(resultSet.getInt("id"));
                    coder.setUsername(resultSet.getString("username"));
                    coder.setSkills(resultSet.getString("skills"));
                    coder.setExperienceYears(resultSet.getInt("experience_years"));
                    coder.setAvailable(resultSet.getBoolean("available"));
                    return coder;
                }
            }
        }
        return null;
    }

    public void deleteCoder(int coderId) throws SQLException {
        String query = "DELETE FROM coders WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, coderId);
            statement.executeUpdate();
        }
    }
}
