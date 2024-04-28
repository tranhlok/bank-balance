package dev.codescreen.service;

import dev.codescreen.model.Account;
import dev.codescreen.model.User;
import dev.codescreen.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AccountService accountService, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByAccountNumber(String account_no){
        return userRepository.findByAccountAccountNumber(account_no);
    }
    @Transactional

    public User registerUser(User user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Save the user to ensure it has an ID for linking
        User savedUser = userRepository.save(user);

        // Create an account for the user
        Account account = accountService.createAccount(savedUser);
        account.setUser(savedUser);  // Ensure back reference is set!

        savedUser.setAccount(account);

        // It might be better to save the user again after setting the account
        // if the relationship from user to account is not automatically persisted
        return userRepository.save(savedUser);

    }


    public void saveUser(User user) {
        userRepository.save(user);

    }

}
