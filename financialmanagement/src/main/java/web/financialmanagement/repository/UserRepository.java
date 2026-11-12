package web.financialmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.financialmanagement.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndActive(String email, boolean active);
}
