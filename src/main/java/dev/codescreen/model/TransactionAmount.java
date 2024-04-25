package dev.codescreen.model;

public class TransactionAmount {
    private double amount;
    private String currency;
    private String debitOrCredit; // "CREDIT" or "DEBIT"

    // Constructors
    public TransactionAmount() {
    }

    public TransactionAmount(double amount, String currency, String debitOrCredit) {
        this.amount = amount;
        this.currency = currency;
        this.debitOrCredit = debitOrCredit;
    }

    // Getters and Setters
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDebitOrCredit() {
        return debitOrCredit;
    }

    public void setDebitOrCredit(String debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }
}
