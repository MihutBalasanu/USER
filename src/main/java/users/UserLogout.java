package users;

import accounts.Account;
import accounts.AccountMenu;
import accounts.AccountPayment;
import accounts.AccountWriter;
import utils.Constants;
import utils.Currency;
import utils.MainMenu;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;

public class UserLogout {

    private UserLogin userLogin = new UserLogin();
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());
    private AccountPayment accountPayment = new AccountPayment();
    private AccountWriter accountWriter = new AccountWriter();

    public void logout(Scanner scanner, User user) {
        AccountMenu accountMenu = new AccountMenu();
        MainMenu mainMenu = new MainMenu();
        if (verifyEnoughAccountsForTransfer(user)) {
            mainMenu.displayLogoutConsoleWithTransfer();
        }
        else{
            mainMenu.displayLogoutConsoleWithoutTransfer();
        }
        System.out.println("Select your option!");

        try {
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    accountMenu.accountOperations(scanner, user);
                    logout(scanner, user);
                    break;

                case 2:
                    LOGGER.info(user.getUsername() + " you are successfully logged out!");
                    userLogin.setLogged(false);
                    user.setUserAccountList(null);
                    break;

                case 3:
                    if (verifyEnoughAccountsForTransfer(user)) {
                        String currency = null;
                        do {
                            currency = accountPayment.setTransferCurrency(scanner);
                        }
                        while (!accountPayment.setUserAccontListByCurrency(currency,user,scanner).isPresent());

                        BigDecimal amount = accountPayment.setTransferAmount(scanner);
                        Account accountToPayFrom = accountPayment.setAccountToPayFrom(scanner, currency, user).get();
                        Account accountToPayInto = accountPayment.setAccountToPayInto(scanner, accountToPayFrom, currency, user).get();

                        boolean enoghMoneyForTransfer = accountPayment.verifyEnoughAmountForPayment(amount, accountToPayFrom);

                        if (enoghMoneyForTransfer) {
                            accountToPayFrom.setBalance(accountToPayFrom.getBalance().subtract(amount));
                            updateUserAccount(accountToPayFrom, accountToPayFrom.getBalance().subtract(amount));
                            accountToPayInto.setBalance(accountToPayInto.getBalance().add(amount));
                            updateUserAccount(accountToPayInto, accountToPayInto.getBalance().add(amount));
                            LOGGER.info("You succesfully transfer the amount: " + amount + " " + currency);
                        } else {
                            LOGGER.warning("Not enough money in your account!");
                        }
                    } else {
                        LOGGER.warning("Not a valid option!");
                        logout(scanner, user);
                    }
                    logout(scanner, user);
                    break;

                default:
                    LOGGER.warning("Not a valid option!");
                    logout(scanner, user);
                    break;
            }
        } catch (InputMismatchException exception) {
            LOGGER.warning("Try again!");
            LOGGER.warning("Invalid input: " + scanner.nextLine());
            logout(scanner, user);
        }
    }

    public boolean verifyEnoughAccountsForTransfer(User user){
        boolean enoughAccountsForTransfer = false;
        Map<String, Integer> numberOfAccountsPerCurrency = new HashMap<>();
        for(Currency currency: Currency.values()){
            numberOfAccountsPerCurrency.put(String.valueOf(currency),0);
        }
        for(Account account: user.getUserAccountList()){
            for(Currency currency: Currency.values()){
                if(account.getAccountType().equals(String.valueOf(currency))){
                    numberOfAccountsPerCurrency.put(String.valueOf(currency), numberOfAccountsPerCurrency.get(String.valueOf(currency)) + 1);
                    if(numberOfAccountsPerCurrency.get(String.valueOf(currency)) >= 2){
                        enoughAccountsForTransfer = true;
                    }
                }
            }
        }
        return enoughAccountsForTransfer;
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

