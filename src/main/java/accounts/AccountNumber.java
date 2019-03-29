package accounts;

public class AccountNumber {

    private static Long count = null;

    public AccountNumber(){
        generateAccountNumber();
        count++;
    }

    private String generateAccountNumber(){
        String accountNumber = "RO";
        Long number = new Long(1000000000);
        number = number + count;
        accountNumber = accountNumber.concat(String.valueOf(number));
        return accountNumber;
    }
}
