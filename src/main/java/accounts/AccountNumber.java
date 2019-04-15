package accounts;

import java.util.List;

public class AccountNumber {

    private static Long count = 1L;

    public AccountNumber(){
        count = getCount();
       count++;
    }

    public String generateAccountNumber(){
        String accountNumber = "RO25BCRY3400SV";
        Long number = new Long(1000000000);
        number = number + count;
        accountNumber = accountNumber.concat(String.valueOf(number));
        return accountNumber;
    }

    public Long getCount(){
        AccountFileReader accountFileReader = new AccountFileReader();
        List<Account> accounts = accountFileReader.getAccounts();
        for (Account account : accounts) {
            count++;
            }
        return count;
        }
    }

