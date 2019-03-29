package accounts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountFileReader {

    private static List<Account> accountList = new ArrayList<>();

    public static List<Account> readFromFile(String path) {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(path))) {
            String line;
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
                    System.out.println( "Wrong information at line " + line + " !" );
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accountList;
    }
}
