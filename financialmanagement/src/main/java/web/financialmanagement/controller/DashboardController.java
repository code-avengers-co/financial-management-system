package web.financialmanagement.controller;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.financialmanagement.dto.account.AccountResponse;
import web.financialmanagement.dto.budget.BudgetResponse;
import web.financialmanagement.dto.transaction.MonthOption;
import web.financialmanagement.service.AccountService;
import web.financialmanagement.service.BudgetService;
import web.financialmanagement.service.TransactionService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final BudgetService budgetService;

    public DashboardController(AccountService accountService, TransactionService transactionService, BudgetService budgetService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.budgetService = budgetService;
    }

    @GetMapping("/")
    public String dashboard(Model model, HtmxRequest htmxRequest) {
        LocalDate today = LocalDate.now();
        List<AccountResponse> accounts = accountService.findAllActive();

        model.addAttribute("period", MonthOption.nameOf(today.getMonthValue()) + " de " + today.getYear());
        model.addAttribute("summary", transactionService.summarizeMonth(today.getMonthValue(), today.getYear()));
        model.addAttribute("accounts", accounts);
        model.addAttribute("totalBalance", accountService.calculateTotalBalance(accounts));

        List<BudgetResponse> budgets =
                budgetService.findByMonthAndYear(today.getMonthValue(), today.getYear());
                System.out.println("TOTAL DE ORÇAMENTOS: " + budgets.size());

        for (BudgetResponse budget : budgets) {
            System.out.println(
                budget.categoryName() + " - " + budget.budgetMonth() + "/" + budget.budgetYear()
            );
        }

        Map<Long, BigDecimal> budgetExpenses = new HashMap<>();

        for (BudgetResponse budget : budgets) {
            budgetExpenses.put(
                    budget.categoryId(),
                    transactionService.calculateTotalExpenses(
                            budget.categoryId(), today.getMonthValue(), today.getYear())
            );
        }

        model.addAttribute("budgets", budgets);
        model.addAttribute("budgetExpenses", budgetExpenses);

        return htmxRequest.isHtmxRequest() ? "index :: conteudo" : "index";
    }
}
