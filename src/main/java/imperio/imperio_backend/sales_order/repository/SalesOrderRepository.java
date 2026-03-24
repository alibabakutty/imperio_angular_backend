package imperio.imperio_backend.sales_order.repository;

import imperio.imperio_backend.sales_order.module.SalesOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrderEntity, Long> {

    // find all line items associated with a specific order number
    List<SalesOrderEntity> findByOrderNumber(String orderNumber);
    // checks if order number exists in the system
    boolean existsByOrderNumber(String orderNumber);
    // calculates the grand total of an entire order by summing up
    BigDecimal getGrandTotalByOrderNumber(@Param("orderNumber") String orderNumber);
    // finds all orders placed by a specific ledger/customer
    List<SalesOrderEntity> findByLedgerNameContainingIgnoreCase(String ledgerName);
}

