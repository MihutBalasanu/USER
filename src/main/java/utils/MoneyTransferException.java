package utils;

public class MoneyTransferException extends Exception{
    public MoneyTransferException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
