package com.example.SpringApp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String user1;

    private String user2;

    private double amount;

    private LocalDateTime date;

    private String status; // e.g. "pending", "approved", "rejected", "flagged"

    @Version
    private long version;

    private String otp;

}
