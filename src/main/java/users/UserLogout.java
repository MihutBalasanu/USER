package users;

import accounts.Account;
import accounts.AccountMenu;
import utils.Currency;
import utils.MainMenu;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class UserLogout {


    private UserLogin userLogin = new UserLogin();
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());
    private static boolean enoughAccountsForTransfer = false;

    public boolean isEnoughAccountsForTransfer() {
        return enoughAccountsForTransfer;
    }

    public void logout(Scanner scanner, User user) {

        MainMenu mainMenu = new MainMenu();
        mainMenu.displayLogoutConsole();

        try {
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    AccountMenu accountMenu = new AccountMenu();
                    List<Account> accountUserList = accountMenu.setUserAllAccounts(user);
                    List<Account> accountListWithSameCurrency = new ArrayList<>();
                    for (Currency currency : Currency.values()) {
                        for (Account account : accountUserList) {
                            if (account.getAccountType().equals(String.valueOf(currency))) {
                                accountListWithSameCurrency.add(account);
                            }
                        }
                        if (accountListWithSameCurrency.size() >= 2) {
                            enoughAccountsForTransfer = true;
                            break;
                        }
                    }
                    if(enoughAccountsForTransfer) {
                            mainMenu.displayAccountMenuWithTransfer();
                    }else{
                            mainMenu.displayAccountMenuWithoutTransfer();
                    }

                    accountMenu.accountOperations(scanner,user);
                    logout(scanner,user);
                    break;

                case 2:
                    LOGGER.info(user.getUsername() + " you are successfully logged out!");
                    userLogin.setLogged(false);
                    break;

                default:
                    LOGGER.warning("Not a valid option!");
                    logout(scanner,user);
                    break;
            }
        } catch (InputMismatchException exception) {
              LOGGER.warning("Try again!");
              LOGGER.warning("Invalid input: " + scanner.nextLine());
              logout(scanner,user);
        }
    }
}
