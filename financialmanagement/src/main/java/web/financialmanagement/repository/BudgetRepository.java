package web.financialmanagement.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import web.financialmanagement.model.Budget;
import web.financialmanagement.model.Category;
import web.financialmanagement.model.User;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Page<Budget> findAllByUser(User user, Pageable pageable);

    Optional<Budget> findByIdAndUser(Long id, User user);

     boolean existsByUserAndCategoryAndBudgetMonthAndBudgetYear(
            User user, Category category, int budgetMonth, int budgetYear);

    boolean existsByUserAndCategoryAndBudgetMonthAndBudgetYearAndIdNot(
            User user, Category category, int budgetMonth, int budgetYear, Long id);
}
