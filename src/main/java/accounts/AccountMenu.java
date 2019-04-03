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
    private MainMenu mainMenu = new MainMenu();
    private AccountConsoleReader accountConsoleReader = new AccountConsoleReader();
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());

    public void accountOperations(Scanner scanner) {

        mainMenu.displayAccountMenu();
        try{
        int option;
        System.out.println("Select your option: ");
        option = scanner.nextInt();
            switch (option) {
                case 1:
                    createUserAccount(scanner);
                    break;
                case 2:
                    displayUserAllAccounts(userLogin.getValidatedUser().get());
                    break;
                case 3:
                    userLogin.run(scanner);
                    break;
                default:
//                    System.out.println("Not a valid option!");
                    LOGGER.warning("Not a valid option!");
                    accountOperations(scanner);
                    break;
            }
        } catch (InputMismatchException exception) {
//            System.out.println("Invalid input: " + scanner.nextLine());
              LOGGER.warning("Invalid input: " + scanner.nextLine());

        } catch (NoSuchElementException e) {
//            e.printStackTrace();
            LOGGER.warning(e.getMessage());
        }
    }

    private void displayUserAllAccounts(User user) {

        Set<Account> userAccountSet = new HashSet<>();
        for (Account account : AccountFileReader.readFromFile(Constants.ACCOUNT_FILE_PATH)) {
            if (user.getUsername().equals(account.getUsername())) {
                userAccountSet.add(account);
                System.out.println(accountWriter.displayAccountData(account));
            }
        }
        if(userAccountSet.isEmpty()){
//            System.out.println(user.getUsername() + " has no account yet!");
            LOGGER.info(user.getUsername() + " has no account yet!");
        }
    }

    private Account createUserAccount(Scanner scanner){

        Account account = accountConsoleReader.readAccountData(scanner);
        AccountFileWriter.writeOnFile(Constants.ACCOUNT_FILE_PATH, accountWriter.displayAccountData(account));
        return account;
    }
}


