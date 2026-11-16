package web.financialmanagement.dto.transaction;

import java.math.BigDecimal;

public record MonthSummaryResponse(BigDecimal totalIncome, BigDecimal totalExpense, BigDecimal balance) {
}
