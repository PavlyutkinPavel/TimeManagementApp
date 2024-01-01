package com.timemanagement.timemanagementrest.security.service;


import com.timemanagement.timemanagementrest.domain.Role;
import com.timemanagement.timemanagementrest.domain.User;
import com.timemanagement.timemanagementrest.domain.Wallet;
import com.timemanagement.timemanagementrest.exception.UserNotFoundException;
import com.timemanagement.timemanagementrest.repository.UserRepository;
import com.timemanagement.timemanagementrest.repository.WalletRepository;
import com.timemanagement.timemanagementrest.security.JwtUtils;
import com.timemanagement.timemanagementrest.security.domain.AdminDTO;
import com.timemanagement.timemanagementrest.security.domain.AuthRequest;
import com.timemanagement.timemanagementrest.security.domain.RegistrationDTO;
import com.timemanagement.timemanagementrest.security.domain.SecurityCredentials;
import com.timemanagement.timemanagementrest.security.repository.SecurityCredentialsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SecurityService {

    private final PasswordEncoder passwordEncoder;
    private final SecurityCredentialsRepository securityCredentialsRepository;
    private final JwtUtils jwtUtils;

    private final Wallet wallet;
    private final User user;

    private final SecurityCredentials securityCredentials;

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public SecurityService(PasswordEncoder passwordEncoder, SecurityCredentialsRepository securityCredentialsRepository, JwtUtils jwtUtils, Wallet wallet, User user, SecurityCredentials securityCredentials, UserRepository userRepository, WalletRepository walletRepository) {
        this.passwordEncoder = passwordEncoder;
        this.securityCredentialsRepository = securityCredentialsRepository;
        this.jwtUtils = jwtUtils;
        this.wallet = wallet;
        this.user = user;
        this.securityCredentials = securityCredentials;
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    public String generateToken(AuthRequest authRequest){
        //1. get User by login
        //2. check passwords
        //3. generate token by login
        //4. if all is bad then return empty string ""
        Optional<SecurityCredentials> credentials = securityCredentialsRepository.findByUserLogin(authRequest.getLogin());
        if (credentials.isPresent() && passwordEncoder.matches(authRequest.getPassword(),credentials.get().getUserPassword())){
            return jwtUtils.generateJwtToken(authRequest.getLogin());
        }
        return "";
    }

    @Transactional(rollbackFor = Exception.class)
    public void registration(RegistrationDTO registrationDTO){
        //1. parse DTO
        //2. create User+SecurityCredentials
        //3. make transaction and execution
        //Optional<SecurityCredentials> securityCredentials1 = securityCredentialsRepository.findByUserLogin(registrationDTO.getUserLogin())

        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setCreatedAt(LocalDateTime.now());
        user.setEmail(registrationDTO.getEmail());
        user.setAverage_progress(0.0);
        List<User> users = userRepository.findAllUsers();
        Integer currentSize;
        if(users.isEmpty()){
            currentSize = 0;
        }else{
            currentSize = userRepository.findAllUsers().size();
        }
        user.setId((long) (currentSize+1));
        userRepository.saveUser(user);
        User userResult = userRepository.findByFirstName(registrationDTO.getFirstName()).orElseThrow(UserNotFoundException::new);

        wallet.setId(walletRepository.getNextSequenceValue());
        wallet.setUserId(userResult.getId());
        wallet.setBalance(0L);

        securityCredentials.setUserLogin(registrationDTO.getUserLogin());
        securityCredentials.setUserPassword(passwordEncoder.encode(registrationDTO.getUserPassword()));
        securityCredentials.setUserRole(Role.USER);
        securityCredentials.setUserId(userResult.getId());
        securityCredentialsRepository.save(securityCredentials);
    }

    public Boolean checkIfAdmin(String login){
        Optional<SecurityCredentials> credentials = securityCredentialsRepository.findByUserLogin(login);
        if (credentials.isPresent() && credentials.get().getUserRole().toString() == "ADMIN"){
            return true;
        }else{
            return false;
        }
    }

    public void authorizeAdmin(AdminDTO adminDTO, Principal principal){
        String adminPassword = "admin";
        log.info(adminDTO.getAdminPassword());
        if(adminPassword.equals(adminDTO.getAdminPassword())){
            SecurityCredentials securityCredentials = securityCredentialsRepository.findByUserLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
            securityCredentials.setUserRole(Role.ADMIN);
            securityCredentialsRepository.save(securityCredentials);
        }else {
            log.info("User "+ principal.getName() + " tried to become admin");
        }
    }

    public String getUserByLogin(String login){
        SecurityCredentials credentials = securityCredentialsRepository.findByUserLogin(login).orElseThrow(UserNotFoundException::new);
        if (credentials != null){
            return credentials.getUserLogin();
        }else{
            return "";
        }
    }

    public Long getUserIdByLogin(String login){
        String username  = getUserByLogin(login);
        SecurityCredentials credentials = securityCredentialsRepository.findUserIdByLogin(username).orElseThrow(UserNotFoundException::new);
        if (credentials != null){
            return credentials.getUserId();
        }else{
            return 0L;
        }
    }

    public String getUserPasswordByLogin(String login){
        String username  = getUserByLogin(login);
        SecurityCredentials credentials = securityCredentialsRepository.findUserIdByLogin(username).orElseThrow(UserNotFoundException::new);
        if (credentials != null){
            return credentials.getUserPassword();
        }else{
            return "";
        }
    }
}
