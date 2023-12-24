package com.example.SpringApp.serviceImpl;

import com.example.SpringApp.config.JwtService;
import com.example.SpringApp.models.*;
import com.example.SpringApp.repository.UserRepository;
import com.example.SpringApp.repository.VendorPersonalWalletRepository;
import com.example.SpringApp.repository.VendorWalletRepository;
import com.example.SpringApp.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final VendorWalletRepository vendorWalletRepository;
    private final VendorPersonalWalletRepository vendorPersonalWalletRepository;

    @Override
    public String addVendor(User vendor) {

        if(vendor.getRole() == Role.VENDOR && vendor.getGpsLocationX()!=0 && vendor.getGpsLocationY()!=0) {
            User savedVendor = userRepository.save(vendor);
            savedVendor.setPassword(passwordEncoder.encode(savedVendor.getPassword()));
            userRepository.save(savedVendor);
            String jwt = jwtService.generateToken(savedVendor);
            System.out.println(jwt);

            return jwt;
        }
        else {
            return "Vendor not added";
        }
    }

    @Override
    public String transferFundsToPersonalWallet(TransferFundsToPersonal funds) {

        int vendorId = funds.getVendorId();
        double amount = funds.getAmount();

        VendorWallet vendorWallet = vendorWalletRepository.findByVendorId(vendorId).orElseThrow();
        VendorPersonalWallet vendorPersonalWallet = vendorPersonalWalletRepository.findByVendorId(vendorId).orElseThrow();

        if(vendorWallet.getBalance() < amount) {
            return "Insufficient balance";
        }

        vendorWallet.setBalance(vendorWallet.getBalance() - amount);
        vendorPersonalWallet.setBalance(vendorPersonalWallet.getBalance() + amount);

        vendorWalletRepository.save(vendorWallet);
        vendorPersonalWalletRepository.save(vendorPersonalWallet);

        return "Funds transferred";
    }

}
