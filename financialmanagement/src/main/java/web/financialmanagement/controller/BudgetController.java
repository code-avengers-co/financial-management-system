package web.financialmanagement.controller;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.financialmanagement.core.web.pagination.PageWrapper;
import web.financialmanagement.dto.budget.BudgetCreateRequest;
import web.financialmanagement.dto.budget.BudgetResponse;
import web.financialmanagement.dto.budget.BudgetSearchRequest;
import web.financialmanagement.dto.budget.BudgetUpdateRequest;
import web.financialmanagement.service.BudgetService;
import web.financialmanagement.service.CategoryService;

@Controller
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetService budgetService;
    private final CategoryService categoryService;

    public BudgetController(
            BudgetService budgetService,
            CategoryService categoryService) {
        this.budgetService = budgetService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String search(
            BudgetSearchRequest request,
            Model model,
            @PageableDefault(size = 7)
            @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable,
            HttpServletRequest httpRequest,
            HtmxRequest htmxRequest) {

        Page<BudgetResponse> page = budgetService.search(request, pageable);

        model.addAttribute("page", new PageWrapper<>(page, httpRequest));
        model.addAttribute("categories", categoryService.findAllActive());

        return htmxRequest.isHtmxRequest()
                ? "budgets/list :: conteudo"
                : "budgets/list";
    }

    @GetMapping("/new")
    public String openCreateForm(
            BudgetCreateRequest request,
            Model model,
            HtmxRequest htmxRequest) {

        model.addAttribute("categories", categoryService.findAllActive());

        return htmxRequest.isHtmxRequest()
                ? "budgets/create :: formulario"
                : "budgets/create";
    }

    @GetMapping("/{id}/edit")
    public String openEditForm(
            @PathVariable Long id,
            Model model,
            HtmxRequest htmxRequest) {

        BudgetResponse budget = budgetService.findForEdit(id);

        BudgetUpdateRequest request = new BudgetUpdateRequest(
                budget.limitAmount(),
                budget.budgetMonth(),
                budget.budgetYear(),
                budget.categoryId()
        );

        model.addAttribute("budgetUpdateRequest", request);
        model.addAttribute("budgetId", id);
        model.addAttribute("categories", categoryService.findAllActive());

        return htmxRequest.isHtmxRequest()
                ? "budgets/edit :: formulario"
                : "budgets/edit";
    }

    @PutMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @Valid BudgetUpdateRequest request,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            model.addAttribute("budgetId", id);
            model.addAttribute("categories", categoryService.findAllActive());
            return "budgets/edit";
        }

        try {
            budgetService.update(id, request);

            attributes.addFlashAttribute(
                    "message", "Orçamento atualizado com sucesso");

            return "redirect:htmx:location:/budgets";

        } catch (IllegalArgumentException e) {
            model.addAttribute("budgetId", id);
            model.addAttribute("categories", categoryService.findAllActive());
            model.addAttribute("errorMessage", e.getMessage());

            return "budgets/edit";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable Long id,
            RedirectAttributes attributes) {

        budgetService.delete(id);

        attributes.addFlashAttribute(
                "message", "Orçamento removido com sucesso");

        return "redirect:htmx:location:/budgets";
    }

    @PostMapping
    public String create(
            @Valid BudgetCreateRequest request,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAllActive());
            return "budgets/create";
        }

        try {
            budgetService.save(request);
            attributes.addFlashAttribute(
                    "message", "Orçamento cadastrado com sucesso");

            return "redirect:htmx:location:/budgets";

        } catch (IllegalArgumentException e) {
            model.addAttribute("categories", categoryService.findAllActive());
            model.addAttribute("errorMessage", e.getMessage());

            return "budgets/create";
        }
    }

}
