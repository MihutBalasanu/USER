package view;

import bankapp.Login;
import model.User;
import utils.SessionUtil;
import java.util.Scanner;
import java.util.logging.Logger;

public class LoginMenu extends AbstractMenu{

    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());

    @Override
    protected void displayOptions() {
        System.out.println("\nLOGIN MENU");
        System.out.println("1 - Login");
        System.out.println("0 - Exit");
    }

    @Override
    protected void executeOption(Integer option) {
        switch (option) {
            case 1:
                Scanner scanner = new Scanner(System.in);
                System.out.println();
                System.out.print("username: ");
                String userID = scanner.nextLine();
                System.out.print("password: ");
                String password = scanner.nextLine();
                Login login = new Login();
                User user = login.login(userID, password);
                if (user != null) {
                    SessionUtil.user = user;
                    LOGGER.info("Welcome " + user.getId());
                    AccountsMenu accountsMenu = new AccountsMenu();
                    accountsMenu.displayMenu();
                } else {
                    LOGGER.warning("Invalid username/password");
                }
                break;
            case 0:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid option!");
                break;
        }

    }
}
