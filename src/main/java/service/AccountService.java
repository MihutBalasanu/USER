package service;

import dao.AccountDao;
import dao.NotificationDao;
import dao.TransactionDao;
import dao.UserDao;
import model.Account;
import model.Notification;
import model.Transaction;
import model.User;
import utils.AccountValidator;
import utils.CurrencyType;
import utils.MoneyTransferException;
import utils.SessionUtil;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

public class AccountService {

    private AccountDao accountDao = new AccountDao();
    private UserDao userDao;
    private TransactionDao transactionDao;
    private NotificationDao notificationDao;
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());

    public AccountService() {
        accountDao = new AccountDao();
        userDao = new UserDao();
        transactionDao = new TransactionDao();
    }

    public Account createAccount() {
        Scanner scanner = new Scanner(System.in);
        boolean isValidAccount = false;
        String accountID = "";
        while (!isValidAccount) {
            System.out.println();
            System.out.print("Account number (RO + 22 characters): ");
            accountID = scanner.nextLine();
            isValidAccount = AccountValidator.validateId(accountID);
        }
        System.out.print("Amount of money: ");
        BigDecimal balance = setBalance(scanner);
        System.out.print("Currency [RON/EUR]: ");
        String currency = setCurrency(scanner);

        Account account = new Account(accountID, balance, currency);
        account.setUser(SessionUtil.user);

        accountDao.createEntity(account);
        return account;
    }

    public String setCurrency(Scanner scanner) {
        // Ensures a proper value input for currency
        boolean properCurrency = false;
        System.out.println("Set currency: " + scanner.nextLine());
        String setCurrency = null;
        while (!properCurrency) {
            setCurrency = scanner.nextLine();
            // The currency from input should be equal with one of the constants of the CurrencyType enum
            for (CurrencyType currency : CurrencyType.values()) {
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

    public BigDecimal setBalance(Scanner scanner){
        System.out.println("Set balance: ");
        // Ensures a proper value for input of the amount
        BigDecimal balance = null;
        while(balance == null){
            try {
                balance = scanner.nextBigDecimal();
                if(balance.signum() <= 0){
                    LOGGER.warning("Choose a proper amount!" + scanner.nextLine());
                    balance = null;
                }
            }catch(InputMismatchException e) {
                LOGGER.warning("Choose a proper amount!" + scanner.nextLine());
                balance = null;
            }
        }
        return balance;
    }

    public void makePayment(User user) throws MoneyTransferException {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.print("From account: ");
        String fromAccountName = scanner.nextLine();
        System.out.print("To account: ");
        String toAccountName = scanner.nextLine();
        System.out.print("Amount: ");
        BigDecimal amount = BigDecimal.ZERO;
        try {
            amount = new BigDecimal(Integer.parseInt(scanner.nextLine()));
        } catch (NumberFormatException err) {
            throw new MoneyTransferException("Amount of money should be a positive number", err);
        }
        System.out.println("Details about payment: ");
        String details = scanner.nextLine();
        if (transferMoney(fromAccountName, toAccountName, amount, details)) {
            LOGGER.info("Transaction completed successfully!");
        } else {
            LOGGER.warning("Transaction failed!");
        }
    }

    private boolean transferMoney(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String details) {
        Account fromAccount = accountDao.findByAccountNumber(fromAccountNumber);
        if (fromAccount == null) {
            LOGGER.warning("Invalid source account!");
            return false;
        }
        Account toAccount = accountDao.findByAccountNumber(toAccountNumber);
        if (toAccount == null) {
            LOGGER.warning("Invalid destination account!");
            return false;


        }
        if (fromAccountNumber.equals(toAccountNumber)) {
            LOGGER.warning("Source and destination accounts must be different!");
            return false;
        }
        if (amount.compareTo(fromAccount.getBalance()) == 1) {
            LOGGER.warning("Insufficient funds!");
            return false;
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountDao.updateEntity(fromAccount);
        accountDao.updateEntity(toAccount);

        Transaction transaction1 = new Transaction();
        transaction1.setAmount(amount);
        transaction1.setAccount(toAccount);
        Transaction transaction2 = new Transaction();
        transaction2.setAccount(fromAccount);
        transactionDao.createEntity(transaction1);
        transactionDao.createEntity(transaction2);

        Notification notification = new Notification();
        notification.setDetails(details);
        notificationDao.createEntity(notification);

        return true;
    }

}
