package imperio.imperio_backend.stock_item_master.module;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "rate_master")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateMasterTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date is required")
    private LocalDate rateMasterDate;

    @NotNull(message = "MRP is required")
    @DecimalMin(value = "0.0", message = "MRP cannot be negative")
    @Column(precision = 19, scale = 2)
    private BigDecimal rateMasterMrp;

    @NotNull(message = "Rate is required")
    @DecimalMin(value = "0.0", message = "Rate cannot be negative")
    @Column(precision = 19, scale = 2)
    private BigDecimal rateMasterRate;

    @DecimalMin(value = "0.0", message = "VAT cannot be negative")
    @DecimalMax(value = "100.0",message = "VAT cannot exceed 100%")
    @Column(precision = 5, scale = 2)
    private BigDecimal vatPercentage;

    @NotBlank(message = "Status is required")
    private String rateMasterStatus;
}
