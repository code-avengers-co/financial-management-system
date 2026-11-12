package web.financialmanagement.service;

import org.springframework.stereotype.Service;
import web.financialmanagement.common.exception.EntidadeNaoEncontradaException;
import web.financialmanagement.model.User;
import web.financialmanagement.repository.UserRepository;

@Service
public class UserService {
    private static final String TEST_USER_EMAIL = "usuario@teste.com";

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findCurrentUser() {
        return userRepository.findByEmailAndActive(TEST_USER_EMAIL, true)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
    }
}
