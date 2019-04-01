package users;

import utils.Constants;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        UserFileReader.readFromFile(Constants.USER_FILE_PATH);
        Scanner scanner = new Scanner(System.in);
        UserLogin userLogin = new UserLogin();
        userLogin.login(scanner);
        userLogin.run(scanner);
        scanner.close();
    }
}
