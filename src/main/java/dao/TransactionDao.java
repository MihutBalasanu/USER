package dao;

import model.Transaction;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class TransactionDao extends GenericDao<Transaction> {
}
