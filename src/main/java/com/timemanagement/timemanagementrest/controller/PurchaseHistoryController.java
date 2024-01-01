package com.timemanagement.timemanagementrest.controller;

import com.timemanagement.timemanagementrest.domain.PurchaseHistory;
import com.timemanagement.timemanagementrest.exception.PurchaseHistoryNotFoundException;
import com.timemanagement.timemanagementrest.security.service.SecurityService;
import com.timemanagement.timemanagementrest.service.PurchaseHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "PurchaseHistory Controller", description = "makes all operations with purchaseHistory")
@RestController
@RequestMapping("/purchaseHistory")
public class PurchaseHistoryController {
    private final PurchaseHistoryService purchaseHistoryService;
    private final SecurityService securityService;

    public PurchaseHistoryController(PurchaseHistoryService purchaseHistoryService, SecurityService securityService) {
        this.purchaseHistoryService = purchaseHistoryService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all purchaseHistory(for all admins)")
    @GetMapping
    public ResponseEntity<List<PurchaseHistory>> getPurchaseHistories(Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            List<PurchaseHistory> purchaseHistories = purchaseHistoryService.getPurchaseHistories();
            if (purchaseHistories.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(purchaseHistories, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @Operation(summary = "get specific purchase(for all authorized users)")
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseHistory> getPurchaseHistory(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            PurchaseHistory purchaseHistory = purchaseHistoryService.getPurchaseHistory(id).orElseThrow(PurchaseHistoryNotFoundException::new);
            return new ResponseEntity<>(purchaseHistory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @Operation(summary = "buy something(for all authorized users)")
    @PostMapping
    public ResponseEntity<HttpStatus> createPurchaseHistory(@RequestParam String productName, @RequestParam String myLogin, Principal principal) {
        purchaseHistoryService.createPurchaseHistory(productName, myLogin, principal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "update purchaseHistory(for all admins)")
    @PutMapping
    public ResponseEntity<HttpStatus> updatePurchaseHistory(@RequestBody PurchaseHistory purchaseHistory, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            purchaseHistoryService.updatePurchaseHistory(purchaseHistory);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete purchaseHistory(for all admins)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePurchaseHistory(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            purchaseHistoryService.deletePurchaseHistoryById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
