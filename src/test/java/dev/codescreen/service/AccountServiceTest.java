package dev.codescreen.service;

import dev.codescreen.model.Account;
import dev.codescreen.repository.AccountRepository;
import dev.codescreen.security.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private AccountService accountService;

    @Test
    void testGetAccountByEmailFound() {
        Account mockAccount = new Account();
        when(accountRepository.findByEmail("user@example.com")).thenReturn(mockAccount);

        Account result = accountService.getAccountByEmail("user@example.com");

        assertNotNull(result);
        verify(accountRepository).findByEmail("user@example.com");
    }

    @Test
    void testGetAccountByEmailNotFound() {
        when(accountRepository.findByEmail("user@example.com")).thenReturn(null);

        Account result = accountService.getAccountByEmail("user@example.com");

        assertNull(result);
        verify(accountRepository).findByEmail("user@example.com");
    }

    @Test
    void testRegisterAccount() {
        Account account = new Account();
        account.setPassword("password123");
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = accountService.registerAccount(account);

        assertEquals("encodedPassword", result.getPassword());
        verify(passwordEncoder).encode("password123");
        verify(accountRepository).save(account);
    }

    @Test
    void testLoginSuccess() {
        Account account = new Account();
        account.setAccountNumber("12345");
        account.setPassword("encodedPassword");

        when(accountRepository.findByAccountNumber("12345")).thenReturn(account);
        when(passwordEncoder.matches("rawPassword", "encodedPassword")).thenReturn(true);
        when(jwtTokenUtil.generateToken("12345")).thenReturn("token");

        String result = accountService.login("12345", "rawPassword");

        assertEquals("token", result);
        verify(accountRepository).findByAccountNumber("12345");
        verify(passwordEncoder).matches("rawPassword", "encodedPassword");
    }

    @Test
    void testLoginFailureAccountNotFound() {
        when(accountRepository.findByAccountNumber("12345")).thenReturn(null);

        String result = accountService.login("12345", "rawPassword");

        assertEquals("Account number does not exist.", result);
        verify(accountRepository).findByAccountNumber("12345");
    }

    @Test
    void testLoginFailureIncorrectPassword() {
        Account account = new Account();
        account.setAccountNumber("12345");
        account.setPassword("encodedPassword");

        when(accountRepository.findByAccountNumber("12345")).thenReturn(account);
        when(passwordEncoder.matches("rawPassword", "encodedPassword")).thenReturn(false);

        String result = accountService.login("12345", "rawPassword");

        assertEquals("Password is incorrect.", result);
        verify(passwordEncoder).matches("rawPassword", "encodedPassword");
    }
}
