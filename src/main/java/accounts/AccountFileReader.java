package accounts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AccountFileReader {

    private static List<Account> accountList = new ArrayList<>();
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());

    public static List<Account> readFromFile(String path) {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(path))) {
            String line;
            int numberOfLines = 1;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" " );
                if (parts.length >= 4) {
                    String accontNumber = parts[0];
                    String username = parts[1];
                    BigDecimal balance = new BigDecimal(parts[2]);
                    String accountType = parts[3];

                    Account account = new Account(accontNumber,username,balance,accountType);
                    accountList.add(account);
                } else {
//                    System.out.println( "Wrong information at line " + numberOfLines + " !" );
                      LOGGER.warning("Wrong information at line " + numberOfLines + " !" );
                }
                numberOfLines++;
            }
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            LOGGER.warning(e.getMessage());
        } catch (IOException e) {
//            e.printStackTrace();
            LOGGER.warning(e.getMessage());
        }
        return accountList;
    }
}
