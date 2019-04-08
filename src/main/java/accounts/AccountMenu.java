package accounts;

import users.User;
import users.UserLogin;
import users.UserLogout;
import utils.Constants;
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
    private UserLogout userLogout = new UserLogout();

    public void accountOperations(Scanner scanner,User user) {

        try{
            int option;
            System.out.println("Select your option: ");
            option = scanner.nextInt();
                switch (option) {
                    case 1:
                        createUserAccount(scanner,user);
                        break;
                    case 2:
                        displayUserAllAccounts(user);
                        break;
                    case 3:
                        userLogin.run(scanner);
                        break;
                    case 4:
                        if(userLogout.isEnoughAccountsForTransfer()) {
                            String currency = accountPayment.setTransferCurrency(scanner);
                            BigDecimal amount = accountPayment.setTransferAmount(scanner);
                            Account accountToPayFrom = accountPayment.setAccountToPayFrom(scanner, currency, user).get();;
                            Account accountToPayInto = accountPayment.setAccountToPayInto(scanner, accountToPayFrom, currency, user).get();
//
                            boolean enoghMoneyForTransfer = accountPayment.verifyEnoughAmountForPayment(amount,accountToPayFrom);

                            if (enoghMoneyForTransfer) {
                                accountToPayFrom.setBalance(accountToPayFrom.getBalance().subtract(amount));
                                accountToPayInto.setBalance(accountToPayInto.getBalance().add(amount));
                                LOGGER.info("You succesfully transfer the amount: " + amount + " " + currency);
                            } else {
                                LOGGER.warning("Not enough money in your account!");
                            }
                        }else{
                            LOGGER.warning("Not a valid option!");
                            accountOperations(scanner,user);
                        }
                        break;
                    default:
                        LOGGER.warning("Not a valid option!");
                        accountOperations(scanner,user);
                        break;
                }
        } catch (InputMismatchException exception) {
            LOGGER.warning("Invalid input: " + scanner.nextLine());

        } catch (NoSuchElementException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    public List<Account> setUserAllAccounts(User user) {
        List<Account> userAllAccountsList = new ArrayList<>();
        AccountFileReader accountFileReader = AccountFileReader.getInstance();
        List<Account> accounts = accountFileReader.getAccounts();
        for (Account account : accounts) {
            if (user.getUsername().equals(account.getUsername())) {
                userAllAccountsList.add(account);
            }
        }
        return userAllAccountsList;
    }

    private void displayUserAllAccounts(User user) {
        List<Account> userAccountList = new ArrayList<>();
        AccountFileReader accountFileReader = AccountFileReader.getInstance();
        List<Account> accounts = accountFileReader.getAccounts();
        for (Account account : accounts) {
            if (user.getUsername().equals(account.getUsername())) {
                userAccountList.add(account);
                System.out.println(accountWriter.displayAccountData(account));
            }
        }
        if(userAccountList.isEmpty()){
            LOGGER.info(user.getUsername() + " has no account yet!");
        }
    }

    private void createUserAccount(Scanner scanner, User user){
        Account account = accountConsoleReader.readAccountData(scanner,user);
        AccountFileWriter.writeOnFile(Constants.ACCOUNT_FILE_PATH, accountWriter.displayAccountData(account));
    }
}


