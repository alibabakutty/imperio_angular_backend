package imperio.imperio_backend.stock_item_master.service;

import imperio.imperio_backend.stock_item_master.dto.StockItemRequest;
import imperio.imperio_backend.stock_item_master.dto.StockItemResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StockItemMasterService {
    @Transactional
    StockItemResponse createStockItem(StockItemRequest request);

    @Transactional(readOnly = true)
    StockItemResponse getStockItemById(Long id);

    @Transactional(readOnly = true)
    List<StockItemResponse> getAllStockItems();

    @Transactional
    StockItemResponse updateStockItem(Long id, StockItemRequest request);

    @Transactional
    void deleteStockItem(Long id);
}
