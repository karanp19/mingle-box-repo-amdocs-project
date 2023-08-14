import java.util.Date;

public class Bid {
    private int id;
    private int userId;
    private int coderId;
    private double bidAmount;
    private Date bidDate;
    String status;  // New field for bid status


    public Bid(int Id,int userId, int coderId, double bidAmount, Date bidDate, String status) {
        this.userId = userId;
        this.coderId = coderId;
        this.bidAmount = bidAmount;
        this.bidDate = new Date();
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCoderId() {
        return coderId;
    }

    public void setCoderId(int coderId) {
        this.coderId = coderId;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Date getBidDate() {
        return bidDate;
    }

    public void setBidDate(Date bidDate) {
        this.bidDate = bidDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", userId=" + userId +
                ", coderId=" + coderId +
                ", bidAmount=" + bidAmount +
                ", bidDate=" + bidDate +
                ", status=" + status +
                '}';
    }


}
