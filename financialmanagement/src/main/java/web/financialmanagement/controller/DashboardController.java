package web.financialmanagement.controller;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.financialmanagement.dto.account.AccountResponse;
import web.financialmanagement.dto.transaction.MonthOption;
import web.financialmanagement.service.AccountService;
import web.financialmanagement.service.TransactionService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class DashboardController {
    private final AccountService accountService;
    private final TransactionService transactionService;

    public DashboardController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public String dashboard(Model model, HtmxRequest htmxRequest) {
        LocalDate today = LocalDate.now();
        List<AccountResponse> accounts = accountService.findAllActive();

        model.addAttribute("period", MonthOption.nameOf(today.getMonthValue()) + " de " + today.getYear());
        model.addAttribute("summary", transactionService.summarizeMonth(today.getMonthValue(), today.getYear()));
        model.addAttribute("accounts", accounts);
        model.addAttribute("totalBalance", accountService.calculateTotalBalance(accounts));

        return htmxRequest.isHtmxRequest() ? "index :: conteudo" : "index";
    }
}
