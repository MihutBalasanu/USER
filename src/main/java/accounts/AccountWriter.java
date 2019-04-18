package accounts;

/**
 * Sets the format to display an account data
 * @author Mihut Balasanu
 */
public class AccountWriter {

    /**
     * Sets the format to display an account data
     * @param account Instance of Account
     * @return A string containing the format to display the account data
     */
    public String displayAccountData(Account account){
        String str = account.getAccontNumber()+ " "+
                account.getUsername() + " " +
                account.getBalance() + " " +
                account.getAccountType();
        return str;
    }
}
