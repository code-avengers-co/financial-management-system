package web.financialmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import web.financialmanagement.model.Account;
import web.financialmanagement.model.Category;
import web.financialmanagement.model.Transaction;
import web.financialmanagement.model.TransactionType;
import web.financialmanagement.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByAccount(Account account);

    Page<Transaction> findAllByAccountUserAndAccountActiveAndTransactionDateBetween(
            User user, boolean active, LocalDate start, LocalDate end, Pageable pageable);

    Page<Transaction> findAllByAccountAndTransactionDateBetween(
            Account account, LocalDate start, LocalDate end, Pageable pageable);

    List<Transaction> findAllByAccountUserAndAccountActiveAndCategoryAndTypeAndTransactionDateBetween(
            User user, boolean active, Category category, TransactionType type, LocalDate start, LocalDate end);

    Optional<Transaction> findByIdAndAccountUser(Long id, User user);
}
