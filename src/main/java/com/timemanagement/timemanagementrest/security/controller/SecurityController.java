package com.timemanagement.timemanagementrest.security.controller;

import com.timemanagement.timemanagementrest.domain.User;
import com.timemanagement.timemanagementrest.security.domain.AdminDTO;
import com.timemanagement.timemanagementrest.security.domain.AuthRequest;
import com.timemanagement.timemanagementrest.security.domain.AuthResponse;
import com.timemanagement.timemanagementrest.security.domain.RegistrationDTO;
import com.timemanagement.timemanagementrest.security.service.SecurityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.junit.jupiter.api.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@Tag(name = "REGISTRATION/AUTHENTICATION")
public class SecurityController {

    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthResponse> generateToken(@RequestBody AuthRequest authRequest){
        //1. generate JWT if all is good
        //2. return 401 code if all is bad
        String token = securityService.generateToken(authRequest);
        if (token.isBlank()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody RegistrationDTO registrationDTO, Principal principal){
        if(principal != null){
            if (!securityService.checkIfAdmin(principal.getName())) {
                securityService.registration(registrationDTO);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
            securityService.registration(registrationDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<HttpStatus> admin(@RequestBody AdminDTO adminDTO, Principal principal){
        securityService.authorizeAdmin(adminDTO, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}