package dev.codescreen.model;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String accountNumber; // New field for account number

    @Column(unique = true)
    private String email;

    private String name;

    @Column(length = 100)  // Suitable for storing encrypted passwords
    private String password;

    private double balance;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Method to generate a unique account number
    @PrePersist
    public void generateAccountNumber() {
        // This is a simple placeholder. Replace with your actual account number generation logic
        this.accountNumber = "ACCT-" + System.currentTimeMillis();
    }
}
