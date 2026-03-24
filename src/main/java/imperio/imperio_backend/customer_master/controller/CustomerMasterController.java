package imperio.imperio_backend.customer_master.controller;

import imperio.imperio_backend.customer_master.dto.CustomerRequest;
import imperio.imperio_backend.customer_master.dto.CustomerResponse;
import imperio.imperio_backend.customer_master.service.CustomerMasterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerMasterController {

    private final CustomerMasterService customerMasterService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        CustomerResponse response = customerMasterService.createCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id){
        return ResponseEntity.ok(customerMasterService.getCustomerById(id));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(){
        return ResponseEntity.ok(customerMasterService.getAllCustomers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequest request){
        return ResponseEntity.ok(customerMasterService.updateCustomer(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockItem(@PathVariable Long id){
        customerMasterService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
