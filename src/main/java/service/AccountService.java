package service;

import dao.AccountDao;
import model.Account;
import model.User;
import java.util.List;

public class AccountService {

private AccountDao accountDao = new AccountDao();

    public Account findById(Long id){
        return accountDao.getEntityById(Account.class, id);
    }

    public List<Account> findAll(){
        return accountDao.findAll();
    }

    public List<Account> findUserAllAccount(User user){
        List<Account> userAllAccounts = null;
        List<Account> allAccounts = findAll();
        for(Account account: allAccounts){
            if(account.getUser().equals(user)){
                userAllAccounts.add(account);
            }
        }
        return userAllAccounts;
    }

    public void createAccount(Account account) {
        accountDao.createEntity(account);
    }

    public void deleteAccount(Account account){
        accountDao.deleteEntity(account);
    }

    public void updateAccount(Account account) {
        accountDao.updateEntity(account);
    }

}
