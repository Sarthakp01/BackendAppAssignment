package com.example.SpringApp.service;

import com.example.SpringApp.models.OfflineTransactionRequest;
import com.example.SpringApp.models.OnlineTransactionRequest;
import com.example.SpringApp.models.OnlineTransactionResponse;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {
    OnlineTransactionResponse onlineTransaction(OnlineTransactionRequest onlineTransactionRequest);
    String onlineTransactionVerify(OnlineTransactionResponse onlineTransactionResponse);
    String offlineTransaction(OfflineTransactionRequest offlineTransactionRequest);

}
