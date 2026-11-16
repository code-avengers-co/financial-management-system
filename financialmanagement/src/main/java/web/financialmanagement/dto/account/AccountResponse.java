package web.financialmanagement.dto.account;

import java.math.BigDecimal;

public record AccountResponse(Long id, String name, BigDecimal initialBalance, BigDecimal currentBalance) {
}
