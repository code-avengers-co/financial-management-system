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

    Page<Budget> findAllByUserAndCategory(
            User user, Category category, Pageable pageable);

    Page<Budget> findAllByUserAndBudgetMonth(
            User user, int budgetMonth, Pageable pageable);

    Page<Budget> findAllByUserAndBudgetYear(
            User user, int budgetYear, Pageable pageable);

    Page<Budget> findAllByUserAndCategoryAndBudgetMonth(
            User user, Category category, int budgetMonth, Pageable pageable);

    Page<Budget> findAllByUserAndCategoryAndBudgetYear(
            User user, Category category, int budgetYear, Pageable pageable);

    Page<Budget> findAllByUserAndBudgetMonthAndBudgetYear(
            User user, int budgetMonth, int budgetYear, Pageable pageable);

    Page<Budget> findAllByUserAndCategoryAndBudgetMonthAndBudgetYear(
            User user, Category category, int budgetMonth, int budgetYear, Pageable pageable);

}
