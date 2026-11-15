package web.financialmanagement.dto.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import web.financialmanagement.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionUpdateRequest(
        @NotNull(message = "A conta é obrigatória") Long accountId,
        @NotNull(message = "A categoria é obrigatória") Long categoryId,
        @NotNull(message = "O tipo é obrigatório") TransactionType type,
        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor deve ser maior que zero") BigDecimal amount,
        @NotNull(message = "A data é obrigatória") LocalDate transactionDate,
        String description) {
}
