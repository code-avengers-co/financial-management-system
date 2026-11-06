package web.financialmanagement.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateRequest(@NotBlank(message = "O nome é obrigatório") String name) {
}
