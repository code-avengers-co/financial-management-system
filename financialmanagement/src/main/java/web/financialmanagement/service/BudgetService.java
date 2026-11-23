package web.financialmanagement.service;

import web.financialmanagement.repository.BudgetRepository;
import web.financialmanagement.repository.CategoryRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web.financialmanagement.common.exception.EntidadeNaoEncontradaException;
import web.financialmanagement.dto.budget.BudgetCreateRequest;
import web.financialmanagement.dto.budget.BudgetResponse;
import web.financialmanagement.dto.budget.BudgetSearchRequest;
import web.financialmanagement.dto.budget.BudgetStatus;
import web.financialmanagement.dto.budget.BudgetUpdateRequest;
import web.financialmanagement.model.Budget;
import web.financialmanagement.model.User;
import web.financialmanagement.model.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service 
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final TransactionService transactionService;

    public BudgetService(
            BudgetRepository budgetRepository,
            CategoryRepository categoryRepository,
            UserService userService,
            TransactionService transactionService) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.transactionService = transactionService;
    }


    @Transactional(readOnly = true)
    public Page<BudgetResponse> search(BudgetSearchRequest request, Pageable pageable) {

        User user = userService.findCurrentUser();

        if (request.isEmpty()) {
            return budgetRepository.findAllByUser(user, pageable)
                    .map(this::toResponse);
        }

        if (request.categoryId() != null
                && request.budgetMonth() != null
                && request.budgetYear() != null) {

            Category category = findCategory(request.categoryId());

            return budgetRepository.findAllByUserAndCategoryAndBudgetMonthAndBudgetYear(
                    user, category, request.budgetMonth(), request.budgetYear(), pageable)
                    .map(this::toResponse);
        }

        if (request.categoryId() != null && request.budgetMonth() != null) {

            Category category = findCategory(request.categoryId());

            return budgetRepository.findAllByUserAndCategoryAndBudgetMonth(
                    user, category, request.budgetMonth(), pageable)
                    .map(this::toResponse);
        }

        if (request.categoryId() != null && request.budgetYear() != null) {

            Category category = findCategory(request.categoryId());

            return budgetRepository.findAllByUserAndCategoryAndBudgetYear(
                    user, category, request.budgetYear(), pageable)
                    .map(this::toResponse);
        }

        if (request.budgetMonth() != null && request.budgetYear() != null) {
            return budgetRepository.findAllByUserAndBudgetMonthAndBudgetYear(
                    user, request.budgetMonth(), request.budgetYear(), pageable)
                    .map(this::toResponse);
        }

        if (request.categoryId() != null) {
            Category category = findCategory(request.categoryId());

            return budgetRepository.findAllByUserAndCategory(user, category, pageable)
                    .map(this::toResponse);
        }

        if (request.budgetMonth() != null) {
            return budgetRepository.findAllByUserAndBudgetMonth(
                    user, request.budgetMonth(), pageable)
                    .map(this::toResponse);
        }

        return budgetRepository.findAllByUserAndBudgetYear(
                user, request.budgetYear(), pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public java.util.List<BudgetResponse> findByMonthAndYear(int month, int year) {
        User user = userService.findCurrentUser();

        return budgetRepository
                .findAllByUserAndBudgetMonthAndBudgetYear(
                        user, month, year, org.springframework.data.domain.Pageable.unpaged())
                .map(this::toResponse)
                .getContent();
    }

    @Transactional(readOnly = true)
    public BudgetResponse findForEdit(Long id) {

        User user = userService.findCurrentUser();

        Budget budget = budgetRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Orçamento não encontrado"
                ));

        return toResponse(budget);
    }

    @Transactional
    public void update(Long id, BudgetUpdateRequest request) {

        User user = userService.findCurrentUser();

        Budget budget = budgetRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Orçamento não encontrado"
                ));

        Category category = findCategory(request.categoryId());

        boolean alreadyExists = budgetRepository
                .existsByUserAndCategoryAndBudgetMonthAndBudgetYearAndIdNot(
                        user,
                        category,
                        request.budgetMonth(),
                        request.budgetYear(),
                        id
                );

        if (alreadyExists) {
            throw new IllegalArgumentException(
                    "Já existe um orçamento para essa categoria neste mês e ano"
            );
        }

        budget.setCategory(category);
        budget.setLimitAmount(request.limitAmount());
        budget.setBudgetMonth(request.budgetMonth());
        budget.setBudgetYear(request.budgetYear());

        budgetRepository.save(budget);
    }

    @Transactional
    public void delete(Long id) {

        User user = userService.findCurrentUser();

        Budget budget = budgetRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Orçamento não encontrado"
                ));

        budgetRepository.delete(budget);
    }

    private Category findCategory(Long id) {
        return categoryRepository.findByIdAndActive(id, true)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Categoria não encontrada"));
    }


    @Transactional
    public void save(BudgetCreateRequest request) {

        User user = userService.findCurrentUser();

        Category category = findCategory(request.categoryId());

        boolean alreadyExists = budgetRepository
                .existsByUserAndCategoryAndBudgetMonthAndBudgetYear(
                        user,
                        category,
                        request.budgetMonth(),
                        request.budgetYear()
                );

        if (alreadyExists) {
            throw new IllegalArgumentException(
                    "Já existe um orçamento para essa categoria neste mês e ano"
            );
        }

        Budget budget = new Budget();

        budget.setUser(user);
        budget.setCategory(category);
        budget.setLimitAmount(request.limitAmount());
        budget.setBudgetMonth(request.budgetMonth());
        budget.setBudgetYear(request.budgetYear());

        budgetRepository.save(budget);
    }


    private BudgetResponse toResponse(Budget budget) {
        BigDecimal spent = transactionService.calculateTotalExpenses(
                budget.getCategory(), budget.getBudgetMonth(), budget.getBudgetYear());

        return new BudgetResponse(
                budget.getId(),
                budget.getLimitAmount(),
                budget.getBudgetMonth(),
                budget.getBudgetYear(),
                budget.getCategory().getId(),
                budget.getCategory().getName(),
                spent,
                calculatePercentage(spent, budget.getLimitAmount()),
                calculateStatus(spent, budget.getLimitAmount())
        );
    }

    private int calculatePercentage(BigDecimal spent, BigDecimal limit) {
        return spent.multiply(BigDecimal.valueOf(100))
                .divide(limit, 0, RoundingMode.DOWN)
                .intValue();
    }

    private BudgetStatus calculateStatus(BigDecimal spent, BigDecimal limit) {
        if (spent.compareTo(limit) > 0) {
            return BudgetStatus.EXCEEDED;
        }

        if (spent.multiply(BigDecimal.valueOf(100))
                .compareTo(limit.multiply(BigDecimal.valueOf(80))) >= 0) {
            return BudgetStatus.NEAR_LIMIT;
        }

        return BudgetStatus.WITHIN_LIMIT;
    }


}
