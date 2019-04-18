package accounts;

import users.User;
import users.UserLogin;
import utils.Constants;
import utils.MainMenu;
import java.util.*;
import java.util.logging.Logger;

/**
 * Actions to be made by the user according to his options from the account menu.
 * @author Mihut Balasanu
 */
public class AccountMenu {

    private AccountWriter accountWriter = new AccountWriter();
    private UserLogin userLogin = new UserLogin();
    private AccountConsoleReader accountConsoleReader = new AccountConsoleReader();
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());

    /**
     * Actions to be made by the user according to his options from the account menu.
     * @param scanner Instance of Scanner
     * @param user Instance of User
     */
    public void accountOperations(Scanner scanner,User user) {

        MainMenu mainMenu = new MainMenu();
        mainMenu.displayAccountMenu();
        // Ensures a proper selection from the options
        try{
            int option;
            System.out.println("Select your option: ");
            option = scanner.nextInt();
                switch (option) {
                    case 1:
                        createUserAccount(scanner,user);
                        accountOperations(scanner, user);
                        break;
                    case 2:
                        displayUserAllAccounts(user);
                        accountOperations(scanner, user);
                        break;
                    case 3:
                        userLogin.run(scanner);
                        break;

                    default:
                        LOGGER.warning("Not a valid option!");
                        accountOperations(scanner,user);
                        break;
                }
        } catch (InputMismatchException exception) {
            LOGGER.warning("Invalid input: " + scanner.nextLine());

        } catch (NoSuchElementException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    /**
     * Sets all accounts of a user
     * @param user instance of User
     */
    public void setUserAllAccounts(User user) {
        List<Account> userAllAccountsList = new ArrayList<>();
        // Gets all the accounts from all users
        AccountFileReader accountFileReader = new AccountFileReader();
        List<Account> accounts = accountFileReader.getAccounts();
        // Extracts only the accounts of the user and assign them to the user
        for (Account account : accounts) {
            if (user.getUsername().equals(account.getUsername())) {
                userAllAccountsList.add(account);
            }
            user.setUserAccountList(userAllAccountsList);
        }
    }

    /**
     * Displays all accounts of the user.
     * @param user Instance of User
     */
    private void displayUserAllAccounts(User user) {

        for(Account account: user.getUserAccountList()){
            System.out.println(accountWriter.displayAccountData(account));
        }
        if( user.getUserAccountList().isEmpty()){
            LOGGER.info(user.getUsername() + " has no account yet!");
        }
    }

    /**
     * Creates an account to a user
     * @param scanner Instance of Scanner
     * @param user Instance of User
     */
    private void createUserAccount(Scanner scanner, User user){
        // Creates an account based on the user inputs
        Account account = accountConsoleReader.readAccountData(scanner,user);
        // Adds the new account to the user's account list
        user.getUserAccountList().add(account);
        // Writes the new account's fields in the "account details.txt" file
        AccountFileWriter.writeOnFile(Constants.ACCOUNT_FILE_PATH, accountWriter.displayAccountData(account));
    }
}


