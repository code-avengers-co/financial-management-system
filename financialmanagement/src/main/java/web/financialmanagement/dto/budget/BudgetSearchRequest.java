package web.financialmanagement.dto.budget;

public record BudgetSearchRequest (
        Long categoryId,
        Integer budgetMonth,
        Integer budgetYear
){
    public boolean isEmpty() {
        return categoryId == null
                && budgetMonth == null
                && budgetYear == null;
    }
}
