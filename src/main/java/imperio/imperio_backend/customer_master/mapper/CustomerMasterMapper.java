package imperio.imperio_backend.customer_master.mapper;

import imperio.imperio_backend.customer_master.dto.CustomerRequest;
import imperio.imperio_backend.customer_master.dto.CustomerResponse;
import imperio.imperio_backend.customer_master.module.CustomerMaster;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMasterMapper {

    // For post
    CustomerMaster toEntity(CustomerRequest request);
    // For Get
    CustomerResponse toResponse(CustomerMaster entity);

    // For put/patch
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromRequest(CustomerRequest request, @MappingTarget CustomerMaster entity);
}
