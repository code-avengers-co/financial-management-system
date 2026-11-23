package web.financialmanagement.dto.budget;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BudgetUpdateRequest(
    @NotNull(message = "O limite é obrigatório")
        @DecimalMin(value = "0.01", message = "O limite deve ser maior que zero")
        BigDecimal limitAmount,

        @NotNull(message = "O mês é obrigatório")
        @Min(value = 1, message = "O mês deve estar entre 1 e 12")
        @Max(value = 12, message = "O mês deve estar entre 1 e 12")
        Integer budgetMonth,

        @NotNull(message = "O ano é obrigatório")
        @Min(value = 2000, message = "Informe um ano válido")
        Integer budgetYear,

        @NotNull(message = "A categoria é obrigatória")
        Long categoryId

) {

}
