package com.example.SpringApp.controller;

import com.example.SpringApp.models.OfflineTransactionRequest;
import com.example.SpringApp.models.OnlineTransactionRequest;
import com.example.SpringApp.models.OnlineTransactionResponse;
import com.example.SpringApp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/online")
    public OnlineTransactionResponse onlineTransaction(@RequestBody OnlineTransactionRequest onlineTransactionRequest) {
        return transactionService.onlineTransaction(onlineTransactionRequest);
    }

    @PostMapping("/online/verify")
    public String onlineTransactionVerify(@RequestBody OnlineTransactionResponse onlineTransactionResponse) {
        return transactionService.onlineTransactionVerify(onlineTransactionResponse);
    }

    @PostMapping("/offline")
    public String offlineTransaction(@RequestBody OfflineTransactionRequest offlineTransactionRequest) {
        return transactionService.offlineTransaction(offlineTransactionRequest);
    }
}
