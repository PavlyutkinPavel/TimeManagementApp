package com.timemanagement.timemanagementrest.service;

import com.timemanagement.timemanagementrest.domain.Product;
import com.timemanagement.timemanagementrest.domain.PurchaseHistory;
import com.timemanagement.timemanagementrest.exception.ProductNotFoundException;
import com.timemanagement.timemanagementrest.repository.ProductRepository;
import com.timemanagement.timemanagementrest.repository.PurchaseHistoryRepository;
import com.timemanagement.timemanagementrest.security.domain.SecurityCredentials;
import com.timemanagement.timemanagementrest.security.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PurchaseHistoryService {
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final SecurityService securityService;
    private final ProductRepository productRepository;
    private final PurchaseHistory purchaseHistory;

    @Autowired
    public PurchaseHistoryService(PurchaseHistoryRepository purchaseHistoryRepository, SecurityService securityService, ProductRepository productRepository, PurchaseHistory purchaseHistory) {
        this.purchaseHistoryRepository = purchaseHistoryRepository;
        this.securityService = securityService;
        this.productRepository = productRepository;
        this.purchaseHistory = purchaseHistory;
    }

    public List<PurchaseHistory> getPurchaseHistories() {
        return purchaseHistoryRepository.findAllPurchaseHistories();
    }

    public Optional<PurchaseHistory> getPurchaseHistory(Long id) {
        return purchaseHistoryRepository.findByIdPurchaseHistory(id);
    }

    public void createPurchaseHistory(String productName, String myLogin, Principal principal) {
        if(myLogin.equals(principal.getName())){
            purchaseHistory.setId(purchaseHistoryRepository.getNextSequenceValue());
            purchaseHistory.setUserId(securityService.getUserIdByLogin(principal.getName()));
            Product product = productRepository.findByTitleProduct(productName).orElseThrow(ProductNotFoundException::new);
            purchaseHistory.setProductId(product.getId());
            purchaseHistory.setTotalAmount(product.getPrice());
            purchaseHistoryRepository.savePurchaseHistory(purchaseHistory);
        }else {
            log.info("User with login "+principal.getName()+" tried to buy product: "+productName);
        }

    }

    public void updatePurchaseHistory(PurchaseHistory purchaseHistory) {
        purchaseHistoryRepository.saveAndFlushPurchaseHistory(purchaseHistory);
    }

    public void deletePurchaseHistoryById(Long id) {
        purchaseHistoryRepository.deleteByIdPurchaseHistory(id);
    }

}