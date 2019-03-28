package users;

import utils.MainMenu;

public class Main {

    public static void main(String[] args) {
        UserLogin userLogin = UserLogin.getInstance();
        userLogin.run();
    }
}
