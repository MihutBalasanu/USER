package accounts;

import users.User;
import users.UserLogin;
import utils.Constants;

import java.util.List;
import java.util.Scanner;

public class AccountMenu {

    private AccountService accountService = AccountService.getInstance();
    private AccountWriter accountWriter = new AccountWriter();
    private UserLogin userLogin = UserLogin.getInstance();
    private User user = userLogin.getValidatedUser().orElseThrow(IllegalArgumentException::new);


    public void displayAccountMenu() {

        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            displayUserOptions();
            System.out.print("Choose option: ");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    displayUserAllAccounts();
                    break;
                case 2:
                    createUserAccount();
                    break;
                case 0:
                    break;
            }
        } while (option != 0);

    }

    private void displayUserOptions() {
        System.out.println("1. Display all user accounts");
        System.out.println("2. Create account");
        System.out.println("0. Exit");
        System.out.println("Select your option");
    }

    private void displayUserAllAccounts() {

        List<Account> allUserAccounts = accountService.getAllUserAcounts(user);
        for (Account account : allUserAccounts) {
            accountWriter.displayAccountData(account);
        }
    }

    private void createUserAccount(){

        Account account = accountService.createAccount();
        AccountFileWriter.writeOnFile(Constants.ACCOUNT_FILE_PATH, accountWriter.displayAccountData(account));
    }
}


