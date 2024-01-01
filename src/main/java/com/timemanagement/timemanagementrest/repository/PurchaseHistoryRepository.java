package com.timemanagement.timemanagementrest.repository;

import com.timemanagement.timemanagementrest.domain.PurchaseHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM purchase_history WHERE id = :purchaseHistoryId")
    Optional<PurchaseHistory> findByIdPurchaseHistory(@Param("purchaseHistoryId") Long purchaseHistoryId);

    @Query(nativeQuery = true, value = "SELECT * FROM purchase_history")
    List<PurchaseHistory> findAllPurchaseHistories();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO purchase_history(id, product_id, total_amount, user_id) VALUES (:#{#purchaseHistory.id}, :#{#purchaseHistory.productId}, :#{#purchaseHistory.totalAmount}, :#{#purchaseHistory.userId})")
    void savePurchaseHistory(@Param("purchaseHistory") PurchaseHistory purchaseHistory);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE purchase_history SET product_id = :#{#purchaseHistory.productId},  total_amount = :#{#purchaseHistory.totalAmount}, user_id = :#{#purchaseHistory.userId} WHERE id = :#{#purchaseHistory.id}")
    void saveAndFlushPurchaseHistory(@Param("purchaseHistory") PurchaseHistory purchaseHistory);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM purchase_history WHERE id = :purchaseHistoryId")
    void deleteByIdPurchaseHistory(@Param("purchaseHistoryId") Long purchaseHistoryId);

    @Query(value = "SELECT NEXTVAL('purchase_history_id_seq')", nativeQuery = true)
    Long getNextSequenceValue();
}
