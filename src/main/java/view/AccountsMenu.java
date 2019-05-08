package view;

import model.Account;
import service.AccountService;
import utils.MoneyTransferException;
import utils.SessionUtil;
import java.util.logging.Logger;

public class AccountsMenu extends AbstractMenu{

    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());

    public AccountsMenu() {
    }

    @Override
    protected void displayOptions() {
        System.out.println("\nACCOUNTS MENU");
        System.out.println("1 - Create account");
        System.out.println("2 - Make payments");
        System.out.println("0 - Logout");

    }

    @Override
    protected void executeOption(Integer option) {
        AccountService accountService = new AccountService();
        switch (option) {
            case 1:
                accountService.createAccount();
                break;
            case 2:
                try {
                    accountService.makePayment(SessionUtil.user);
                } catch (MoneyTransferException err) {
                    LOGGER.warning(err.getMessage());
                    LOGGER.warning("Payment not completed!");
                }
                break;
            case 0:
                SessionUtil.user = null;
                LOGGER.info("Successfully logout...");
                break;
            default:
                LOGGER.warning("Invalid option!");
                break;
        }
    }
}
