package com.timemanagement.timemanagementrest.repository;

import com.timemanagement.timemanagementrest.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long>{
    @Query(value = "SELECT NEXTVAL('wallets_id_seq')", nativeQuery = true)
    Long getNextSequenceValue();
}
