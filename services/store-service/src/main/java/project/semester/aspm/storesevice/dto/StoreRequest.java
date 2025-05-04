package project.semester.aspm.storesevice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequest {
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
} 