package accounts;

import users.User;
import users.UserLogin;
import utils.Currency;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountConsoleReader {

    UserLogin userLogin = new UserLogin();


    public Account readAccountData() {
        Account account = new Account();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add account details.");
        System.out.println("Amount to put:");
        account.setBalance(scanner.nextBigDecimal());
        System.out.println("Currency:");
        String currency = scanner.nextLine();
        try {
            account.setAccountType(String.valueOf(Currency.valueOf(currency)));
        } catch (InputMismatchException e) {
            System.out.println("Choose a proper currency!");
        }
        User user = userLogin.getValidatedUser().orElseThrow(IllegalArgumentException::new);
        account.setUsername(user.getUsername());

        AccountNumber accountNumber = new AccountNumber();
        account.setAccontNumber(String.valueOf(accountNumber));

        return account;
    }
}
