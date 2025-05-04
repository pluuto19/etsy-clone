package project.semester.aspm.storesevice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.semester.aspm.storesevice.dto.StoreRequest;
import project.semester.aspm.storesevice.dto.StoreResponse;
import project.semester.aspm.storesevice.exception.StoreServiceException;
import project.semester.aspm.storesevice.service.StoreService;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/{userId}")
    public ResponseEntity<StoreResponse> createStore(
            @PathVariable Long userId,
            @RequestBody StoreRequest request) {
        StoreResponse createdStore = storeService.createStore(request, userId);
        return new ResponseEntity<>(createdStore, HttpStatus.CREATED);
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<StoreResponse> getStoreByUserId(@PathVariable Long userId) {
        StoreResponse store = storeService.getStoreBySellerId(userId);
        return ResponseEntity.ok(store);
    }
    
    @GetMapping("/check/{userId}")
    public ResponseEntity<Boolean> checkIfUserHasStore(@PathVariable Long userId) {
        boolean hasStore = storeService.hasStore(userId);
        return ResponseEntity.ok(hasStore);
    }
    
    @GetMapping("/public/{storeId}")
    public ResponseEntity<StoreResponse> getStoreById(@PathVariable Long storeId) {
        StoreResponse store = storeService.getStoreById(storeId);
        return ResponseEntity.ok(store);
    }
    
    @GetMapping("/public/name/{storeName}")
    public ResponseEntity<StoreResponse> getStoreByName(@PathVariable String storeName) {
        StoreResponse store = storeService.getStoreByName(storeName);
        return ResponseEntity.ok(store);
    }
} 