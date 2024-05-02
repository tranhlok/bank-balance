package dev.codescreen.controller;

import dev.codescreen.dto.LoginRequest;
import dev.codescreen.dto.UserResponse;  // This may need to be renamed to AccountResponse based on your DTO structure
import dev.codescreen.model.Account;
import dev.codescreen.security.JwtTokenUtil;
import dev.codescreen.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;



    public AccountController(AccountService accountService) {
        this.accountService = accountService;

    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerAccount(@RequestBody Account account){

        Account registeredAccount = accountService.registerAccount(account);

        UserResponse userResponse = new UserResponse(); // Consider renaming this DTO to something like AccountResponse
        userResponse.setName(registeredAccount.getName());
        userResponse.setAccountNumber(registeredAccount.getAccountNumber()); // Assuming you store account number in a different way or use ID as account number.
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Use the AccountService to check credentials and generate a token
        String result = accountService.login(loginRequest.getAccountNumber(), loginRequest.getPassword());
        System.out.println("result" + result);
        // Check the result and respond accordingly
        if (result.startsWith("Account number does not exist") || result.startsWith("Password is incorrect")) {
            // If the login method returns an error string, return 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        } else {
            System.out.println("login successful");
            // If login successful, the result is a JWT token
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", result);
            return ResponseEntity.ok(tokenMap);
        }
    }

}
