package imperio.imperio_backend.stock_item_master.module;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.aspectj.bridge.IMessage;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock_item_master", indexes = {
        @Index(name = "idx_stock_item_code", columnList = "stock_item_code")
})
@EntityListeners(AuditingEntityListener.class)
public class StockItemMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Item code is mandatory")
    @Size(max = 50)
    @Column(name = "stock_item_code", nullable = false, unique = true, length = 50)
    private String stockItemCode;

    @Column(name = "stock_item_description", nullable = false)
    private String stockItemDescription;

    @NotBlank(message = "Item name is mandatory")
    @Column(name = "stock_item_name", nullable = false)
    private String stockItemName;

    @Column(name = "stock_item_category")
    private String stockItemCategory;

    @Column(name = "uom")
    private String uom;

    @Column(name = "is_rate_master", nullable = false)
    private boolean rateMaster = false;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "stock_item_id")  // points to the FK in the rate master table
    @ToString.Exclude   //  prevent infinite loop in logs
    private List<RateMasterTable> rateMasterTables = new java.util.ArrayList<>();

//    @NotNull(message = "Rate cannot be null")
//    @PositiveOrZero(message = "Rate must be zero or positive")
//    @Column(name = "stock_item_rate",precision = 19, scale = 4)
//    private BigDecimal stockItemRate;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // --- Custom Equals & HashCode (Professional Approach) ---
    // We only use the ID (or unique business key) for equality check.
    // This avoids LazyInitializationException and infinite recursion.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockItemMaster that = (StockItemMaster) o;
        return Objects.equals(id, that.id) || Objects.equals(stockItemCode, that.stockItemCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stockItemCode);
    }
}
