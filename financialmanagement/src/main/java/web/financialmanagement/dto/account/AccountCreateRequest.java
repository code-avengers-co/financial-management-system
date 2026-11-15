package web.financialmanagement.dto.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountCreateRequest(
        @NotBlank(message = "O nome é obrigatório") String name,
        @NotNull(message = "O saldo inicial é obrigatório") BigDecimal initialBalance) {
}
