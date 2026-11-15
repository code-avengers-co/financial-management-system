package web.financialmanagement.dto.transaction;

public record TransactionSearchRequest(Integer month, Integer year, Long accountId) {
}
