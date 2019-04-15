package accounts;

import users.User;
import users.UserLogin;
import utils.Constants;
import utils.MainMenu;
import java.util.*;
import java.util.logging.Logger;

public class AccountMenu {

    private AccountWriter accountWriter = new AccountWriter();
    private UserLogin userLogin = new UserLogin();
    private AccountConsoleReader accountConsoleReader = new AccountConsoleReader();
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());

    public void accountOperations(Scanner scanner,User user) {

        MainMenu mainMenu = new MainMenu();
        mainMenu.displayAccountMenu();

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

    public void setUserAllAccounts(User user) {
        List<Account> userAllAccountsList = new ArrayList<>();
        AccountFileReader accountFileReader = new AccountFileReader();
        List<Account> accounts = accountFileReader.getAccounts();
        for (Account account : accounts) {
            if (user.getUsername().equals(account.getUsername())) {
                userAllAccountsList.add(account);
            }
            user.setUserAccountList(userAllAccountsList);
        }
    }

    private void displayUserAllAccounts(User user) {

        for(Account account: user.getUserAccountList()){
            System.out.println(accountWriter.displayAccountData(account));
        }
        if( user.getUserAccountList().isEmpty()){
            LOGGER.info(user.getUsername() + " has no account yet!");
        }
    }

    private void createUserAccount(Scanner scanner, User user){
        Account account = accountConsoleReader.readAccountData(scanner,user);
        user.getUserAccountList().add(account);
        AccountFileWriter.writeOnFile(Constants.ACCOUNT_FILE_PATH, accountWriter.displayAccountData(account));
    }
}


