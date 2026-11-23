package web.financialmanagement.dto.budget;

public enum BudgetStatus {
    WITHIN_LIMIT("Dentro do limite"),
    NEAR_LIMIT("Perto do limite"),
    EXCEEDED("Estourado");

    private final String description;

    BudgetStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
