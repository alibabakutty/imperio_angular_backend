package imperio.imperio_backend.sales_order.service.service_impl;

import imperio.imperio_backend.sales_order.dto.SalesOrderRequest;
import imperio.imperio_backend.sales_order.dto.SalesOrderResponse;
import imperio.imperio_backend.sales_order.mapper.SalesOrderMapper;
import imperio.imperio_backend.sales_order.module.SalesOrderEntity;
import imperio.imperio_backend.sales_order.repository.SalesOrderRepository;
import imperio.imperio_backend.sales_order.service.SalesOrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesOrderServiceImpl implements SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final SalesOrderMapper salesOrderMapper;

    @Transactional
    @Override
    public SalesOrderResponse createSalesOrder(SalesOrderRequest request){
        SalesOrderEntity entity = salesOrderMapper.toEntity(request);

        calculateFinancials(entity);

        SalesOrderEntity savedEntity = salesOrderRepository.save(entity);
        return salesOrderMapper.toResponse(savedEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public SalesOrderResponse getSalesOrderById(Long id){
        return salesOrderRepository.findById(id)
                .map(salesOrderMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Sales order not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<SalesOrderResponse> getAllSalesOrders(){
        return salesOrderRepository.findAll()
                .stream()
                .map(salesOrderMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<SalesOrderResponse> getFullOrderByOrderNumber(String orderNumber){
        return salesOrderRepository.findByOrderNumber(orderNumber)
                .stream()
                .map(salesOrderMapper::toResponse)
                .toList();
    }

    @Transactional
    @Override
    public SalesOrderResponse updateSalesOrder(Long id, SalesOrderRequest request){
        SalesOrderEntity existingEntity = salesOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Update failed: Order not found"));

        // update fields from DTO
        salesOrderMapper.updateEntityFromRequest(request, existingEntity);
        // recalculate based on new values
        calculateFinancials(existingEntity);

        return salesOrderMapper.toResponse(salesOrderRepository.save(existingEntity));
    }

    @Transactional
    @Override
    public void deleteSalesOrder(Long id){
        if (!salesOrderRepository.existsById(id)) {
            throw new EntityNotFoundException("Delete failed: Order not found");
        }
        salesOrderRepository.deleteById(id);
    }

    private void calculateFinancials(SalesOrderEntity entity){
        BigDecimal qty = BigDecimal.valueOf(entity.getItemQuantity());
        BigDecimal rate = entity.getItemRate();

        // calculate gross amount (qty * rate)
        BigDecimal itemGrossAmount = qty.multiply(rate);

        // calculate discount
        BigDecimal discount = itemGrossAmount.multiply(entity.getDiscountPercentage())
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        entity.setDiscountAmount(discount);

        // calculate net rate & net amount
        BigDecimal netAmount = itemGrossAmount.subtract(discount);
        entity.setItemNetAmount(netAmount);

        if (qty.compareTo(BigDecimal.ZERO) > 0) {
            entity.setItemRate(netAmount.divide(qty, 4, RoundingMode.HALF_UP));
        }

        // calculate VAT/TAX
        BigDecimal vat = netAmount.multiply(entity.getVatPercentage())
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);

        // final total for this row
        entity.setTotalAmount(netAmount.add(vat));
    }
}
