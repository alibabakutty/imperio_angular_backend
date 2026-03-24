package imperio.imperio_backend.stock_item_master.service.service_impl;


import imperio.imperio_backend.stock_item_master.dto.StockItemRequest;
import imperio.imperio_backend.stock_item_master.dto.StockItemResponse;
import imperio.imperio_backend.stock_item_master.mapper.StockItemMasterMapper;
import imperio.imperio_backend.stock_item_master.module.StockItemMaster;
import imperio.imperio_backend.stock_item_master.repository.StockItemMasterRepository;
import imperio.imperio_backend.stock_item_master.service.StockItemMasterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockItemMasterServiceImpl implements StockItemMasterService {

    private final StockItemMasterRepository stockItemMasterRepository;
    private final StockItemMasterMapper stockItemMasterMapper;

    @Transactional
    @Override
    public StockItemResponse createStockItem(StockItemRequest request){
        if (stockItemMasterRepository.existsByStockItemCode(request.stockItemCode())) {
            throw new IllegalArgumentException("Item code '" + request.stockItemCode() + "' already exists.");
        }
        
        StockItemMaster entity = stockItemMasterMapper.toEntity(request);
        StockItemMaster savedEntity = stockItemMasterRepository.save(entity);
        return stockItemMasterMapper.toResponse(savedEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public StockItemResponse getStockItemById(Long id){
        StockItemMaster entity = stockItemMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stock item not found with ID: " + id));
        return stockItemMasterMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StockItemResponse> getAllStockItems(){
        return stockItemMasterRepository.findAll()
                .stream()
                .map(stockItemMasterMapper::toResponse)
                .toList();
    }

    @Transactional
    @Override
    public StockItemResponse updateStockItem(Long id, StockItemRequest request){
        // fetch existing record
        StockItemMaster existingEntity = stockItemMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Update failed: Stock item not found with ID: " + id));

        // clear existing rates to avoid "Collection was replaced" or duplicate errors"
        if (existingEntity.getRateMasterTables() != null) {
            existingEntity.getRateMasterTables().clear();
        }

        // use the mapper to merge changes
        stockItemMasterMapper.updateEntityFromRequest(request, existingEntity);
        // save and return
        StockItemMaster updatedEntity = stockItemMasterRepository.save(existingEntity);
        return stockItemMasterMapper.toResponse(updatedEntity);
    }

    @Transactional
    @Override
    public void deleteStockItem(Long id){
        if (!stockItemMasterRepository.existsById(id)) {
            throw new EntityNotFoundException("Delete failed: Stock item not found with ID: " + id);
        }
        stockItemMasterRepository.deleteById(id);
    }
}
