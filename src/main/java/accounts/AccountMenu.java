package accounts;

import users.User;
import users.UserLogin;
import utils.Constants;
import utils.Currency;
import utils.MainMenu;

import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;

public class AccountMenu {

    private AccountWriter accountWriter = new AccountWriter();
    private UserLogin userLogin = new UserLogin();
    private MainMenu mainMenu = new MainMenu();
    private AccountConsoleReader accountConsoleReader = new AccountConsoleReader();
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());
    private AccountPayment accountPayment = new AccountPayment();


    public void accountOperations(Scanner scanner) {

        mainMenu.displayAccountMenu();
        try{
        int option;
        System.out.println("Select your option: ");
        option = scanner.nextInt();
            switch (option) {
                case 1:
                    createUserAccount(scanner);
                    break;
                case 2:
                    if(userLogin.getValidatedUser().isPresent()) {
                        displayUserAllAccounts(userLogin.getValidatedUser().get());
                    }
                    break;
                case 3:
                    String currency = accountPayment.setTransferCurrency(scanner);
                    BigDecimal amount = accountPayment.setTransferAmount(scanner);
                    Account accountToPayFrom = accountPayment.setAccountToPayFrom(scanner,currency);
                    Account accountToPayInto = accountPayment.setAccountToPayInto(scanner,accountToPayFrom,currency);
                    boolean enoghMoneyForTransfer = accountPayment.verifyEnoughAmountForPayment(scanner,amount,currency);
                    if(enoghMoneyForTransfer){
                        accountToPayFrom.setBalance(accountToPayFrom.getBalance().subtract(amount));
                        accountToPayInto.setBalance(accountToPayInto.getBalance().add(amount));
                        LOGGER.info("You succesfully transfer the amount: " + amount + currency);
                    }else{
                        LOGGER.warning("Not enough money in your account!");

                    }
                    break;
                case 4:
                    userLogin.run(scanner);
                    break;
                default:
//                    System.out.println("Not a valid option!");
                    LOGGER.warning("Not a valid option!");
                    accountOperations(scanner);
                    break;
            }
        } catch (InputMismatchException exception) {
//            System.out.println("Invalid input: " + scanner.nextLine());
              LOGGER.warning("Invalid input: " + scanner.nextLine());

        } catch (NoSuchElementException e) {
//            e.printStackTrace();
            LOGGER.warning(e.getMessage());
        }
    }

    public List<Account> setUserLoginAllAccounts() {
        List<Account> userLoginAllAccountsList = new ArrayList<>();
        List<Account> accounts = AccountFileReader.getInstance().getAccounts(Constants.ACCOUNT_FILE_PATH);

        if(userLogin.getValidatedUser().isPresent()) {
            for (Account account : accounts) {
                if (userLogin.getValidatedUser().get().getUsername().equals(account.getUsername())) {
                    userLoginAllAccountsList.add(account);
                }
            }
        }
        return userLoginAllAccountsList;
    }

    private void displayUserAllAccounts(User user) {

        List<Account> userAccountList = new ArrayList<>();
        List<Account> accounts = AccountFileReader.getInstance().getAccounts(Constants.ACCOUNT_FILE_PATH);

        for (Account account : accounts) {
            if (user.getUsername().equals(account.getUsername())) {
                userAccountList.add(account);
                System.out.println(accountWriter.displayAccountData(account));
            }
        }
        if(userAccountList.isEmpty()){
//            System.out.println(user.getUsername() + " has no account yet!");
            LOGGER.info(user.getUsername() + " has no account yet!");
        }
    }

    private Account createUserAccount(Scanner scanner){

        Account account = accountConsoleReader.readAccountData(scanner);
        AccountFileWriter.writeOnFile(Constants.ACCOUNT_FILE_PATH, accountWriter.displayAccountData(account));
        return account;
    }

}


