package imperio.imperio_backend.sales_order.controller;


import imperio.imperio_backend.sales_order.dto.SalesOrderRequest;
import imperio.imperio_backend.sales_order.dto.SalesOrderResponse;
import imperio.imperio_backend.sales_order.service.SalesOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/sales-orders")
@RequiredArgsConstructor
public class SalesOrderController {

    private final SalesOrderService salesOrderService;

    @PostMapping
    public ResponseEntity<SalesOrderResponse> createSalesOrder(@Valid @RequestBody SalesOrderRequest request){
        return new ResponseEntity<>(salesOrderService.createSalesOrder(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesOrderResponse> getSalesOrderById(@PathVariable Long id){
        return ResponseEntity.ok(salesOrderService.getSalesOrderById(id));
    }

    @GetMapping
    public ResponseEntity<List<SalesOrderResponse>> getAllSalesOrders(){
        return ResponseEntity.ok(salesOrderService.getAllSalesOrders());
    }

    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<List<SalesOrderResponse>> getFullOrderByOrderNumber(@PathVariable String orderNumber){
        return ResponseEntity.ok(salesOrderService.getFullOrderByOrderNumber(orderNumber));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesOrderResponse> updateSalesOrder(@PathVariable Long id, @Valid @RequestBody SalesOrderRequest request){
        return ResponseEntity.ok(salesOrderService.updateSalesOrder(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSalesOrder(@PathVariable Long id){
        salesOrderService.deleteSalesOrder(id);
        return ResponseEntity.noContent().build();
    }
}
