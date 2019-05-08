package utils;

public class AccountValidator {
    public static boolean validateId(String accountID) {
        if(accountID.length() != Constants.ACCOUNT_NAME_LENGTH) {
            return false;
        }

        if(!accountID.substring(0, 2).equals("RO")) {
            return false;
        }
        return true;
    }
}
