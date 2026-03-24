package imperio.imperio_backend.stock_item_master.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// For sending data back

public record StockItemResponse (
        Long id,
        String stockItemCode,
        String stockItemDescription,
        String stockItemName,
        String stockItemCategory,
        String uom,
//        BigDecimal stockItemRate,
        String rateMaster,
        List<RateMasterDTO> rateMasterTables,
        LocalDateTime createdAt
){}