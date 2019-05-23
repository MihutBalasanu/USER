package dto;

import model.User;

public class AccountDTO {


    private User user;
    private String accountNumber;
    private String accountType;

    public AccountDTO(User user, String accountNumber, String accountType) {
        super();
        this.user = user;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
