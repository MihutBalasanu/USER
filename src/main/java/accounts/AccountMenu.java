package accounts;

import users.User;
import users.UserLogin;
import users.UserLogout;
import utils.Constants;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;

public class AccountMenu {

    private AccountWriter accountWriter = new AccountWriter();
    private UserLogin userLogin = new UserLogin();
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
                            String currency = null;
                            do {
                                currency = accountPayment.setTransferCurrency(scanner);
                            }
                            while(!accountPayment.setUserAccontListByCurrency(currency,user).isPresent());
//                                LOGGER.warning("You don't have at least 2 accounts of " + currency + " type to make transfers");

                            BigDecimal amount = accountPayment.setTransferAmount(scanner);
                            Account accountToPayFrom = accountPayment.setAccountToPayFrom(scanner,currency,user).get();;
                            Account accountToPayInto = accountPayment.setAccountToPayInto(scanner, accountToPayFrom,currency, user).get();
//
                            boolean enoghMoneyForTransfer = accountPayment.verifyEnoughAmountForPayment(amount,accountToPayFrom);

                            if (enoghMoneyForTransfer) {
                                accountToPayFrom.setBalance(accountToPayFrom.getBalance().subtract(amount));
                                updateUserAccount( accountToPayFrom,accountToPayFrom.getBalance().subtract(amount));
                                accountToPayInto.setBalance(accountToPayInto.getBalance().add(amount));
                                updateUserAccount(accountToPayInto,accountToPayInto.getBalance().add(amount));
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

    public void updateUserAccount(Account account, BigDecimal amount){

        File fileToBeModified = new File(Constants.ACCOUNT_FILE_PATH);
        String newString = accountWriter.displayAccountData(account);
        String oldString = null;

        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;

        try{
            reader = new BufferedReader(new FileReader(fileToBeModified));
            String line = reader.readLine();
            while (line != null){
                oldContent = oldContent + line + System.lineSeparator();
                if(!line.equals("") && line.substring(0,24).equals(account.getAccontNumber())){
                    oldString = line;
                }
                line = reader.readLine();
            }
            String newContent = null;
            if (oldString != null) {
                newContent = oldContent.replaceAll(oldString, newString);
            }
            writer = new FileWriter(fileToBeModified);
            if (newContent != null) {
                writer.write(newContent);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try{
                if (reader != null) {
                    reader.close() ;
                }
                if (writer != null) {
                    writer.close();
                }

            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}


