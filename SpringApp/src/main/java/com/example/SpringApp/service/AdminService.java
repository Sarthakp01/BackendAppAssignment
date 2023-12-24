package com.example.SpringApp.service;

import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    String approveUser(String user_id);
    String toggleOfflinePayments(String user_id);
    String approveVendor(String vendor_id);
    String checkFlaggedTransaction(int transaction_id);
}
