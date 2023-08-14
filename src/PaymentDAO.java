import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentDAO {
    private Connection connection;

    public PaymentDAO(Connection connection) {
        this.connection = connection;
    }

    public void createPayment(Payment payment) throws SQLException {
        String query = "INSERT INTO payments (user_id, amount, timestamp, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, payment.getUserId());
            statement.setDouble(2, payment.getAmount());
            statement.setTimestamp(3, new java.sql.Timestamp(payment.getTimestamp().getTime()));
            statement.setBoolean(4, payment.isStatus());
            statement.executeUpdate();
        }
    }

    public Payment getPaymentById(int paymentId) throws SQLException {
        String query = "SELECT * FROM payments WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, paymentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Payment payment = new Payment();
                    payment.setId(resultSet.getInt("id"));
                    payment.setUserId(resultSet.getInt("user_id"));
                    payment.setAmount(resultSet.getDouble("amount"));
                    payment.setTimestamp(resultSet.getTimestamp("timestamp"));
                    payment.setStatus(resultSet.getBoolean("status"));
                    return payment;
                }
            }
        }
        return null;
    }

    public void updatePayment(Payment payment) throws SQLException {
        String query = "UPDATE payments SET user_id = ?, amount = ?, timestamp = ?, status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, payment.getUserId());
            statement.setDouble(2, payment.getAmount());
            statement.setTimestamp(3, new java.sql.Timestamp(payment.getTimestamp().getTime()));
            statement.setBoolean(4, payment.isStatus());
            statement.setInt(5, payment.getId());
            statement.executeUpdate();
        }
    }
    public List<Payment> getPaymentsByUserId(int userId) throws SQLException {
        List<Payment> paymentList = new ArrayList<>();
        String query = "SELECT * FROM payments WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Payment payment = new Payment();
                    payment.setId(resultSet.getInt("id"));
                    payment.setUserId(resultSet.getInt("user_id"));
                    payment.setAmount(resultSet.getDouble("amount"));
                    payment.setTimestamp(resultSet.getTimestamp("timestamp"));
                    payment.setStatus(resultSet.getBoolean("status"));
                    paymentList.add(payment);
                }
            }
        }
        return paymentList;
    }
}
