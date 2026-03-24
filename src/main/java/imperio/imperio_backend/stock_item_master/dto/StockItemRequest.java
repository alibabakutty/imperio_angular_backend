package imperio.imperio_backend.stock_item_master.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

// For creating and updating

public record StockItemRequest (
        @NotBlank @Size(max = 50) String stockItemCode,
        @NotBlank String stockItemDescription,
        @NotBlank String stockItemName,
        String stockItemCategory,
        String uom,
        boolean rateMaster,

        @Valid // this ensures the rates inside the list are also validated
        List<RateMasterDTO> rateMasterTables
//        @NotNull @PositiveOrZero BigDecimal stockItemRate
) {}
