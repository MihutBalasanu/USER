package service;

import model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.TransactionRepository;

@Service("transactionService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class TransactionService {


    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction findById(Long id){
        return transactionRepository.findById(id);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteTransactionById(Long id){
        transactionRepository.delete(id);
    }

    public Transaction updateTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }
}
