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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.financialmanagement.core.web.pagination.PageWrapper;
import web.financialmanagement.dto.account.AccountCreateRequest;
import web.financialmanagement.dto.account.AccountResponse;
import web.financialmanagement.dto.account.AccountSearchRequest;
import web.financialmanagement.service.AccountService;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String search(
            AccountSearchRequest request,
            Model model,
            @PageableDefault(size = 7)
            @SortDefault(sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable,
            HttpServletRequest httpRequest,
            HtmxRequest htmxRequest) {
        Page<AccountResponse> page = accountService.search(request, pageable);
        model.addAttribute("page", new PageWrapper<>(page, httpRequest));

        return htmxRequest.isHtmxRequest() ? "accounts/list :: conteudo" : "accounts/list";
    }

    @GetMapping("/new")
    public String openCreateForm(AccountCreateRequest request, HtmxRequest htmxRequest) {
        return htmxRequest.isHtmxRequest() ? "accounts/create :: formulario" : "accounts/create";
    }

    @PostMapping
    public String create(@Valid AccountCreateRequest request,
                         BindingResult result,
                         RedirectAttributes attributes,
                         HtmxRequest htmxRequest) {
        if (result.hasErrors()) {
            logger.info("Invalid account: {}", result.getFieldErrors());
            return htmxRequest.isHtmxRequest() ? "accounts/create :: formulario" : "accounts/create";
        }

        accountService.save(request);
        attributes.addFlashAttribute("message", "Conta cadastrada com sucesso");

        return "redirect:htmx:location:/accounts";
    }
}
