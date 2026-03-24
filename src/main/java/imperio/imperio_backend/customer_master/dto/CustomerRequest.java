package imperio.imperio_backend.customer_master.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerRequest(
        @NotBlank @Size(max = 50) String customerCode,
        @NotBlank String customerName,
        String customerMailId,
        String customerRegion,
        String customerSalesExecutiveName

) {
}
