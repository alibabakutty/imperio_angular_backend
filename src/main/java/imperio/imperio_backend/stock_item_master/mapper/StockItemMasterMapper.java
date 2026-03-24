package imperio.imperio_backend.stock_item_master.mapper;

import imperio.imperio_backend.stock_item_master.dto.RateMasterDTO;
import imperio.imperio_backend.stock_item_master.dto.StockItemRequest;
import imperio.imperio_backend.stock_item_master.dto.StockItemResponse;
import imperio.imperio_backend.stock_item_master.module.RateMasterTable;
import imperio.imperio_backend.stock_item_master.module.StockItemMaster;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockItemMasterMapper {

    // For Post (Create)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    StockItemMaster toEntity(StockItemRequest request);

    // For Get (Read)
    StockItemResponse toResponse(StockItemMaster entity);

    // For put/patch
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    // selection strategy: avoids replacing the whole collection if possible
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(StockItemRequest request, @MappingTarget StockItemMaster entity);

    // helpers for mappings for list
    RateMasterTable toRateEntity(RateMasterDTO dto);

    RateMasterDTO toRateDto(RateMasterTable entity);
}