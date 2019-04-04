package accounts;

import users.UserLogin;

import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;


public class AccountPayment {

    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());
    private UserLogin userLogin = new UserLogin();


    public String setTransferCurrency(Scanner scanner){
        System.out.println("Set currency: ");
        String currency = null;
        while (currency == null) {
            try {
                currency = scanner.next();
            } catch (InputMismatchException e) {
                currency = null;
                LOGGER.warning("Choose a proper currency!");
            }
        }
        return currency;
    }

    public Optional<List<Account>> setUserAccontListByCurrency(String currency){
        Optional<List<Account>> accountListsByCurrency;
        List<Account> accountListByCurrency = new ArrayList<>();
        int countAccount = 0;
        if(userLogin.getValidatedUser().isPresent()) {
            for (Account account : userLogin.getValidatedUser().get().getUserAccountList()) {
                if (account.getAccountType().equals(currency)) {
                    accountListByCurrency.add(account);
                    countAccount++;
                }
            }
        }
        if (countAccount >= 2) {
            accountListsByCurrency = Optional.of(accountListByCurrency);
            return accountListsByCurrency;
        } else {
            LOGGER.warning("Not enough accounts to make transfers!");
            return Optional.empty();
        }
    }

    public BigDecimal setTransferAmount(Scanner scanner){
        System.out.println("Set amount to transfer: ");
        BigDecimal amount = null;
        while(amount == null){
            try {
                amount = scanner.nextBigDecimal();
            }catch(InputMismatchException e) {
                amount = null;
                LOGGER.warning("Choose a proper amount!");
            }
        }
        return amount;
    }

    public Account chooseAccountFromList(Scanner scanner, String currency) {
        List<Account> accountList = setUserAccontListByCurrency(currency).get();

            int countAccount = 1;
            Account[] accounts = null;

            for (Account account : accountList) {
                System.out.println(countAccount + ". " + account.getAccontNumber());

                accounts[countAccount - 1] = account;
                countAccount++;
            }
            System.out.println("Select the account to pay from: ");
            int option = 0;
            try {
                while (option == 0) {
                    option = scanner.nextInt();
                    for (int i = 1; i < countAccount; i++) {
                        if (i == option) {
                            return accounts[i];
                        } else {
                            option = 0;
                        }
                    }
                }
            } catch (InputMismatchException exception) {
                LOGGER.warning("Try again!");
                LOGGER.warning("Invalid input: " + scanner.nextLine());

        }return null;
    }

    public Account setAccountToPayFrom(Scanner scanner, String currency){
        return chooseAccountFromList(scanner,currency);
    }

    public Account setAccountToPayInto(Scanner scanner, Account accountToPayFrom, String currency) {
        Account AccountToPayInto = null;
        while ( AccountToPayInto == accountToPayFrom) {
            AccountToPayInto = chooseAccountFromList(scanner,currency);
        }
        return AccountToPayInto;
    }

    public boolean verifyEnoughAmountForPayment(Scanner scanner, BigDecimal amount, String currency){
        if(amount.compareTo((setAccountToPayFrom(scanner,currency).getBalance())) > 0){
            return false;
        }else{
            return true;
        }
    }
}
