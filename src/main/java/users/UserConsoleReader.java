package users;

import java.util.Scanner;

public class UserConsoleReader {

    public User readUserData(){
        User user = new User();
        Scanner scanner = new Scanner (System.in);
        System.out.println("Add login details: ");
        System.out.println("Username: ");
        user.setUsername(scanner.nextLine());
        System.out.println("Password: ");
        user.setPassword(scanner.nextLine());
        scanner.close();
        return user;
    }
}