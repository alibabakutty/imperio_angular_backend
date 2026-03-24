package imperio.imperio_backend.stock_item_master.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RateMasterDTO(
        Long id,
        LocalDate rateMasterDate,
        BigDecimal rateMasterMrp,
        BigDecimal rateMasterRate,
        BigDecimal vatPercentage,
        String rateMasterStatus
) {
}
