package com.example.SpringApp.controller;

import com.example.SpringApp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/approve/{user_id}")
    public String approveUser(@PathVariable String user_id) {
        return adminService.approveUser(user_id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/toggleOfflinePayments/{user_id}")
    public String toggleOfflinePayments(@PathVariable String user_id) {
        return adminService.toggleOfflinePayments(user_id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/vendor/approve/{vendor_id}")
    public String approveVendor(@PathVariable String vendor_id) {
        return adminService.approveVendor(vendor_id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/checkFlaggedTransaction/{transaction_id}")
    public String checkFlaggedTransaction(@PathVariable int transaction_id) {
        return adminService.checkFlaggedTransaction(transaction_id);
    }

}
