package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "account_number", length = 24, nullable = false)
    private String accontNumber;

    @Column(name = "balance", length = 16, nullable = false)
    private BigDecimal balance;

    @Column(name = "account_type", length = 50, nullable = false)
    private String accountType;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    @OneToMany(mappedBy ="transaction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public Account(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getAccontNumber() {
        return accontNumber;
    }


    public void setAccontNumber(String accontNumber) {
        this.accontNumber = accontNumber;
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    public String getAccountType() {
        return accountType;
    }


    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(getId(), account.getId()) &&
                Objects.equals(getUser(), account.getUser()) &&
                Objects.equals(getAccontNumber(), account.getAccontNumber()) &&
                Objects.equals(getBalance(), account.getBalance()) &&
                Objects.equals(getAccountType(), account.getAccountType()) &&
                Objects.equals(getCreatedTime(), account.getCreatedTime()) &&
                Objects.equals(getUpdatedTime(), account.getUpdatedTime()) &&
                Objects.equals(getTransactions(), account.getTransactions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getAccontNumber(), getBalance(), getAccountType(), getCreatedTime(), getUpdatedTime(), getTransactions());
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", user=" + user +
                ", accontNumber='" + accontNumber + '\'' +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", transactions=" + transactions +
                '}';
    }
}
