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

    @PutMapping("/load/{messageId}")
    public ResponseEntity<TransactionResponse> loadMoney(
            @PathVariable String messageId, @RequestBody LoadRequest request) {
        // Pass both messageId and request to the service method
        TransactionResponse response = transactionService.processLoad(messageId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/authorize/{messageId}")
    public ResponseEntity<TransactionResponse> authorizeTransaction(
            @PathVariable String messageId, @RequestBody AuthorizationRequest request) {
        // Pass both messageId and request to the service method
        TransactionResponse response = transactionService.processAuthorization(messageId, request);
        return ResponseEntity.ok(response);
    }
}
