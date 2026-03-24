package imperio.imperio_backend.customer_master.repository;

import imperio.imperio_backend.customer_master.module.CustomerMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerMasterRepository extends JpaRepository<CustomerMaster, Long> {
    Optional<CustomerMaster> findByCustomerCode(String customerCode);
    boolean existsByCustomerCode(String customerCode);
}
