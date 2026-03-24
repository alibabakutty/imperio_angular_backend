package imperio.imperio_backend.customer_master.service;

import imperio.imperio_backend.customer_master.dto.CustomerRequest;
import imperio.imperio_backend.customer_master.dto.CustomerResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerMasterService {
    @Transactional
    CustomerResponse createCustomer(CustomerRequest request);

    @Transactional(readOnly = true)
    CustomerResponse getCustomerById(Long id);

    @Transactional(readOnly = true)
    List<CustomerResponse> getAllCustomers();

    @Transactional
    CustomerResponse updateCustomer(Long id, CustomerRequest request);

    @Transactional
    void deleteCustomer(Long id);
}
