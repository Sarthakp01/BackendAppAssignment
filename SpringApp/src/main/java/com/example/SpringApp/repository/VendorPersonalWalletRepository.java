package com.example.SpringApp.repository;

import com.example.SpringApp.models.VendorPersonalWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorPersonalWalletRepository extends JpaRepository<VendorPersonalWallet, Long> {
    Optional<VendorPersonalWallet> findByVendorId(int vendorId);
}
