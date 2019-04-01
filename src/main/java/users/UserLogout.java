package users;

import utils.MainMenu;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserLogout {


    private UserLogin userLogin = new UserLogin();
    private User user = new User();


    public void logout(Scanner scanner) {

        MainMenu mainMenu = new MainMenu();
        mainMenu.displayLogoutConsole();

        try {
            int option = scanner.nextInt();
            switch (option) {
                case 1:

                    user = userLogin.getValidatedUser().get();
                    System.out.println(user.getUsername() + " you are successfully logged out!");
                    userLogin.setLogged(false);
                    break;

                default:
                    System.out.println("Not a valid option!");
                    logout(scanner);
                    break;
            }
        } catch (InputMismatchException exception) {
            System.out.println("Try again!");
            mainMenu.displayLogoutConsole();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }
}
