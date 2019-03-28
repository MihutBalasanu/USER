package users;

import utils.Constants;
import utils.FileReader;
import utils.MainMenu;

import java.util.*;

public class UserLogin {

    private static UserLogin userLogin = null;
    private Optional<User> validatedUser;
    boolean isLogged = false;
    User user = new User();


    private UserLogin(){

    }

    public static UserLogin getInstance(){
        if(userLogin == null){
            userLogin = new UserLogin();
        }
        return userLogin;
    }

    public void run() {
        if (!isLogged) {
            login();
        } else {
            UserLogout userLogout = new UserLogout();
            userLogout.logout();
        }
        run();
    }

    public Optional<User> getValidatedUser() {
        return validatedUser;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public void login() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.displayLoginConsole();
        Scanner scanner = new Scanner(System.in);
        try {
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    UserConsoleReader userConsoleReader = new UserConsoleReader();
                    user = userConsoleReader.readUserData();
                    validatedUser = verifyLogin(user);
                    break;

                case 2:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Not a valid option");
                    login();
                    break;
            }

            if(validatedUser.isPresent()){
                userLogin.validatedUser = validatedUser;
                isLogged = true;
                System.out.println("Welcome user: " + user.getUsername() + "!");
                validatedUser = null;
            } else {
                System.out.println("Wrong username / password!");
                login();
            }


        } catch (InputMismatchException exception) {
            System.out.println("Try again!");
            mainMenu.displayLoginConsole();
        }
        scanner.close();
    }

    public Optional<User> verifyLogin(User user) {
        for (User i : FileReader.readFromFile(Constants.FILE_PATH)) {
            if (i.equals(user)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

}

