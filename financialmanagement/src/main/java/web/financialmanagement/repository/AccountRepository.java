package web.financialmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import web.financialmanagement.model.Account;
import web.financialmanagement.model.User;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Page<Account> findAllByUserAndActive(User user, boolean active, Pageable pageable);
    Page<Account> findAllByUserAndActiveAndNameContainingIgnoreCase(User user, boolean active, String name, Pageable pageable);
    List<Account> findAllByUserAndActiveOrderByNameAsc(User user, boolean active);
    Optional<Account> findByIdAndUserAndActive(Long id, User user, boolean active);
}
