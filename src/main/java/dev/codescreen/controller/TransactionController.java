package dev.codescreen.controller;

import dev.codescreen.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PutMapping("/load/{messageId}")
    public ResponseEntity<?> loadMoney(
            @PathVariable String messageId, @RequestBody LoadRequest request) {
        return null;
    }

    @PutMapping("/authorize/{messageId}")
    public ResponseEntity<?> authorizeTransaction(
            @PathVariable String messageId, @RequestBody AuthorizationRequest request) {
        return null;
    }
}
