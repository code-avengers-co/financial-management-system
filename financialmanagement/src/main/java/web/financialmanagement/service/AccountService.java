package web.financialmanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.financialmanagement.dto.account.AccountCreateRequest;
import web.financialmanagement.dto.account.AccountResponse;
import web.financialmanagement.dto.account.AccountSearchRequest;
import web.financialmanagement.model.Account;
import web.financialmanagement.model.Transaction;
import web.financialmanagement.model.TransactionType;
import web.financialmanagement.model.User;
import web.financialmanagement.repository.AccountRepository;
import web.financialmanagement.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserService userService;

    public AccountService(AccountRepository accountRepository,
                          TransactionRepository transactionRepository,
                          UserService userService) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    public Page<AccountResponse> search(AccountSearchRequest request, Pageable pageable) {
        User user = userService.findCurrentUser();

        if (request.isEmpty()) {
            return accountRepository.findAllByUserAndActive(user, true, pageable)
                    .map(this::toResponse);
        }

        return accountRepository.findAllByUserAndActiveAndNameContainingIgnoreCase(user, true, request.name(), pageable)
                .map(this::toResponse);
    }

    public List<AccountResponse> findAllActive() {
        return accountRepository.findAllByUserAndActiveOrderByNameAsc(userService.findCurrentUser(), true)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void save(AccountCreateRequest request) {
        accountRepository.save(toEntity(request, userService.findCurrentUser()));
    }

    private BigDecimal calculateCurrentBalance(Account account) {
        BigDecimal balance = account.getInitialBalance();

        for (Transaction transaction : transactionRepository.findAllByAccount(account)) {
            if (transaction.getType() == TransactionType.INCOME) {
                balance = balance.add(transaction.getAmount());
            } else {
                balance = balance.subtract(transaction.getAmount());
            }
        }

        return balance;
    }

    private Account toEntity(AccountCreateRequest request, User user) {
        Account account = new Account();
        account.setName(request.name());
        account.setInitialBalance(request.initialBalance());
        account.setActive(true);
        account.setUser(user);
        return account;
    }

    private AccountResponse toResponse(Account account) {
        return new AccountResponse(account.getId(), account.getName(), account.getInitialBalance(),
                calculateCurrentBalance(account));
    }
}
