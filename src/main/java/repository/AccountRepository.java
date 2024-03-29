package repository;

import model.Account;

public interface AccountRepository extends ModelRepository<Account> {

    Account findByAccountNumber(String accountNumber);
}
