package accounts;

import users.User;
import utils.Currency;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;


public class AccountPayment {

    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());
    private boolean properCurrency = false;


    public String setTransferCurrency(Scanner scanner) {
        System.out.println("Set currency: " + scanner.nextLine());
        String setCurrency = null;
        while (!properCurrency) {
           setCurrency = scanner.nextLine();
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


    public Optional<List<Account>> setUserAccontListByCurrency(String currency, User user, Scanner scanner){

        Optional<List<Account>> accountListsByCurrency;
        List<Account> accountListByCurrency = new ArrayList<>();
        AccountMenu accountMenu = new AccountMenu();
        int countAccount = 0;
        for (Account account : user.getUserAccountList()) {
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
            accountMenu.accountOperations(scanner,user);
            return Optional.empty();
        }
    }

    public BigDecimal setTransferAmount(Scanner scanner){
        System.out.println("Set amount to transfer: ");
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

    public Optional<Account> setAccountToPayFrom(Scanner scanner, String currency, User user){
        System.out.println("Select the account to pay from: ");
        return chooseAccountFromList(scanner,currency,user);
    }

    public Optional<Account> setAccountToPayInto(Scanner scanner, Account accountToPayFrom, String currency, User user) {
        System.out.println("Select the account to pay into: ");
        Account selectedAccount = null;
        if(setUserAccontListByCurrency(currency, user,scanner).isPresent()) {
            List<Account> accountList = setUserAccontListByCurrency(currency,user,scanner).get();
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
