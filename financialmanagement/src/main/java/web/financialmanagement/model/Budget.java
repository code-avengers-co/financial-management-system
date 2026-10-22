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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public BigDecimal getLimitAmount() {
        return this.limitAmount;
    }

    public void setBudgetMonth(int budgetMonth) {
        this.budgetMonth = budgetMonth;
    }

    public int getBudgetMonth() {
        return this.budgetMonth;
    }

    public void setBudgetYear(int budgetYear) {
        this.budgetYear = budgetYear;
    }

    public int getBudgetYear() {
        return this.budgetYear;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return this.category;
    }
}
