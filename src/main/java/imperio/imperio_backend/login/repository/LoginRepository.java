package imperio.imperio_backend.login.repository;

import imperio.imperio_backend.login.module.LoginModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<LoginModule, Long> {
    Optional<LoginModule> findByUsername(String username);
    Optional<LoginModule> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
