package dev.codescreen.service;

import dev.codescreen.model.*;
import dev.codescreen.repository.AccountRepository;
import dev.codescreen.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    // Example of deposit (could be similar for withdrawal but negative amount)
    public Transaction performDeposit(String accountNumber, double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null){
            return null;
        }
        boolean success = account != null;
        if (success) {
            account.setBalance(account.getBalance() + amount);
            accountRepository.save(account);
        }
        return recordTransaction("EXTERNAL_SOURCE", accountNumber, amount, LocalDateTime.now(), success);
    }

    // General method to record transactions
    private Transaction recordTransaction(String originAccount, String targetAccount, double amount, LocalDateTime transactionDate, boolean successful) {
        Transaction transaction = new Transaction();
        transaction.setOriginAccount(originAccount);
        transaction.setTargetAccount(targetAccount);
        transaction.setAmount(amount);
        transaction.setTransactionDate(transactionDate);
        transaction.setSuccessful(successful);
        return transactionRepository.save(transaction);
    }
}
