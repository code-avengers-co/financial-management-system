package web.financialmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.financialmanagement.model.Account;
import web.financialmanagement.model.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByAccount(Account account);
}
