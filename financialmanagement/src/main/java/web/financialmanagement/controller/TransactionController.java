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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.financialmanagement.core.web.pagination.PageWrapper;
import web.financialmanagement.dto.transaction.MonthOption;
import web.financialmanagement.dto.transaction.TransactionCreateRequest;
import web.financialmanagement.dto.transaction.TransactionResponse;
import web.financialmanagement.dto.transaction.TransactionSearchRequest;
import web.financialmanagement.dto.transaction.TransactionUpdateRequest;
import web.financialmanagement.model.TransactionType;
import web.financialmanagement.service.AccountService;
import web.financialmanagement.service.CategoryService;
import web.financialmanagement.service.TransactionService;

import java.time.LocalDate;

@Controller
@RequestMapping("/transactions")
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final CategoryService categoryService;

    public TransactionController(TransactionService transactionService,
                                 AccountService accountService,
                                 CategoryService categoryService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String search(
            TransactionSearchRequest request,
            Model model,
            @PageableDefault(size = 7)
            @SortDefault(sort = "transactionDate", direction = Sort.Direction.DESC)
            Pageable pageable,
            HttpServletRequest httpRequest,
            HtmxRequest htmxRequest) {
        LocalDate today = LocalDate.now();
        boolean validMonth = request.month() != null && request.month() >= 1 && request.month() <= 12;
        TransactionSearchRequest filter = new TransactionSearchRequest(
                validMonth ? request.month() : today.getMonthValue(),
                request.year() != null ? request.year() : today.getYear(),
                request.accountId());

        Page<TransactionResponse> page = transactionService.search(filter, pageable);
        model.addAttribute("transactionSearchRequest", filter);
        model.addAttribute("months", MonthOption.all());
        model.addAttribute("accounts", accountService.findAllActive());
        model.addAttribute("page", new PageWrapper<>(page, httpRequest));

        return htmxRequest.isHtmxRequest() ? "transactions/list :: conteudo" : "transactions/list";
    }

    @GetMapping("/new")
    public String openCreateForm(Model model, HtmxRequest htmxRequest) {
        model.addAttribute("transactionCreateRequest",
                new TransactionCreateRequest(null, null, TransactionType.EXPENSE, null, LocalDate.now(), null));
        addFormOptions(model);

        return htmxRequest.isHtmxRequest() ? "transactions/create :: formulario" : "transactions/create";
    }

    @PostMapping
    public String create(@Valid TransactionCreateRequest request,
                         BindingResult result,
                         RedirectAttributes attributes,
                         HtmxRequest htmxRequest,
                         Model model) {
        if (result.hasErrors()) {
            logger.info("Invalid transaction: {}", result.getFieldErrors());
            addFormOptions(model);
            return htmxRequest.isHtmxRequest() ? "transactions/create :: formulario" : "transactions/create";
        }

        transactionService.save(request);
        attributes.addFlashAttribute("message", "Lançamento cadastrado com sucesso");

        return "redirect:htmx:location:/transactions";
    }

    @GetMapping("/{id}/edit")
    public String openEditForm(@PathVariable Long id, Model model, HtmxRequest htmxRequest) {
        model.addAttribute("transactionId", id);
        model.addAttribute("transactionUpdateRequest", transactionService.findForEdit(id));
        addFormOptions(model);

        return htmxRequest.isHtmxRequest() ? "transactions/edit :: formulario" : "transactions/edit";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid TransactionUpdateRequest request,
                         BindingResult result,
                         RedirectAttributes attributes,
                         HtmxRequest htmxRequest,
                         Model model) {
        if (result.hasErrors()) {
            logger.info("Invalid transaction: {}", result.getFieldErrors());
            model.addAttribute("transactionId", id);
            addFormOptions(model);
            return htmxRequest.isHtmxRequest() ? "transactions/edit :: formulario" : "transactions/edit";
        }

        transactionService.update(id, request);
        attributes.addFlashAttribute("message", "Lançamento alterado com sucesso");

        return "redirect:htmx:location:/transactions";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        transactionService.delete(id);
        attributes.addFlashAttribute("message", "Lançamento removido com sucesso");

        return "redirect:htmx:location:/transactions";
    }

    private void addFormOptions(Model model) {
        model.addAttribute("accounts", accountService.findAllActive());
        model.addAttribute("categories", categoryService.findAllActive());
        model.addAttribute("types", TransactionType.values());
    }
}
