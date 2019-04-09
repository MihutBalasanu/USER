package accounts;

import users.User;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;


public class AccountPayment {

    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());


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

    public Optional<List<Account>> setUserAccontListByCurrency(String currency, User user){

        Optional<List<Account>> accountListsByCurrency;
        List<Account> accountListByCurrency = new ArrayList<>();
        AccountMenu accountMenu = new AccountMenu();
        int countAccount = 0;
        for (Account account : accountMenu.setUserAllAccounts(user)) {
                if (account.getAccountType().equals(currency)) {
                    accountListByCurrency.add(account);
                    countAccount++;
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

    public Account selectAccount(Scanner scanner, Map<Integer, Account> accountMap){

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


    public Optional<Account> chooseAccountFromList(Scanner scanner, String currency, User user) {

        Account selectedAccount = null;
        if(setUserAccontListByCurrency(currency, user).isPresent()) {
            List<Account> accountList = setUserAccontListByCurrency(currency,user).get();
            Map<Integer,Account> accountMap = displayAccountList(accountList);
            selectedAccount = selectAccount(scanner,accountMap);
        }else {
            LOGGER.warning("Not enough accounts to make transfers!");
            return Optional.empty();
        }
        return Optional.of(selectedAccount);
    }

    public Optional<Account> setAccountToPayFrom(Scanner scanner, String currency, User user){
        System.out.println("Select the account to pay from: ");
        return chooseAccountFromList(scanner,currency,user);
    }

    public Optional<Account> setAccountToPayInto(Scanner scanner, Account accountToPayFrom, String currency, User user) {
        System.out.println("Select the account to pay into: ");
        Account selectedAccount = null;
        if(setUserAccontListByCurrency(currency, user).isPresent()) {
            List<Account> accountList = setUserAccontListByCurrency(currency, user).get();
            accountList.remove(accountToPayFrom);
            Map<Integer,Account> accountMap = displayAccountList(accountList);
            selectedAccount = selectAccount(scanner,accountMap);
        }else {
            LOGGER.warning("Not enough accounts to make transfers!");
            return Optional.empty();
        }
        return Optional.of(selectedAccount);
    }

    public boolean verifyEnoughAmountForPayment(BigDecimal amount, Account account){
        BigDecimal rest = amount.subtract(account.getBalance());
        if(rest.signum() == 1){
            return false;
        }else{
            return true;
        }
    }
}
