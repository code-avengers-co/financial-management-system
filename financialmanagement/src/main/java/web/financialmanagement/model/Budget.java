package web.financialmanagement.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Budget implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private BigDecimal limitAmount;
    private int budgetMonth;
    private int budgetYear;
    private User user;
    private Category category;

    private void setId(Long id) {
        this.id = id;
    }

    private Long getId() {
        return this.id;
    }

    private void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    private BigDecimal getLimitAmount() {
        return this.limitAmount;
    }

    private void setBudgetMonth(int budgetMonth) {
        this.budgetMonth = budgetMonth;
    }

    private int getBudgetMonth() {
        return this.budgetMonth;
    }

    private void setBudgetYear(int budgetYear) {
        this.budgetYear = budgetYear;
    }

    private int getBudgetYear() {
        return this.budgetYear;
    }

    private void setUser(User user) {
        this.user = user;
    }

    private User getUser() {
        return this.user;
    }

    private void setCategory(Category category) {
        this.category = category;
    }

    private Category getCategory() {
        return this.category;
    }
}
