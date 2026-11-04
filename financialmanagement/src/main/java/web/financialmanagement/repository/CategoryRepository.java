package web.financialmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import web.financialmanagement.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAllByActive(boolean active, Pageable pageable);
}
