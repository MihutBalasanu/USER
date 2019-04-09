package accounts;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {

    private String accontNumber;
    private String username;
    private BigDecimal balance;
    private String accountType;

    public Account(){

    }

    public Account(String accountType){
        this.accountType = accountType;
    }

    public Account(String accontNumber, String username, BigDecimal balance, String accountType) {
        this.accontNumber = accontNumber;
        this.username = username;
        this.balance = balance;
        this.accountType = accountType;
    }

    public String getAccontNumber() {
        return accontNumber;
    }

    public void setAccontNumber(String accontNumber) {
        this.accontNumber = accontNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(accontNumber, account.getAccontNumber()) &&
                Objects.equals(username, account.getUsername()) &&
                Objects.equals(balance, account.getBalance()) &&
                Objects.equals(accountType, account.getAccountType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(accontNumber, username, balance, accountType);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accontNumber='" + accontNumber + '\'' +
                ", username='" + username + '\'' +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
