package web.financialmanagement.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.financialmanagement.dto.category.CategoryCreateRequest;
import web.financialmanagement.dto.category.CategoryResponse;
import web.financialmanagement.dto.category.CategorySearchRequest;
import web.financialmanagement.model.Category;
import web.financialmanagement.repository.CategoryRepository;
import web.financialmanagement.dto.category.CategoryUpdateRequest;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<CategoryResponse> search(CategorySearchRequest request, Pageable pageable) {
        if (request.isEmpty()) {
            return categoryRepository.findAllByActive(true, pageable)
                    .map(this::toResponse);
        }

        Category categoryFilter = toEntity(request);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        return categoryRepository.findAll(Example.of(categoryFilter, matcher), pageable)
                .map(this::toResponse);
    }

    @Transactional
    public void save(CategoryCreateRequest request) {
        categoryRepository.save(toEntity(request));
    }

    public CategoryResponse findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow();

        return toResponse(category);
    }

    @Transactional
    public void update(Long id, CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow();

        category.setName(request.name());

        categoryRepository.save(category);
    }

    private Category toEntity(CategoryCreateRequest request) {
        Category category = new Category();
        category.setName(request.name());
        category.setActive(true);
        return category;
    }

    private Category toEntity(CategorySearchRequest request) {
        Category category = new Category();
        category.setId(request.id());
        category.setName(request.name());
        category.setActive(true);
        return category;
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
