package dev.codescreen.service;

import dev.codescreen.model.LoadRequest;
import dev.codescreen.model.AuthorizationRequest;
import dev.codescreen.model.TransactionResponse;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    public TransactionResponse processLoad(LoadRequest request) {
        // Simulate processing a load transaction
        // Here, you would update the account balance and create transaction records
        return new TransactionResponse(request.getUserId(), "Processed", request.getTransactionAmount());
    }

    public TransactionResponse processAuthorization(AuthorizationRequest request) {
        // Simulate processing an authorization transaction
        // Check if the user has enough balance, then update accordingly
        // For now, we assume the transaction is always approved
        return new TransactionResponse(request.getUserId(), "Approved", request.getTransactionAmount());
    }
}
