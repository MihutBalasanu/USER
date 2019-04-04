package users;

import accounts.AccountMenu;
import utils.Constants;
import utils.MainMenu;
import java.util.*;
import java.util.logging.Logger;

public class UserLogin {

    private static Optional<User> validatedUser;
    private static boolean isLogged = false;
    private User user = new User();
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());
    private AccountMenu accountMenu = new AccountMenu();


    public void run(Scanner scanner) {
        if (!isLogged) {
            login(scanner);
        } else {
            UserLogout userLogout = new UserLogout();
            userLogout.logout(scanner);
        }
        run(scanner);
    }

    public Optional<User> getValidatedUser() {
        return validatedUser;
    }

    public void setValidatedUser(Optional<User> validatedUser) {
        this.validatedUser = validatedUser;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public void login(Scanner scanner) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.displayLoginConsole();

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
                    validatedUser = verifyLogin(user);
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

            if (validatedUser.isPresent()) {

                isLogged = true;
                LOGGER.info("Welcome user: " + user.getUsername() + "!");

            } else {
                LOGGER.warning("Wrong username / password!");
                login(scanner);
            }


        } catch (InputMismatchException exception) {
            LOGGER.warning("Try again!");
            LOGGER.warning("Invalid line: " + scanner.nextLine());
        }
    }

    public Optional<User> verifyLogin(User user) {
        for (User user1 : UserFileReader.getInstance().getUsers(Constants.USER_FILE_PATH)) {
                if (user1.equals(user)) {
                user.setUserAccountList(accountMenu.setUserLoginAllAccounts());
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}

