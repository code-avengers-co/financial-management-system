package web.financialmanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.financialmanagement.common.exception.EntidadeNaoEncontradaException;
import web.financialmanagement.dto.transaction.TransactionCreateRequest;
import web.financialmanagement.dto.transaction.TransactionResponse;
import web.financialmanagement.dto.transaction.TransactionSearchRequest;
import web.financialmanagement.dto.transaction.TransactionUpdateRequest;
import web.financialmanagement.model.Account;
import web.financialmanagement.model.Category;
import web.financialmanagement.model.Transaction;
import web.financialmanagement.model.TransactionType;
import web.financialmanagement.model.User;
import web.financialmanagement.repository.AccountRepository;
import web.financialmanagement.repository.CategoryRepository;
import web.financialmanagement.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository,
                              CategoryRepository categoryRepository,
                              UserService userService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public Page<TransactionResponse> search(TransactionSearchRequest request, Pageable pageable) {
        User user = userService.findCurrentUser();
        YearMonth period = YearMonth.of(request.year(), request.month());

        if (request.accountId() == null) {
            return transactionRepository.findAllByAccountUserAndAccountActiveAndTransactionDateBetween(
                            user, true, period.atDay(1), period.atEndOfMonth(), pageable)
                    .map(this::toResponse);
        }

        Account account = findAccount(request.accountId(), user);
        return transactionRepository.findAllByAccountAndTransactionDateBetween(
                        account, period.atDay(1), period.atEndOfMonth(), pageable)
                .map(this::toResponse);
    }

    @Transactional
    public void save(TransactionCreateRequest request) {
        User user = userService.findCurrentUser();

        Transaction transaction = new Transaction();
        transaction.setAccount(findAccount(request.accountId(), user));
        transaction.setCategory(findCategory(request.categoryId()));
        transaction.setType(request.type());
        transaction.setAmount(request.amount());
        transaction.setTransactionDate(request.transactionDate());
        transaction.setDescription(request.description());

        transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public TransactionUpdateRequest findForEdit(Long id) {
        return toUpdateRequest(findTransaction(id, userService.findCurrentUser()));
    }

    @Transactional
    public void update(Long id, TransactionUpdateRequest request) {
        User user = userService.findCurrentUser();
        Transaction transaction = findTransaction(id, user);

        transaction.setAccount(findAccount(request.accountId(), user));
        transaction.setCategory(findCategory(request.categoryId()));
        transaction.setType(request.type());
        transaction.setAmount(request.amount());
        transaction.setTransactionDate(request.transactionDate());
        transaction.setDescription(request.description());

        transactionRepository.save(transaction);
    }

    @Transactional
    public void delete(Long id) {
        Transaction transaction = findTransaction(id, userService.findCurrentUser());

        transactionRepository.delete(transaction);
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateTotalExpenses(Category category, int month, int year) {
        YearMonth period = YearMonth.of(year, month);
        List<Transaction> expenses = transactionRepository
                .findAllByAccountUserAndAccountActiveAndCategoryAndTypeAndTransactionDateBetween(
                        userService.findCurrentUser(), true, category, TransactionType.EXPENSE,
                        period.atDay(1), period.atEndOfMonth());

        return sumAmounts(expenses);
    }

    private BigDecimal sumAmounts(List<Transaction> transactions) {
        BigDecimal total = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            total = total.add(transaction.getAmount());
        }

        return total;
    }

    private Transaction findTransaction(Long id, User user) {
        return transactionRepository.findByIdAndAccountUser(id, user)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Lançamento não encontrado"));
    }

    private Account findAccount(Long id, User user) {
        return accountRepository.findByIdAndUserAndActive(id, user, true)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Conta não encontrada"));
    }

    private Category findCategory(Long id) {
        return categoryRepository.findByIdAndActive(id, true)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Categoria não encontrada"));
    }

    private TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(transaction.getId(), transaction.getTransactionDate(),
                transaction.getDescription(), transaction.getAccount().getName(),
                transaction.getCategory().getName(), transaction.getType(), transaction.getAmount());
    }

    private TransactionUpdateRequest toUpdateRequest(Transaction transaction) {
        return new TransactionUpdateRequest(transaction.getAccount().getId(), transaction.getCategory().getId(),
                transaction.getType(), transaction.getAmount(), transaction.getTransactionDate(),
                transaction.getDescription());
    }
}
