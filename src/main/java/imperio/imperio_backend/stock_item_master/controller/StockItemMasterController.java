package imperio.imperio_backend.stock_item_master.controller;


import imperio.imperio_backend.stock_item_master.dto.StockItemRequest;
import imperio.imperio_backend.stock_item_master.dto.StockItemResponse;
import imperio.imperio_backend.stock_item_master.service.StockItemMasterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/stock-items")
@RequiredArgsConstructor
public class StockItemMasterController {

    private final StockItemMasterService stockItemMasterService;

    @PostMapping
    public ResponseEntity<StockItemResponse> createStockItem(@Valid @RequestBody StockItemRequest request){
        StockItemResponse response = stockItemMasterService.createStockItem(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockItemResponse> getStockItemById(@PathVariable Long id){
        return ResponseEntity.ok(stockItemMasterService.getStockItemById(id));
    }

    @GetMapping
    public ResponseEntity<List<StockItemResponse>> getAllStockItems(){
        return ResponseEntity.ok(stockItemMasterService.getAllStockItems());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockItemResponse> updateStockItem(@PathVariable Long id, @Valid @RequestBody StockItemRequest request){
        return ResponseEntity.ok(stockItemMasterService.updateStockItem(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockItem(@PathVariable Long id){
        stockItemMasterService.deleteStockItem(id);
        return ResponseEntity.noContent().build();
    }
}
