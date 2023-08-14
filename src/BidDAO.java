import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BidDAO {
    private Connection connection;

    public BidDAO(Connection connection) {
        this.connection = connection;
    }

    public void createBid(Bid bid) throws SQLException {
        String query = "INSERT INTO bids (user_id, coder_id, bid_amount, bid_date, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bid.getUserId());
            statement.setInt(2, bid.getCoderId());
            statement.setDouble(3, bid.getBidAmount());
            statement.setTimestamp(4, new Timestamp(bid.getBidDate().getTime())); // Convert Date to Timestamp
            statement.setString(5,bid.status);
            statement.executeUpdate();
        }
    }

    public double calculateFinalBids(int coderId) throws SQLException {
        String query = "SELECT MAX(bid_amount) FROM bids WHERE coder_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, coderId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble(1);
                }
            }
        }

        return 0.0; // Return 0.0 if no bids found
    }
    public List<Bid> getBidsByUserId(int userId) throws SQLException {
        List<Bid> bids = new ArrayList<>();
        String query = "SELECT * FROM bids WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Bid bid = new Bid(
                            resultSet.getInt("id"),

                            resultSet.getInt("user_id"),
                            resultSet.getInt("coder_id"),
                            resultSet.getDouble("bid_amount"),
                            resultSet.getDate("bid_date"),
                            resultSet.getString("status")
                    );
                    bid.setBidDate(resultSet.getTimestamp("bid_date"));
                    bids.add(bid);
                }
            }
        }
        return bids;
    }


    public void updateBid(Bid bid) throws SQLException {
        String query = "UPDATE bids SET bid_amount = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, bid.getBidAmount());
            statement.setInt(2, bid.getId());
            statement.setString(3, bid.getStatus());
            statement.executeUpdate();
        }
    }

    public void deleteBid(int bidId) throws SQLException {
        String query = "DELETE FROM bids WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bidId);
            statement.executeUpdate();
        }
    }

    // Other methods for additional functionality can be added here
    public void updateBidStatus(int bidId, String newStatus) {
        String updateStatusSQL = "UPDATE bid SET status = ? WHERE bid_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(updateStatusSQL)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, bidId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Bid getBidById(int bidId) throws SQLException {
        String query = "SELECT * FROM bids WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bidId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int userId = resultSet.getInt("user_id");
                    int coderId = resultSet.getInt("coder_id");
                    double bidAmount = resultSet.getDouble("bid_amount");
                    String status = resultSet.getString("status");
                    Date bidDate = resultSet.getDate("bid_date");

                    return new Bid(id, userId, coderId, bidAmount, bidDate, status);
                } else {
                    return null; // Bid with given ID not found
                }
            }
        }
    }
}
