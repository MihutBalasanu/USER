package users;

import accounts.Account;
import accounts.AccountMenu;
import accounts.AccountPayment;
import accounts.AccountWriter;
import utils.Constants;
import utils.Currency;
import utils.MainMenu;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;

/**
 * Contains the logic for logout menu
 */
public class UserLogout {

    private UserLogin userLogin = new UserLogin();
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());
    private AccountPayment accountPayment = new AccountPayment();
    private AccountWriter accountWriter = new AccountWriter();

    /**
     * Contains the logic for logout menu
     * @param scanner Instance of Scanner
     * @param user Instance of User
     */
    public void logout(Scanner scanner, User user) {
        AccountMenu accountMenu = new AccountMenu();
        MainMenu mainMenu = new MainMenu();
        // Display the proper menu whether user has enough accounts for transfer or not
        if (verifyEnoughAccountsForTransfer(user)) {
            mainMenu.displayLogoutConsoleWithTransfer();
        }
        else{
            mainMenu.displayLogoutConsoleWithoutTransfer();
        }
        System.out.println("Select your option!");

        // Ensures a proper user's selection
        try {
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    // User is enabled to make account operations
                    accountMenu.accountOperations(scanner, user);
                    // Enables the user to make another account operation if he wants
                    logout(scanner, user);
                    break;

                case 2:
                    LOGGER.info(user.getUsername() + " you are successfully logged out!");
                    userLogin.setLogged(false);
                    user.setUserAccountList(null);
                    break;

                case 3:
                    if (verifyEnoughAccountsForTransfer(user)) {
                        // Logic for transfer between user's accounts
                        String currency = null;
                        // Ensures a proper choice of currency
                        do {
                            currency = accountPayment.setTransferCurrency(scanner);
                        }
                        while (!accountPayment.setUserAccontListByCurrency(currency,user,scanner).isPresent());
                        // Sets the amount to transfer
                        BigDecimal amount = accountPayment.setTransferAmount(scanner);
                        // Sets the account to pay from
                        Account accountToPayFrom = accountPayment.setAccountToPayFrom(scanner, currency, user).get();
                        // Sets the amount to pay into
                        Account accountToPayInto = accountPayment.setAccountToPayInto(scanner, accountToPayFrom, currency, user).get();
                        // Sets the reference for the check of amount available
                        boolean enoghMoneyForTransfer = accountPayment.verifyEnoughAmountForPayment(amount, accountToPayFrom);
                        // Sets the transfer if it is possible
                        if (enoghMoneyForTransfer) {
                            accountToPayFrom.setBalance(accountToPayFrom.getBalance().subtract(amount));
                            updateUserAccount(accountToPayFrom, accountToPayFrom.getBalance().subtract(amount));
                            accountToPayInto.setBalance(accountToPayInto.getBalance().add(amount));
                            updateUserAccount(accountToPayInto, accountToPayInto.getBalance().add(amount));
                            LOGGER.info("You succesfully transfer the amount: " + amount + " " + currency);
                        } else {
                            LOGGER.warning("Not enough money in your account!");
                        }
                    } else {
                        LOGGER.warning("Not a valid option!");
                        logout(scanner, user);
                    }
                    // Enables the user to make another account operation if he wants
                    logout(scanner, user);
                    break;

                default:
                    LOGGER.warning("Not a valid option!");
                    // Enables the user to make another choice from menu
                    logout(scanner, user);
                    break;
            }
        } catch (InputMismatchException exception) {
            LOGGER.warning("Try again!");
            // Enables the user to make a proper choice from menu
            LOGGER.warning("Invalid input: " + scanner.nextLine());
            logout(scanner, user);
        }
    }

    /**
     * Verifies if the user have at least 2 accounts of a currency to be able to make transfers
     * @param user Instance of User
     * @return True if the user has enough accounts to make transfers
     */
    public boolean verifyEnoughAccountsForTransfer(User user){
        boolean enoughAccountsForTransfer = false;

        // A map with valid currencies as keys and the corresponding number of user's accounts as value
        Map<String, Integer> numberOfAccountsPerCurrency = new HashMap<>();
        for(Currency currency: Currency.values()){
            numberOfAccountsPerCurrency.put(String.valueOf(currency),0);
        }
        // For every account it is increased the map value with 1 for the key equal with the account currency
        for(Account account: user.getUserAccountList()){
            for(Currency currency: Currency.values()){
                if(account.getAccountType().equals(String.valueOf(currency))){
                    numberOfAccountsPerCurrency.put(String.valueOf(currency), numberOfAccountsPerCurrency.get(String.valueOf(currency)) + 1);
                    // If it is reached 2 accounts for any currency the boolean is set true
                    if(numberOfAccountsPerCurrency.get(String.valueOf(currency)) >= 2){
                        enoughAccountsForTransfer = true;
                    }
                }
            }
        }
        // If no currency has at least 2 as value the boolean remains false
        return enoughAccountsForTransfer;
    }

    /**
     * Updates an existing account in the "account details.txt" file
     * @param account Account to be modified
     * @param amount New balance of the account
     */
        public void updateUserAccount(Account account, BigDecimal amount){
            // Sets the file to be modified
            File fileToBeModified = new File(Constants.ACCOUNT_FILE_PATH);
            // Sets the account updated data in the proper format
            String newString = accountWriter.displayAccountData(account);
            // Sets the reference for the string to be modified
            String oldString = null;
            // Sets the reference for the old content of the file
            String oldContent = "";
            BufferedReader reader = null;
            FileWriter writer = null;
            // Read the old content, rebuild it and look for the account to be modified
            try{
                reader = new BufferedReader(new FileReader(fileToBeModified));
                String line = reader.readLine();
                while (line != null){
                    oldContent = oldContent + line + System.lineSeparator();
                    // Assign the line to oldString when it is reached the account to be modified
                    if(!line.equals("") && line.substring(0,24).equals(account.getAccontNumber())){
                        oldString = line;
                    }
                    line = reader.readLine();
                }
                // Sets the reference for the new content
                String newContent = null;
                if (oldString != null) {
                    // Make the modification
                    newContent = oldContent.replaceAll(oldString, newString);
                }
                // Writes the updated content
                writer = new FileWriter(fileToBeModified);
                if (newContent != null) {
                    writer.write(newContent);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                // Close reader and writer regardless there is exception thrown or not
                try{
                    if (reader != null) {
                        reader.close() ;
                    }
                    if (writer != null) {
                        writer.close();
                    }

                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

