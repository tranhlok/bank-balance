package dev.codescreen.service;

import dev.codescreen.model.Account;
import dev.codescreen.model.Transaction;
import dev.codescreen.repository.AccountRepository;
import dev.codescreen.repository.TransactionRepository;
import dev.codescreen.security.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private TransactionService transactionService;

    private final String token = "Bearer 1234567";
    private final String accountNumber = "10001";

    @BeforeEach
    void setup() {
        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtTokenUtil.getAccountFromToken(token.substring(7))).thenReturn(accountNumber);
    }

    @Test
    void testProcessWithdrawalSuccess() {
        Transaction transaction = new Transaction();
        transaction.setAmount(100.00);
        Account account = new Account();
        account.setBalance(200.00);

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(account);
        when(transactionRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        String result = transactionService.processWithdrawal(request, transaction);

        assertEquals("Withdrawal successful", result);
        assertEquals(100.00, account.getBalance());
    }

    @Test
    void testProcessWithdrawalInsufficientFunds() {
        Transaction transaction = new Transaction();
        transaction.setAmount(300.00);
        Account account = new Account();
        account.setBalance(200.00);

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(account);

        String result = transactionService.processWithdrawal(request, transaction);

        assertEquals("Insufficient funds", result);
        assertEquals(200.00, account.getBalance()); // Balance should not change
    }

    @Test
    void testProcessDepositSuccess() {
        Transaction transaction = new Transaction();
        transaction.setAmount(100.00);
        Account account = new Account();
        account.setBalance(200.00);

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(account);
        when(transactionRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        String result = transactionService.processDeposit(request, transaction);

        assertEquals("Deposit successful", result);
        assertEquals(300.00, account.getBalance());
    }
}
