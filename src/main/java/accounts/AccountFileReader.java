package accounts;


import utils.Constants;
import utils.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Reads the list of the accounts from the "account details.txt" file.
 * @author Mihut Balasanu
 */
public class AccountFileReader {

    private List<Account> accounts = new ArrayList<>();
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());
    private FileReader fileReader = new FileReader();

    /**
     * Every instance creation determines initialization of the account list.
     */
    public AccountFileReader(){
        initializeAccountsList();
    }

    /**
     * Gets the list of the accounts from the "account details.txt" file.
     * @return List of the accounts already created from all users
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Reads the list of the accounts from the "account details.txt" file.
     */
    public void initializeAccountsList() {

        // Set the list of lines read from the file
        List<String> lines = fileReader.readFromFile(Constants.ACCOUNT_FILE_PATH);
        // Every line is split in parts that form an array.
        int numberOfLines = 1;
        for(String line : lines){
            String[] parts = line.split(" ");
            // To every element of the array, according to his index, it is assigned a field of an Account
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
            // Empty lines will be skipped
            }else if(line.equals("")){
                continue;
            // The user is notified if the line is incomplete
            }else{
                LOGGER.warning("Wrong information at line " + numberOfLines + " !");
            }
            numberOfLines++;
        }
    }
}
