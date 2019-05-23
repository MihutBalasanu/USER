package exceptions;

public class MoneyTransferException extends Exception{
    public MoneyTransferException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
