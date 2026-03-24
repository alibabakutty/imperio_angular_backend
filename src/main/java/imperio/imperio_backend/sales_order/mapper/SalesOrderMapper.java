package imperio.imperio_backend.sales_order.mapper;

import imperio.imperio_backend.sales_order.dto.SalesOrderRequest;
import imperio.imperio_backend.sales_order.dto.SalesOrderResponse;
import imperio.imperio_backend.sales_order.module.SalesOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SalesOrderMapper {
    SalesOrderEntity toEntity(SalesOrderRequest request);

    SalesOrderResponse toResponse(SalesOrderEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    void updateEntityFromRequest(SalesOrderRequest request, @MappingTarget SalesOrderEntity entity);
}
