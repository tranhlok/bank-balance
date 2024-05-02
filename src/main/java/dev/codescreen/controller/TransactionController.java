package dev.codescreen.controller;

import dev.codescreen.model.Transaction;
import dev.codescreen.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/withdraw")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> withdraw(HttpServletRequest request, @RequestBody Transaction transaction) {
        System.out.println(transaction.toString());

        String result = transactionService.processWithdrawal(request, transaction);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/deposit")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> deposit(HttpServletRequest request, @RequestBody Transaction transaction) {
        System.out.println(transaction.toString());
        String result = transactionService.processDeposit(request, transaction);
        return ResponseEntity.ok(result);
    }
}
