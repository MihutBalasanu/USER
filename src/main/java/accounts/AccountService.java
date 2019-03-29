package accounts;

import users.User;
import utils.Constants;
import java.util.ArrayList;
import java.util.List;

public class AccountService {

    AccountConsoleReader accountConsoleReader = new AccountConsoleReader();
    private static AccountService accountService = new AccountService();

    private AccountService(){

    }

    public static AccountService getInstance(){
        return accountService;
    }

    public List<Account> getAllUserAcounts(User user) {
        List<Account> userAccountList = new ArrayList<>();
        for (Account account : AccountFileReader.readFromFile(Constants.ACCOUNT_FILE_PATH)) {
            if (user.getUsername().equals(account.getUsername())) {
                userAccountList.add(account);
            }
        }
        return userAccountList;
    }

    public Account createAccount(){
        Account account = accountConsoleReader.readAccountData();
        return account;

    }
}
