package dev.codescreen.repository;
import java.util.List;

import dev.codescreen.model.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByOriginAccountOrTargetAccount(String originAccount, String targetAccount);
}


