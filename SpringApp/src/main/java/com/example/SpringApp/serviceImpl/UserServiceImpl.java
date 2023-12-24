package com.example.SpringApp.serviceImpl;

import com.example.SpringApp.config.JwtService;
import com.example.SpringApp.models.*;
import com.example.SpringApp.repository.UserRepository;
import com.example.SpringApp.repository.UserWalletRepository;
import com.example.SpringApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserWalletRepository userWalletRepository;

    @Override
    public String registerUser(User user) {
        // Generate user secret

        // Save user
        User savedUser = userRepository.save(user);


        if(savedUser.getRole() == Role.ADMIN) {
            savedUser.setApproved(true);
        }

        savedUser.setPassword(passwordEncoder.encode(savedUser.getPassword()));
        userRepository.save(savedUser);

        String jwtToken = jwtService.generateToken(savedUser);

        return jwtToken;
    }


    @Override
    public String authenticateUser(AuthenticateRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserId(),
                        request.getPassword()
                ));

        User user = userRepository.findByUserId(request.getUserId()).orElseThrow();

        String jwtToken = jwtService.generateToken(user);

        return jwtToken;
    }

    @Override
    public String allocateFunds(AllocateFunds funds) {
        Optional<User> optionalUser = userRepository.findByUserId(funds.getUserId());

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<UserWallet> optionalUserWallet = userWalletRepository.findByUserId(user.getId());

            if(optionalUserWallet.isPresent()) {
                UserWallet userWallet = optionalUserWallet.get();
                double balance = userWallet.getBalance();
                userWallet.setBalance(balance+Double.parseDouble(funds.getAmount()));
                userWalletRepository.save(userWallet);
                return "Funds allocated";
            }
        }
        return "User not found";
    }

    @Override
    public String allocateOfflineFunds(AllocateFunds funds) {
        Optional<User> optionalUser = userRepository.findByUserId(funds.getUserId());

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<UserWallet> optionalUserWallet = userWalletRepository.findByUserId(user.getId());

            if(optionalUserWallet.isPresent()) {
                UserWallet userWallet = optionalUserWallet.get();
                double balance = userWallet.getOfflineBalance();

                if(balance + Double.parseDouble(funds.getAmount()) > 5000) {
                    return "Offline balance cannot exceed 5000";
                }

                userWallet.setOfflineBalance(balance+Double.parseDouble(funds.getAmount()));
                userWalletRepository.save(userWallet);

                if(userWallet.getCodes()==null && userWallet.getOfflineBalance() >= 0)
                {
                    Set<String> codes = new HashSet<>();
                    for (int i = 0; i < 5; i++) {
                        String code = UUID.randomUUID().toString();
                        System.out.println(code);
                        codes.add(code);
                }

                    userWallet.setCodes(codes);
                    userWalletRepository.save(userWallet);
                }

                return "Funds allocated";
            }
        }
        return "User not found";
    }
}
