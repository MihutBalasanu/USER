package users;

import accounts.Account;
import java.util.List;
import java.util.Objects;

public class User {
    private String username;
    private String password;
    private List<Account> userAccountList;

    public User() {

    }

    public List<Account> getUserAccountList() {
        return userAccountList;
    }

    public void setUserAccountList(List<Account> userAccountList) {
        this.userAccountList = userAccountList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user1 = (User) o;
        return Objects.equals(username, user1.getUsername()) &&
                Objects.equals(password, user1.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
