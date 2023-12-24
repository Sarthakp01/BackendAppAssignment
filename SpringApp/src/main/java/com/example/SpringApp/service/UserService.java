package com.example.SpringApp.service;

import com.example.SpringApp.models.AllocateFunds;
import com.example.SpringApp.models.AuthenticateRequest;
import com.example.SpringApp.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    String registerUser(User user);
    String authenticateUser(AuthenticateRequest request);
    String allocateFunds(AllocateFunds funds);
    String allocateOfflineFunds(AllocateFunds funds);
}
