package dev.codescreen.repository;

import dev.codescreen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByAccountAccountNumber(String accountNumber);
}
