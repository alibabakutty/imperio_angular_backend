package imperio.imperio_backend.sales_order.service;

import imperio.imperio_backend.sales_order.dto.SalesOrderRequest;
import imperio.imperio_backend.sales_order.dto.SalesOrderResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SalesOrderService {
    @Transactional
    SalesOrderResponse createSalesOrder(SalesOrderRequest request);

    @Transactional(readOnly = true)
    SalesOrderResponse getSalesOrderById(Long id);

    @Transactional(readOnly = true)
    List<SalesOrderResponse> getAllSalesOrders();

    @Transactional(readOnly = true)
    List<SalesOrderResponse> getFullOrderByOrderNumber(String orderNumber);

    @Transactional
    SalesOrderResponse updateSalesOrder(Long id, SalesOrderRequest request);

    @Transactional
    void deleteSalesOrder(Long id);
}
