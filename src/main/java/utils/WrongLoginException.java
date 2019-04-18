package utils;

/**
 * Custom exception for wrong username/password at login.
 */
public class WrongLoginException extends Exception {

    /**
     * Constructor with parameter
     * @param message The message to be displayed when is thrown an exception for wrong login
     */
    public WrongLoginException(String message){
        super(message);
    }
}
