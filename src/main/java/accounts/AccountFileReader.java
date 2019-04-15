package accounts;


import utils.Constants;
import utils.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class AccountFileReader {

    private static List<Account> accounts = new ArrayList<>();
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());
    private FileReader fileReader = new FileReader();

    public AccountFileReader(){
        initializeAccountsList();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void initializeAccountsList() {

        List<String> lines = fileReader.readFromFile(Constants.ACCOUNT_FILE_PATH);
        int numberOfLines = 1;
        for(String line : lines){
            String[] parts = line.split(" ");
            if(parts.length >= 4) {
                String accountNumber = parts[0];
                String username = parts[1];
                BigDecimal balance = new BigDecimal(parts[2]);
                String accountType = parts[3];
                Account account = new Account();
                account.setAccontNumber(accountNumber);
                account.setUsername(username);
                account.setBalance(balance);
                account.setAccountType(accountType);
                accounts.add(account);
            }else if(line.equals("")){
                continue;
            }else{
                System.out.println("Wrong information at line " + numberOfLines + " !");
                LOGGER.warning("Wrong information at line " + numberOfLines + " !");
            }
            numberOfLines++;
        }
    }
}
