import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageDAO {
    private Connection connection;

    public MessageDAO(Connection connection) {
        this.connection = connection;
    }

    public void createMessage(Message message) throws SQLException {
        String query = "INSERT INTO messages (sender_id, receiver_id, content, timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, message.getSenderId());
            statement.setInt(2, message.getReceiverId());
            statement.setString(3, message.getContent());
            statement.setTimestamp(4, new java.sql.Timestamp(message.getTimestamp().getTime()));
            statement.executeUpdate();
        }
    }

    public List<Message> getMessagesBetweenUsers(int user1Id, int user2Id) throws SQLException {
        List<Message> messageList = new ArrayList<>();
        String query = "SELECT * FROM messages WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) ORDER BY timestamp ASC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user1Id);
            statement.setInt(2, user2Id);
            statement.setInt(3, user2Id);
            statement.setInt(4, user1Id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Message message = new Message();
                    message.setId(resultSet.getInt("id"));
                    message.setSenderId(resultSet.getInt("sender_id"));
                    message.setReceiverId(resultSet.getInt("receiver_id"));
                    message.setContent(resultSet.getString("content"));
                    message.setTimestamp(resultSet.getTimestamp("timestamp"));
                    messageList.add(message);
                }
            }
        }
        return messageList;
    }
}
