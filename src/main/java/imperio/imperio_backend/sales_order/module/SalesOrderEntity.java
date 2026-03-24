package imperio.imperio_backend.sales_order.module;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sales_order")
@EntityListeners(AuditingEntityListener.class)
public class SalesOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Order number is required")
    @Column(name = "order_number", nullable = false)
    private String orderNumber;

    @NotBlank(message = "Ledger name is required")
    @Column(name = "ledger_name", nullable = false)
    private String ledgerName;

    @NotNull(message = "Order date is required")
    @Column(name = "order_date")
    private LocalDate orderDate;

    private String stockCategory;

    @NotBlank(message = "Item name is required")
    private String itemName;

    @NonNull
    @PositiveOrZero
    private Integer itemQuantity;

    private String uom;

    @Column(precision = 19, scale = 4)
    private BigDecimal itemRate;

    @Column(precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @Column(precision = 19, scale = 4)
    private BigDecimal discountAmount;

    @Column(precision = 19, scale = 4)
    private BigDecimal itemNetRate;

    @Column(precision = 19, scale = 4)
    private BigDecimal itemNetAmount;

    @Column(precision = 5, scale = 2)
    private BigDecimal vatPercentage;

    @Column(precision = 19, scale = 4)
    private BigDecimal totalAmount;

    @Column(precision = 19, scale = 4)
    private BigDecimal GrossItemQuantity;

    @Column(precision = 19, scale = 4)
    private BigDecimal grossTotalNetAmount;

    @Column(precision = 19, scale = 4)
    private BigDecimal grossTotalAmount;

    @Column(length = 1000)
    private String narration;

    private String orderPlacedBy;

    private String orderApprovedBy;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedTime;

}
