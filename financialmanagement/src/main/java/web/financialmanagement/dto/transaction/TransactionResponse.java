package web.financialmanagement.dto.transaction;

import web.financialmanagement.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(Long id, LocalDate transactionDate, String description, String accountName,
                                  String categoryName, TransactionType type, BigDecimal amount) {
}
