package imperio.imperio_backend.stock_item_master.repository;

import imperio.imperio_backend.stock_item_master.module.StockItemMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StockItemMasterRepository extends JpaRepository<StockItemMaster,Long> {

    Optional<StockItemMaster> findByStockItemCode(String stockItemCode);

    boolean existsByStockItemCode(String stockItemCode);
}
