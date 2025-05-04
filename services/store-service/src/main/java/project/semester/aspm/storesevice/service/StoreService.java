package project.semester.aspm.storesevice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.semester.aspm.storesevice.dto.StoreRequest;
import project.semester.aspm.storesevice.dto.StoreResponse;
import project.semester.aspm.storesevice.exception.StoreServiceException;
import project.semester.aspm.storesevice.model.Store;
import project.semester.aspm.storesevice.repository.StoreRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public StoreResponse createStore(StoreRequest request, Long sellerId) {
        // Check if seller already has a store
        if (storeRepository.existsBySellerId(sellerId)) {
            throw StoreServiceException.conflict("Seller already has a store");
        }
        
        // Check if store name is already taken
        if (storeRepository.existsByStoreName(request.getStoreName())) {
            throw StoreServiceException.conflict("Store name is already taken");
        }
        
        // Create new store
        Store store = new Store();
        store.setStoreName(request.getStoreName());
        store.setDescription(request.getDescription());
        store.setCategory(request.getCategory());
        store.setAddress(request.getAddress());
        store.setContactEmail(request.getContactEmail());
        store.setPhone(request.getPhone());
        store.setStoreLogoUrl(request.getStoreLogoUrl());
        store.setBannerUrl(request.getBannerUrl());
        store.setPolicies(request.getPolicies());
        store.setLocation(request.getLocation());
        store.setBusinessHours(request.getBusinessHours());
        store.setSellerId(sellerId);
        store.setActive(true);
        
        Store savedStore = storeRepository.save(store);
        
        return StoreResponse.fromStore(savedStore);
    }
    
    public StoreResponse getStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> StoreServiceException.notFound("Store not found with ID: " + storeId));
        
        return StoreResponse.fromStore(store);
    }
    
    public StoreResponse getStoreByName(String storeName) {
        Store store = storeRepository.findByStoreName(storeName)
                .orElseThrow(() -> StoreServiceException.notFound("Store not found with name: " + storeName));
        
        return StoreResponse.fromStore(store);
    }
    
    public StoreResponse getStoreBySellerId(Long sellerId) {
        Store store = storeRepository.findBySellerId(sellerId)
                .orElseThrow(() -> StoreServiceException.notFound("Seller does not have a store: " + sellerId));
        
        return StoreResponse.fromStore(store);
    }
    
    public boolean hasStore(Long sellerId) {
        return storeRepository.existsBySellerId(sellerId);
    }
} 