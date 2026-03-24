package imperio.imperio_backend.customer_master.dto;

import java.time.LocalDateTime;

public record CustomerResponse(

        Long id,
        String customerCode,
        String customerName,
        String customerMailId,
        String customerRegion,
        String customerSalesExecutiveName,
        LocalDateTime createdAt
) {
}
