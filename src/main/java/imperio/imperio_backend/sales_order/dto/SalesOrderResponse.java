package imperio.imperio_backend.sales_order.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record SalesOrderResponse (
        Long id,
        String orderNumber,
        String ledgerName,
        LocalDate orderDate,
        String itemName,
        Integer itemQuantity,
        BigDecimal itemRate,
        BigDecimal totalAmount,
        LocalDateTime createdTime
){}
