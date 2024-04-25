package dev.codescreen.controller;

import dev.codescreen.model.LoadRequest;
import dev.codescreen.model.AuthorizationRequest;
import dev.codescreen.model.TransactionResponse;
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

    @PostMapping("/load")
    public ResponseEntity<TransactionResponse> loadMoney(@RequestBody LoadRequest request) {
        TransactionResponse response = transactionService.processLoad(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authorize")
    public ResponseEntity<TransactionResponse> authorizeTransaction(@RequestBody AuthorizationRequest request) {
        TransactionResponse response = transactionService.processAuthorization(request);
        return ResponseEntity.ok(response);
    }
}
