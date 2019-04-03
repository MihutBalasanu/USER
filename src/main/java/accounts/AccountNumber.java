package accounts;

public class AccountNumber {

    private static Long count = 1L;

    public AccountNumber(){
       count++;
    }

    public String generateAccountNumber(){
        String accountNumber = "RO25BCRY3400SV";
        Long number = new Long(1000000000);
        number = number + count;
        accountNumber = accountNumber.concat(String.valueOf(number));
        return accountNumber;
    }
}
