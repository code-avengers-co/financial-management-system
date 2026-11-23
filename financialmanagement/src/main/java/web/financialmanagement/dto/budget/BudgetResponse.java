package web.financialmanagement.dto.budget;

import java.math.BigDecimal;

public record BudgetResponse(
        Long id,
        BigDecimal limitAmount,
        Integer budgetMonth,
        Integer budgetYear,
        Long categoryId,
        String categoryName,
        BigDecimal spentAmount,
        int percentage,
        BudgetStatus status
) {

}
