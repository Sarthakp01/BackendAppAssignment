package com.example.SpringApp.serviceImpl;

import com.example.SpringApp.models.*;
import com.example.SpringApp.repository.*;
import com.example.SpringApp.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final UserWalletRepository userWalletRepository;
    private  final VendorWalletRepository vendorWalletRepository;
    private final TransactionRepository transactionRepository;
    private final VendorPersonalWalletRepository vendorPersonalWalletRepository;


    @Override
    public String approveUser(String user_id) {
        Optional<User> optionalUser = userRepository.findByUserId(user_id);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();
            user.setApproved(true);
            userRepository.save(user);
            var userWallet = UserWallet.builder()
                    .userId(user.getId())
                    .build();
            userWalletRepository.save(userWallet);

            return "User approved and wallet created";
        }

        return "User not found";
    }

    @Override
    public String toggleOfflinePayments(String user_id) {
        Optional<User> optionalUser = userRepository.findByUserId(user_id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (!user.isApproved()) {
                return "User/Vendor not approved";
            }

            user.setOfflinePaymentsEnabled(true);
            userRepository.save(user);


            return "Offline payments toggled";
        }

        return "User/Vendor not found";
    }

    @Override
    public String approveVendor(String vendor_id) {
        Optional<User> optionalVendor = userRepository.findByUserId(vendor_id);

        if (optionalVendor.isPresent()) {
            User vendor = optionalVendor.get();
            vendor.setApproved(true);

            userRepository.save(vendor);
            var vendorWallet = VendorWallet.builder()
                    .vendorId(vendor.getId())
                    .build();

            var vendorPersonalWallet = VendorPersonalWallet.builder()
                    .vendorId(vendor.getId())
                    .build();

            vendorWalletRepository.save(vendorWallet);
            vendorPersonalWalletRepository.save(vendorPersonalWallet);

            return "Vendor approved and wallets created";
        }

        return "Vendor not found";
    }

    @Override
    public String checkFlaggedTransaction(int transaction_id) {

        Transaction transaction = transactionRepository.findById(transaction_id).orElseThrow();
        String msg="";
        User user = userRepository.findByUserId(transaction.getUser1()).orElseThrow();
        User vendor = userRepository.findByUserId(transaction.getUser2()).orElseThrow();

        UserWallet userWallet = userWalletRepository.findByUserId(user.getId()).orElseThrow();
        VendorWallet vendorWallet = vendorWalletRepository.findByVendorId(vendor.getId()).orElseThrow();

        if(transaction.getStatus().equals("flagged")) {

           Random random = new Random();
           boolean flag = random.nextBoolean();

              if(flag) {
                userWallet.setOfflineBalance(userWallet.getOfflineBalance() - transaction.getAmount());
                vendorWallet.setBalance(vendorWallet.getBalance() + transaction.getAmount());
                transaction.setStatus("approved");
                msg = "Transaction approved";
              } else {
                  userWallet.setOfflineBalance(userWallet.getOfflineBalance() + transaction.getAmount());
                  transaction.setStatus("rejected");
                  msg = "Transaction rejected";
              }
        }

        userWalletRepository.save(userWallet);
        vendorWalletRepository.save(vendorWallet);
        transactionRepository.save(transaction);

        return msg;
    }
}
