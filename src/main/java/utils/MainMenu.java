package utils;

/**
 * Holds the menus displayed to the users during the whole application.
 */
public class MainMenu {

    /**
     * Displays the login menu.
     */
    public void displayLoginConsole() {
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Select menu option: ");
    }

    /**
     * Displays the menu in case the users can make transfers.
     */
    public void displayLogoutConsoleWithTransfer() {
        System.out.println("1. Display account menu");
        System.out.println("2. Logout");
        System.out.println("3. Transfer between your accounts");
    }

    /**
     * Displays the menu in case the users can't make transfers.
     */
    public void displayLogoutConsoleWithoutTransfer() {
        System.out.println("1. Display account menu");
        System.out.println("2. Logout");
    }

    /**
     * Displays the menu the user need to make account operations.
     */
    public void displayAccountMenu(){
        System.out.println("1. Create account");
        System.out.println("2. Display user all accounts");
        System.out.println("3. Return");
    }
}
