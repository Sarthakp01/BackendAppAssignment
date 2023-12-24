package com.example.SpringApp.service;

import com.example.SpringApp.models.TransferFundsToPersonal;
import com.example.SpringApp.models.User;
import org.springframework.stereotype.Service;

@Service
public interface VendorService {
    String addVendor(User vendor);
    String transferFundsToPersonalWallet(TransferFundsToPersonal funds);
}
