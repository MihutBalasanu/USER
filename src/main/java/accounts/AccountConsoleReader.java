package accounts;

import users.User;
import utils.Currency;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.logging.Logger;

public class AccountConsoleReader {

    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());

    public Account readAccountData(Scanner scanner,User user) {
        Account account = new Account();
        System.out.println("Add account details.");
        System.out.println("Amount to put:");

        BigDecimal amount = null;
        while (amount == null || amount.signum() != 1) {
            try {
                amount = scanner.nextBigDecimal();
            } catch (IllegalArgumentException e) {
                LOGGER.warning("Input a proper value: " + scanner.nextLine());
            }
        }
        account.setBalance(amount);

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
        AccountNumber accountNumber = new AccountNumber();
        account.setAccontNumber(accountNumber.generateAccountNumber());

        return account;
    }
}
