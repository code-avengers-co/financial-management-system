package web.financialmanagement.controller;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.financialmanagement.core.web.pagination.PageWrapper;
import web.financialmanagement.dto.category.CategoryCreateRequest;
import web.financialmanagement.dto.category.CategoryResponse;
import web.financialmanagement.dto.category.CategorySearchRequest;
import web.financialmanagement.service.CategoryService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import web.financialmanagement.dto.category.CategoryUpdateRequest;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String search(
            CategorySearchRequest request,
            Model model,
            @PageableDefault(size = 7)
            @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable,
            HttpServletRequest httpRequest,
            HtmxRequest htmxRequest) {
        Page<CategoryResponse> page = categoryService.search(request, pageable);
        model.addAttribute("page", new PageWrapper<>(page, httpRequest));

        return htmxRequest.isHtmxRequest() ? "categories/list :: conteudo" : "categories/list";
    }

    @GetMapping("/new")
    public String openCreateForm(CategoryCreateRequest request, HtmxRequest htmxRequest) {
        return htmxRequest.isHtmxRequest() ? "categories/create :: formulario" : "categories/create";
    }

    @GetMapping("/{id}/edit")
    public String openEditForm(@PathVariable Long id,
                            Model model,
                            HtmxRequest htmxRequest) {
        model.addAttribute("categoryId", id);
        model.addAttribute("categoryUpdateRequest", categoryService.findForEdit(id));

        return htmxRequest.isHtmxRequest() ? "categories/edit :: formulario" : "categories/edit";
    }

    @PostMapping
    public String create(@Valid CategoryCreateRequest request,
                         BindingResult result,
                         RedirectAttributes attributes,
                         HtmxRequest htmxRequest) {
        if (result.hasErrors()) {
            logger.info("Invalid category: {}", result.getFieldErrors());
            return htmxRequest.isHtmxRequest() ? "categories/create :: formulario" : "categories/create";
        }

        categoryService.save(request);
        attributes.addFlashAttribute("message", "Categoria cadastrada com sucesso");

        return "redirect:htmx:location:/categories";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id,
                        @Valid CategoryUpdateRequest request,
                        BindingResult result,
                        RedirectAttributes attributes,
                        HtmxRequest htmxRequest,
                        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categoryId", id);
            logger.info("Invalid category: {}", result.getFieldErrors());

            return htmxRequest.isHtmxRequest() ? "categories/edit :: formulario" : "categories/edit";
        }

        categoryService.update(id, request);
        attributes.addFlashAttribute("message", "Categoria atualizada com sucesso");

        return "redirect:htmx:location:/categories";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        categoryService.delete(id);

        attributes.addFlashAttribute("message", "Categoria removida com sucesso");

        return "redirect:htmx:location:/categories";
    }
}
