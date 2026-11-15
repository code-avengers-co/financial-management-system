package web.financialmanagement.dto.account;

public record AccountSearchRequest(String name) {
    public boolean isEmpty() {
        return name == null || name.isBlank();
    }
}
