package users;

import accounts.AccountMenu;
import utils.MainMenu;
import utils.WrongLoginException;

import java.util.*;
import java.util.logging.Logger;

public class UserLogin {

    private static Optional<User> validatedUser;
    private static boolean isLogged = false;
    private User user = new User();
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());


    public void run(Scanner scanner) {
        if (!isLogged) {
            login(scanner);
        } else {
            UserLogout userLogout = new UserLogout();
            validatedUser.ifPresent(user1 -> userLogout.logout(scanner, user1));
            setValidatedUser(Optional.empty());
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
                    AccountMenu accountMenu = new AccountMenu();
                    validatedUser.ifPresent(accountMenu::setUserAllAccounts);
                    break;

                case 2:
                    LOGGER.info("You are succesfully exit!");
                    UserLogout.setEnoughAccountsForTransfer(false);
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
                throw new WrongLoginException("Wrong username / password!");
            }

        } catch (InputMismatchException exception) {
            LOGGER.warning("Try again!");
            LOGGER.warning("Invalid line: " + scanner.nextLine());
        } catch (WrongLoginException e) {
            e.printStackTrace();
            login(scanner);
        }
    }

    public Optional<User> verifyLogin(User user){

        UserFileReader userFileReader = UserFileReader.getInstance();
        for (User user1 : userFileReader.getUsers()) {
                if (user1.equals(user)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}

