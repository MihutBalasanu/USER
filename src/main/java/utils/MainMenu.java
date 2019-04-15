package utils;

public class MainMenu {


    public void displayLoginConsole() {
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Select menu option: ");
    }

    public void displayLogoutConsoleWithTransfer() {
        System.out.println("1. Display account menu");
        System.out.println("2. Logout");
        System.out.println("3. Transfer between your accounts");
    }

    public void displayLogoutConsoleWithoutTransfer() {
        System.out.println("1. Display account menu");
        System.out.println("2. Logout");
    }

    public void displayAccountMenu(){
        System.out.println("1. Create account");
        System.out.println("2. Display user all accounts");
        System.out.println("3. Return");
//        System.out.println("4. Transfer between your accounts");
    }
}
