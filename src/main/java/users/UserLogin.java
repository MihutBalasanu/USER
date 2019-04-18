package users;

import accounts.AccountMenu;
import utils.MainMenu;
import utils.WrongLoginException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Contain the logic for login functionality
 * @author Mihut Balasanu
 */
public class UserLogin {

    private static Optional<User> validatedUser;
    /**
     * Keeps the user's state of login/logout
     */
    private static boolean isLogged = false;
    private User user = new User();
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());

    /**
     * Enables user to login or logout according to the "isLogged" field
     * @param scanner Instance of Scanner
     */
    public void run(Scanner scanner) {
        if (!isLogged) {
            login(scanner);
        } else {
            UserLogout userLogout = new UserLogout();
            // Invokes the logout method
            validatedUser.ifPresent(user1 -> userLogout.logout(scanner, user1));
            // Sets the Optional empty
            setValidatedUser(Optional.empty());
        }
        run(scanner);
    }

    /**
     * Sets the Optional of validated user
     * @param validatedUser validatedUser to be set
     */
    public void setValidatedUser(Optional<User> validatedUser) {
        this.validatedUser = validatedUser;
    }

    /**
     * Sets the field "isLogged"
     * @param logged true or false
     */
    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    /**
     * Login of a user if valid
     * @param scanner Instance of Scanner
     */
    public void login(Scanner scanner) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.displayLoginConsole();

         // Ensures a proper username and password as inputs of the user
        try {
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Add login details: ");
                    System.out.println("Username: ");
                    String username = scanner.next();
                    System.out.println("Password: ");
                    String password = scanner.next();
                    user.setUsername(username);
                    user.setPassword(password);
                    // Assigns to the reference an Optional of  user if valid or Optional empty
                    // if user is not valid
                    validatedUser = verifyLogin(user);
                    AccountMenu accountMenu = new AccountMenu();
                    // Assigns a list of accounts to the logged user
                    validatedUser.ifPresent(accountMenu::setUserAllAccounts);
                    break;

                case 2:
                    LOGGER.info("You are succesfully exit!");
                    System.exit(0);
                    break;

                default:
                    LOGGER.warning("Not a valid option!");
                    login(scanner);
                    break;
            }
            // Assign true to isLogged if there is a user logged
            if (validatedUser.isPresent()) {
                isLogged = true;
                LOGGER.info("Welcome user: " + user.getUsername() + "!");

            // Notifies the user about the wrong login
            } else {
                throw new WrongLoginException("Wrong username / password!");
            }

        } catch (InputMismatchException exception) {
            LOGGER.warning("Try again!");
            LOGGER.warning("Invalid line: " + scanner.nextLine());
        } catch (WrongLoginException e) {
            e.printStackTrace();
            // Ensures that user can try another login
            login(scanner);
        }
    }

    /**
     * Verifies if user's input is valid
     * @param user Instance of User to be checked
     * @return User if is valid or an empty Optional if user's input is not valid
     */
    public Optional<User> verifyLogin(User user){
        UserFileReader userFileReader = new UserFileReader();
        // Check the list of users for "user"
        for (User user1 : userFileReader.getUsers()) {
                if (user1.equals(user)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}

