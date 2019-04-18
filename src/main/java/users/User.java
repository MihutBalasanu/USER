/**
 * Package users implements the functionality of login/logout for the valid users.
 */
package users;

import accounts.Account;
import java.util.List;
import java.util.Objects;

/**
 * Represents an user.
 * @author Mihut Balasanu
 */
public class User {
    private String username;
    private String password;
    private List<Account> userAccountList;

    /**
     * Creates an user.
     */
    public User() {

    }

    /**
     * Getter for user's account list.
     * @return A list containing user's account list
     */
    public List<Account> getUserAccountList() {
        return userAccountList;
    }

    /**
     * Sets user's account list
     * @param userAccountList User's account list to be set
     */
    public void setUserAccountList(List<Account> userAccountList) {
        this.userAccountList = userAccountList;
    }

    /**
     * Getter for username
     * @return A string containing username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets user's username
     * @param username Username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for password
     * @return A string containing password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets user's password
     * @param password Password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Represents any object as a string.
     * @return the string representation of the object
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
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
        if (!(o instanceof User)) return false;
        User user1 = (User) o;
        return Objects.equals(username, user1.getUsername()) &&
                Objects.equals(password, user1.getPassword());
    }

    /**
     * Sets a hash code value of the object that invokes the method.
     * @return an integer
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
