package imperio.imperio_backend.customer_master.module;

import imperio.imperio_backend.stock_item_master.module.StockItemMaster;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_master", indexes = {@Index(name = "idx_customer_code", columnList = "customer_code")})
@EntityListeners(AuditingEntityListener.class)
public class CustomerMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Customer code is mandatory")
    @Size(max = 50)
    @Column(name = "customer_code", nullable = false, unique = true, length = 50)
    private String customerCode;

    @NotBlank(message = "Customer name is mandatory")
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_mail_id")
    private String customerMailId;


    @Column(name = "customer_region")
    private String customerRegion;

    @Column(name = "customer_sales_executive_name")
    private String customerSalesExecutiveName;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // --- Custom Equals & HashCode (Professional Approach) ---
    // We only use the ID (or unique business key) for equality check.
    // This avoids LazyInitializationException and infinite recursion.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerMaster that = (CustomerMaster) o;
        return Objects.equals(id, that.id) || Objects.equals(customerCode, that.customerCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerCode);
    }
}
