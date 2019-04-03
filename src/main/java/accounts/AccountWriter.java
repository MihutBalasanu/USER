package accounts;

public class AccountWriter {

    public String displayAccountData(Account account){
        String str = account.getAccontNumber()+ " "+
                account.getUsername() + " " +
                account.getBalance() + " " +
                account.getAccountType();
        return str;
    }
}
