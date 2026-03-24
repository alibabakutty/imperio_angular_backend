package imperio.imperio_backend.sales_order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

public record SalesOrderRequest(
        @NotBlank String orderNumber,
        @NotBlank String ledgerName,
        @NotNull(message = "Order date is required") LocalDate orderDate,
        String stockCategory,
        @NotBlank String itemName,
        @NotNull @PositiveOrZero Integer itemQuantity,
        String uom,
        @NotNull @PositiveOrZero BigDecimal itemRate,
        BigDecimal discountPercentage,
        BigDecimal discountAmount,
        BigDecimal itemNetRate,
        BigDecimal itemNetAmount,
        BigDecimal vatPercentage,
        BigDecimal totalAmount,
        BigDecimal grossItemQuantity,
        BigDecimal grossTotalNetAmount,
        BigDecimal grossTotalAmount,
        String narration,
        String orderPlacedBy,
        String orderApprovedBy
) {}