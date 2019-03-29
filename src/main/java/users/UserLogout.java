package users;

import utils.MainMenu;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

public class UserLogout {


    UserLogin userLogin = UserLogin.getInstance();
    User user = new User();


    public void logout() {

        MainMenu mainMenu = new MainMenu();
        mainMenu.displayLogoutConsole();

        Scanner scanner = new Scanner(System.in);
        try {
            int option = scanner.nextInt();
            switch (option) {
                case 1:

                        user = userLogin.getValidatedUser().orElseThrow(IllegalArgumentException::new);

                        System.out.println(user.getUsername() + " you are successfully logged out!");
                        userLogin.setLogged(false);
                        break;

                        default:
                            System.out.println("Not a valid option!");
                            logout();
                            break;
            }
            scanner.close();
        } catch (InputMismatchException exception) {
            System.out.println("Try again!");
            mainMenu.displayLogoutConsole();
        }
        catch (NoSuchElementException e){
            e.printStackTrace();
        }
    }
}
