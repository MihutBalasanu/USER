package model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "accounts")
public class Account extends BaseModel{

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "account_number", length = 24, nullable = false)
    private String accountNumber;

    @Column(name = "balance", length = 16, nullable = false)
    private BigDecimal balance;

    @Column(name = "account_type", length = 50, nullable = false)
    private String accountType;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    @OneToMany(mappedBy ="account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    private String currency;

    public Account(){
    }

    public Account(String accountNumber, BigDecimal balance, String currency) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
    }

    public Account(User user, String accountNumber, String accountType) {
        this.user = user;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getId() {
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

    public String getAccountNumber() {
        return accountNumber;
    }


    public void setAccountNumber(String accontNumber) {
        this.accountNumber = accontNumber;
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
                Objects.equals(getAccountNumber(), account.getAccountNumber()) &&
                Objects.equals(getBalance(), account.getBalance()) &&
                Objects.equals(getAccountType(), account.getAccountType()) &&
                Objects.equals(getCreatedTime(), account.getCreatedTime()) &&
                Objects.equals(getUpdatedTime(), account.getUpdatedTime()) &&
                Objects.equals(getTransactions(), account.getTransactions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getAccountNumber(), getBalance(), getAccountType(), getCreatedTime(), getUpdatedTime(), getTransactions());
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", user=" + user +
                ", accontNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", transactions=" + transactions +
                '}';
    }
}
