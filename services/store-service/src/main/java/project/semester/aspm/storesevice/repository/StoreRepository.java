package project.semester.aspm.storesevice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.semester.aspm.storesevice.model.Store;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByStoreName(String storeName);
    Optional<Store> findBySellerId(Long sellerId);
    boolean existsBySellerId(Long sellerId);
    boolean existsByStoreName(String storeName);
} 