package accounts;

import users.User;
import utils.Currency;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Reads the input data from the user when he is creating an account.
 * @author Mihut Balasanu
 */
public class AccountConsoleReader {

    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());

    /**
     * Reads the input data from the user when he is creating an account.
     * @param scanner instance of Scanner
     * @param user instance of User
     * @return an account of the user according to his inputs
     */
    public Account readAccountData(Scanner scanner,User user) {
        Account account = new Account();
        System.out.println("Add account details.");
        System.out.println("Amount to put:");

        // Block of codes that ensures a proper value input of the amount at the opening of the account
        BigDecimal amount = null;
        while (amount == null || amount.signum() != 1) {
            try {
                amount = scanner.nextBigDecimal();
            } catch (IllegalArgumentException e) {
                LOGGER.warning("Input a proper value: " + scanner.nextLine());
            }
        }
        account.setBalance(amount);

        // Block of codes that ensures a proper value input of the currency at the opening of the account
        System.out.println("Currency:");
        String currency = null;
        while (currency == null) {
            currency = scanner.next();
            try {
                account.setAccountType(String.valueOf(Currency.valueOf(currency)));
            } catch (IllegalArgumentException e) {
                currency = null;
                LOGGER.warning("Choose a proper currency!");
            }
        }
        account.setUsername(user.getUsername());
        // The constructor autogenerates an account number
        AccountNumber accountNumber = new AccountNumber();
        account.setAccontNumber(accountNumber.generateAccountNumber());

        return account;
    }
}
