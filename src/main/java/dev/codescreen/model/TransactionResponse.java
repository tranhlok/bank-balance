package dev.codescreen.model;

public class TransactionResponse {
    private String userId;
    private String status;
    private TransactionAmount transactionAmount;

    public TransactionResponse(String userId, String status, TransactionAmount transactionAmount) {
        this.userId = userId;
        this.status = status;
        this.transactionAmount = transactionAmount;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TransactionAmount getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(TransactionAmount transactionAmount) {
        this.transactionAmount = transactionAmount;
    }}

