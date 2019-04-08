package bankapp;

import users.UserLogin;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UserLogin userLogin = new UserLogin();
        userLogin.login(scanner);
        userLogin.run(scanner);
        scanner.close();
    }
}
