package com.example.SpringApp.controller;

import com.example.SpringApp.models.TransferFundsToPersonal;
import com.example.SpringApp.models.User;
import com.example.SpringApp.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    VendorService vendorService;

    @PostMapping("/register")
    public String addVendor(@RequestBody User vendor) {
        return vendorService.addVendor(vendor);
    }

    @PostMapping("/transferFundsToPersonalWallet")
    public String transferFundsToPersonalWallet(@RequestBody TransferFundsToPersonal transferFunds) {
        return vendorService.transferFundsToPersonalWallet(transferFunds);
    }

}
