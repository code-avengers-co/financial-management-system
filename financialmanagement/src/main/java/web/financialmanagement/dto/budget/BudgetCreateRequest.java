package web.financialmanagement.dto.budget;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BudgetCreateRequest(

        @NotNull(message = "The limit is mandatory")
        @DecimalMin(value = "0.01", message = "The limit must be greater then 0")
        BigDecimal limitAmount,

        @NotNull(message = "The month is mandatory")
        @Min(value = 1, message = "The month mus be between 1 and 12")
        @Max(value = 12, message = "The month mus be between 1 and 12")
        Integer budgetMonth,

        @NotNull(message = "The year is mandatory")
        @Min(value = 2000, message = "Enter a valid year")
        Integer budgetYear,

        @NotNull(message = "Enter a category")
        Long categoryId

) {}
