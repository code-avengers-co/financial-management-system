package web.financialmanagement.dto.category;

public record CategorySearchRequest(Long id, String name) {
    public boolean isEmpty(){
        return id == null && (name == null || name.isBlank());
    }
}
