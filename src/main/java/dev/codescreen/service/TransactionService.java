package dev.codescreen.service;

import dev.codescreen.model.*;
import dev.codescreen.repository.AccountRepository;
import dev.codescreen.repository.TransactionRepository;
import dev.codescreen.security.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransactionService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String processWithdrawal(HttpServletRequest request, Transaction transaction) {
        String token = parseToken(request);
        String accountNumber = jwtTokenUtil.getAccountFromToken(token);
        double amount = transaction.getAmount();
        return processTransaction(accountNumber, amount, false);
    }
    private String parseToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null; // or throw an exception if the token is mandatory
    }

    public String processDeposit(HttpServletRequest request, Transaction transaction) {
        String token = parseToken(request);
        String accountNumber = jwtTokenUtil.getAccountFromToken(token);
        double amount = transaction.getAmount(); // Ensure Transaction object has an 'amount' field
        return processTransaction(accountNumber, amount, true);
    }


    @Transactional
    private String processTransaction(String accountNumber, double amount, boolean isDeposit) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            return "Account not found";
        }

        double newBalance = isDeposit ? account.getBalance() + amount : account.getBalance() - amount;
        if (!isDeposit && newBalance < 0) {
            Transaction transaction = new Transaction();
            transaction.setTargetAccount(accountNumber);
            transaction.setAmount(amount);
            transaction.setCurrency("USD");
            transaction.setTransactionDate(LocalDateTime.now());
            transaction.setSuccessful(false);  // Set transaction as unsuccessful
            transaction.setTransactionType("CREDIT");  // Assuming DEBIT is the correct type for failed withdrawals

            transactionRepository.save(transaction);

            return "Insufficient funds";
        }

        account.setBalance(newBalance);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setTargetAccount(accountNumber);
        transaction.setAmount(amount);
        transaction.setCurrency("USD");
        transaction.setTransactionDate(LocalDateTime.now());

        transaction.setSuccessful(true);
        transaction.setTransactionType(isDeposit ? "DEBIT": "CREDIT");  // Set type based on transaction kind

        transactionRepository.save(transaction);

        return isDeposit ? "Deposit successful" : "Withdrawal successful";
    }
}
