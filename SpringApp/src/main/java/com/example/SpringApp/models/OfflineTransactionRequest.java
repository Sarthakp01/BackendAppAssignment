package com.example.SpringApp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfflineTransactionRequest {

    private String userId1;
    private String userId2;
    private double amount;
    private String code;
    private double gpsLocationX;
    private double gpsLocationY;
}
