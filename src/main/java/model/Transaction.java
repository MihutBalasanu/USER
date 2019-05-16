package model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction extends BaseModel{

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", length = 16, nullable = false)
    private BigDecimal amount;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "details", length = 100)
    private String details;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "type", length = 10)
    private String type;

    public Transaction() {
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getCreatedTime(), that.getCreatedTime()) &&
                Objects.equals(getDetails(), that.getDetails()) &&
                Objects.equals(getAccount(), that.getAccount()) &&
                Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAmount(), getCreatedTime(), getDetails(), getAccount(), getType());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", createdTime=" + createdTime +
                ", details='" + details + '\'' +
                ", account=" + account +
                ", type='" + type + '\'' +
                '}';
    }
}
