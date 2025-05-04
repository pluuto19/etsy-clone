package project.semester.aspm.storesevice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "stores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String storeName;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false)
    private String category;
    
    @Column(length = 500)
    private String address;
    
    @Column(name = "contact_email")
    private String contactEmail;
    
    private String phone;
    
    @Column(name = "logo_url")
    private String storeLogoUrl;
    
    @Column(name = "banner_url")
    private String bannerUrl;
    
    @Column(length = 1000)
    private String policies;
    
    private String location;
    
    @Column(name = "business_hours")
    private String businessHours;
    
    @Column(nullable = false)
    private Long sellerId;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    private boolean active = true;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 