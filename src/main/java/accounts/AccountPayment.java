package accounts;

import users.User;
import utils.Currency;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;

/**
 * Contains methods that help user to make transfers between his accounts
 * @author Mihut Balasanu
 */
public class AccountPayment {

    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());

    /**
     * Sets the currency for the transfer as input of the user
     * @param scanner Instance of Scanner
     * @return A string containing the currency of the transfer
     */
    public String setTransferCurrency(Scanner scanner) {
        // Ensures a proper value input for currency
        boolean properCurrency = false;
        System.out.println("Set currency: " + scanner.nextLine());
        String setCurrency = null;
        while (!properCurrency) {
           setCurrency = scanner.nextLine();
           // The currency from input should be equal with one of the constants of the Currency enum
            for (Currency currency : Currency.values()) {
                if (String.valueOf(currency).equals(setCurrency)) {
                    properCurrency = true;
                }
            }
            if(!properCurrency){
                LOGGER.warning("Choose a proper currency!");
                System.out.println("Set currency!");
            }
        }
        return setCurrency;
    }

    /**
     * Sets user account list with the same currency
     * @param currency A string containing the currency for the account list
     * @param user Instance of User
     * @param scanner Instance of Scanner
     * @return An Optional of list of accounts of a set currency for the user
     */
    public Optional<List<Account>> setUserAccontListByCurrency(String currency, User user, Scanner scanner){

        Optional<List<Account>> accountListsByCurrency;
        List<Account> accountListByCurrency = new ArrayList<>();
        AccountMenu accountMenu = new AccountMenu();
        // Extracts from user account list only the accounts with "currency" as account type
        int countAccount = 0;
        for (Account account : user.getUserAccountList()) {
                if (account.getAccountType().equals(currency)) {
                    accountListByCurrency.add(account);
                    countAccount++;
                }
        }
        // If the user has at least 2 accounts with the same currency he can make transfers and
        // the Optional is not empty
        if (countAccount >= 2) {
            accountListsByCurrency = Optional.of(accountListByCurrency);
            return accountListsByCurrency;
        } else {
            LOGGER.warning("Not enough accounts to make transfers!");
            accountMenu.accountOperations(scanner,user);
            return Optional.empty();
        }
    }

    /**
     * Sets the amount to transfer as input of the user
     * @param scanner Instance of Scanner
     * @return A BigDecimal containing the amount set by the user for transfer
     */
    public BigDecimal setTransferAmount(Scanner scanner){
        System.out.println("Set amount to transfer: ");
        // Ensures a proper value for input of the amount
        BigDecimal amount = null;
        while(amount == null){
            try {
                amount = scanner.nextBigDecimal();
                if(amount.signum() <= 0){
                    LOGGER.warning("Choose a proper amount!" + scanner.nextLine());
                    amount = null;
                }
            }catch(InputMismatchException e) {
                LOGGER.warning("Choose a proper amount!" + scanner.nextLine());
                amount = null;
            }
        }
        return amount;
    }

    /**
     * Displays the accounts of the user from which he can choose in order to make transfers
     * @param accountList An account list
     * @return A map containing an order number as key and an account as value.
     */
    public Map<Integer,Account> displayAccountList(List<Account> accountList){
        Map<Integer,Account> accounts = new HashMap<>();
        int countAccount = 1;
        for(Account account: accountList){
            System.out.println(countAccount + "." + account);
            accounts.put(countAccount, account);
            countAccount++;
        }
        return accounts;
    }

    /**
     * Selects an account according to user's option.
     * @param scanner Instance of Scanner
     * @param accountMap A map containing an integer for user's option as key and an account of the user
     *                   as value
     * @return An instance of Account as user's choice
     */
    public Account selectAccount(Scanner scanner, Map<Integer, Account> accountMap){

        // Ensures a proper selection of the user
        Account selectedAccount = null;
        try {
            int option = 0;
            while (option == 0) {
                option = scanner.nextInt();
                if (option > 0 && option <= accountMap.size()) {
                    selectedAccount = accountMap.get(option);
                } else {
                    System.out.println("Invalid input!");
                }
            }
        }catch (InputMismatchException exception) {
            LOGGER.warning("Try again!");
            LOGGER.warning("Invalid input: " + scanner.nextLine());
        }
        return selectedAccount;
    }

    /**
     * Selects an account from user's list of accounts available
     * @param scanner Instance of Scanner
     * @param currency A string containing the currency for transfer
     * @param user Instance of User
     * @return An Optional of Account as the user's choice from his list
     */
    public Optional<Account> chooseAccountFromList(Scanner scanner, String currency, User user) {

        Account selectedAccount = null;
        if(setUserAccontListByCurrency(currency, user,scanner).isPresent()) {
            List<Account> accountList = setUserAccontListByCurrency(currency,user,scanner).get();
            Map<Integer,Account> accountMap = displayAccountList(accountList);
            selectedAccount = selectAccount(scanner,accountMap);
        }else {
            LOGGER.warning("Not enough accounts to make transfers!");
            return Optional.empty();
        }
        return Optional.of(selectedAccount);
    }

    /**
     * Sets account to pay from as a selection from user's account list of one currency
     * @param scanner Instance of Scanner
     * @param currency A string containing the currency for transfer
     * @param user Instance of User
     * @return An Optional of Account as the user's choice for make payment
     */
    public Optional<Account> setAccountToPayFrom(Scanner scanner, String currency, User user){
        System.out.println("Select the account to pay from: ");
        return chooseAccountFromList(scanner,currency,user);
    }

    /**
     * Sets account to pay into as a selection from user's account list of one currency
     * @param scanner Instance of Scanner
     * @param accountToPayFrom The account from which user make payment
     * @param currency A string containing the currency for transfer
     * @param user Instance of User
     * @return An Optional of Account as the user's choice for receive payment
     */
    public Optional<Account> setAccountToPayInto(Scanner scanner, Account accountToPayFrom, String currency, User user) {
        System.out.println("Select the account to pay into: ");
        Account selectedAccount = null;
        if(setUserAccontListByCurrency(currency, user,scanner).isPresent()) {
            List<Account> accountList = setUserAccontListByCurrency(currency,user,scanner).get();
            // Ensures that the account to pay from is not the same with the one to pay into
            accountList.remove(accountToPayFrom);
            Map<Integer,Account> accountMap = displayAccountList(accountList);
            selectedAccount = selectAccount(scanner,accountMap);
        }else {
            LOGGER.warning("Not enough accounts to make transfers!");
            return Optional.empty();
        }
        return Optional.of(selectedAccount);
    }

    /**
     * Verifies if there is enough money to pay.
     * @param amount The amount to be checked
     * @param account The account to pay from
     * @return True if there is enough money to make payment, otherwise returns false
     */
    public boolean verifyEnoughAmountForPayment(BigDecimal amount, Account account){
        BigDecimal rest = amount.subtract(account.getBalance());
        if(rest.signum() == 1){
            return false;
        }else{
            return true;
        }
    }
}
