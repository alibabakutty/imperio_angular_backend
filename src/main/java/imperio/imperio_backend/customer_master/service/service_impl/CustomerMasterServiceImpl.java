package imperio.imperio_backend.customer_master.service.service_impl;

import imperio.imperio_backend.customer_master.dto.CustomerRequest;
import imperio.imperio_backend.customer_master.dto.CustomerResponse;
import imperio.imperio_backend.customer_master.mapper.CustomerMasterMapper;
import imperio.imperio_backend.customer_master.module.CustomerMaster;
import imperio.imperio_backend.customer_master.repository.CustomerMasterRepository;
import imperio.imperio_backend.customer_master.service.CustomerMasterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerMasterServiceImpl implements CustomerMasterService {

    private final CustomerMasterRepository customerMasterRepository;
    private final CustomerMasterMapper customerMasterMapper;

    @Transactional
    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        CustomerMaster entity = customerMasterMapper.toEntity(request);
        CustomerMaster savedEntity = customerMasterRepository.save(entity);
        return customerMasterMapper.toResponse(savedEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerResponse getCustomerById(Long id){
        CustomerMaster entity = customerMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + id));

        return customerMasterMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerResponse> getAllCustomers(){
        return customerMasterRepository.findAll()
                .stream()
                .map(customerMasterMapper::toResponse)
                .toList();
    }

    @Transactional
    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest request){
        // fetch existing record
        CustomerMaster existingEntity = customerMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Update failed: Customer not found with this ID: " + id));
        // use the mapper to merge changes
        customerMasterMapper.updateEntityFromRequest(request, existingEntity);
        // save and return
        CustomerMaster updatedEntity = customerMasterRepository.save(existingEntity);
        return customerMasterMapper.toResponse(updatedEntity);
    }

    @Transactional
    @Override
    public void deleteCustomer(Long id) {
        if (!customerMasterRepository.existsById(id)) {
            throw new EntityNotFoundException("Delete failed: customer not found with this ID: " + id);
        }
        customerMasterRepository.deleteById(id);
    }
}
