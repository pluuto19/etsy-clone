package project.semester.aspm.storesevice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.semester.aspm.storesevice.model.Store;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreResponse {
    private Long id;
    private String storeName;
    private String description;
    private String category;
    private String address;
    private String contactEmail;
    private String phone;
    private String storeLogoUrl;
    private String bannerUrl;
    private String policies;
    private String location;
    private String businessHours;
    private Long sellerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;
    
    public static StoreResponse fromStore(Store store) {
        StoreResponse response = new StoreResponse();
        response.setId(store.getId());
        response.setStoreName(store.getStoreName());
        response.setDescription(store.getDescription());
        response.setCategory(store.getCategory());
        response.setAddress(store.getAddress());
        response.setContactEmail(store.getContactEmail());
        response.setPhone(store.getPhone());
        response.setStoreLogoUrl(store.getStoreLogoUrl());
        response.setBannerUrl(store.getBannerUrl());
        response.setPolicies(store.getPolicies());
        response.setLocation(store.getLocation());
        response.setBusinessHours(store.getBusinessHours());
        response.setSellerId(store.getSellerId());
        response.setCreatedAt(store.getCreatedAt());
        response.setUpdatedAt(store.getUpdatedAt());
        response.setActive(store.isActive());
        return response;
    }
} 