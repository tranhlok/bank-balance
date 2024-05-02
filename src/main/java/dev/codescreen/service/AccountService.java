package dev.codescreen.service;

import dev.codescreen.model.Account;
import dev.codescreen.repository.AccountRepository;
import dev.codescreen.security.JwtTokenUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;



@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Secure key generation

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public Account getAccountByEmail(String email) {
        Account account = accountRepository.findByEmail(email);
        if(account == null){
            return null;
        }
        return account;

    }

    public Account getAccountByAccountNumber(String accountNumber){
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if(account == null){
            return null;
        }
        return account;
    }

    public Account registerAccount(Account account) {
        // Generate account number
        account.generateAccountNumber(); // This assumes you have moved the logic into a callable method within the Account entity.

        // Encode and set the password
        String encodedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);

        // Save the account details
        return accountRepository.save(account);
    }
    public String login(String accountNumber, String password) {
        System.out.println("account: "+accountNumber);
        System.out.println("password:" + password);
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            return "Account number does not exist.";
        }

        if (!passwordEncoder.matches(password, account.getPassword())) {
            return "Password is incorrect.";
        }
        System.out.println("authenticated");
        String token = jwtTokenUtil.generateToken(accountNumber);
        System.out.println("token:"+token);
        // Generate token
        return token;
    }

    private String generateToken(Account account) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + 3600000); // Token is valid for 1 hour

        return Jwts.builder()
                .setSubject(account.getAccountNumber())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }



    public void saveAccount(Account account) {
        if (account.getAccountNumber() == null || account.getAccountNumber().isEmpty()) {
            account.generateAccountNumber(); // Ensure account number is set before saving
        }
        accountRepository.save(account);
    }
}
