package dev.codescreen.model;



public class TransactionAmount {
    private String amount; // Changed from double to String to match the schema
    private String currency;
    private DebitCredit debitOrCredit; // Use the enum
    public enum DebitCredit {
        DEBIT, CREDIT
    }
    // Constructors
    public TransactionAmount() {
    }

    public TransactionAmount(String amount, String currency, DebitCredit debitOrCredit) {
        this.amount = amount;
        this.currency = currency;
        this.debitOrCredit = debitOrCredit;
    }

    // Getters and Setters
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public DebitCredit getDebitOrCredit() {
        return debitOrCredit;
    }

    public void setDebitOrCredit(DebitCredit debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }
}
