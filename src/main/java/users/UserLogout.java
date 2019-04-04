package users;

import accounts.AccountMenu;
import utils.MainMenu;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;



public class UserLogout {


    private UserLogin userLogin = new UserLogin();
    private User user = new User();
    private AccountMenu accountMenu = new AccountMenu();
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());


    public void logout(Scanner scanner) {

        MainMenu mainMenu = new MainMenu();
        mainMenu.displayLogoutConsole();

        try {
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    accountMenu.accountOperations(scanner);
                    break;

                case 2:
                    user = userLogin.getValidatedUser().get();
//                    System.out.println(user.getUsername() + " you are successfully logged out!");
                    LOGGER.info(user.getUsername() + " you are successfully logged out!");
                    userLogin.setLogged(false);
                    userLogin.setValidatedUser(Optional.empty());
                    break;

                default:
//                    System.out.println("Not a valid option!");
                    LOGGER.warning("Not a valid option!");
                    logout(scanner);
                    break;
            }
        } catch (InputMismatchException exception) {
//            System.out.println("Try again!");
//            System.out.println("Invalid input: " + scanner.nextLine());
              LOGGER.warning("Try again!");
              LOGGER.warning("Invalid input: " + scanner.nextLine());
        }
    }
}
