/**
 * Package accounts implements the functionality of account operations.
 * The user can create account, display his accounts and make transfers.
 */
package accounts;

import java.math.BigDecimal;
import java.util.Objects;


/**
 * Represents an account.
 * @author Mihut Balasanu
 */
public class Account {

    private String accontNumber;
    private String username;
    private BigDecimal balance;
    private String accountType;

    /**
     * Creates an account.
     */
    public Account(){
    }

    /**
     * Getter for account number.
     * @return a String containing the account number
     */
    public String getAccontNumber() {
        return accontNumber;
    }

    /**
     * Sets the account number.
     * @param accontNumber to be set
     */
    public void setAccontNumber(String accontNumber) {
        this.accontNumber = accontNumber;
    }

    /**
     * Getter for username
     * @return a String containing the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * @param username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for balance
     * @return a BigDecimal containing the amount existing in the account
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets the balance.
     * @param balance to be set
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Getter for account type
     * @return a String containing the currency of the account
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * Sets the account type.
     * @param accountType to be set
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     *Determines whether the object that invokes the method is equal to the object that is passed
     * as an argument.
     * @param o  Any object
     * @return The method returns True if the argument is not null and is an object of the same
     * type and with the same numeric value
     */
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

    /**
     * Sets a hash code value of the object that invokes the method.
     * @return an integer
     */
    @Override
    public int hashCode() {
        return Objects.hash(accontNumber, username, balance, accountType);
    }

    /**
     * Represents any object as a string.
     * @return the string representation of the object
     */
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
