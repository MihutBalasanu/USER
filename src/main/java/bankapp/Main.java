/**
 * Package bankapp contains the Main class with the main method.
 */
package bankapp;

import users.UserLogin;
import java.util.Scanner;

/**
 * This program implements an application that user can access and after logging he can create a bank account,
 * display his accounts and make transfers between his accounts (of the same currency).
 *
 * @author Mihut Balasanu
 * @version 1.0
 * @since 2019-03-21
 */
public class Main {

    /**
     * This is the main method which makes use of login method.
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UserLogin userLogin = new UserLogin();
        userLogin.login(scanner);
        userLogin.run(scanner);
        scanner.close();
    }
}
