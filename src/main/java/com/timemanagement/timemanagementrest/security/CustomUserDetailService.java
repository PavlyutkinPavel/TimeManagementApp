package com.timemanagement.timemanagementrest.security;

import com.timemanagement.timemanagementrest.security.domain.SecurityCredentials;
import com.timemanagement.timemanagementrest.security.repository.SecurityCredentialsRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final SecurityCredentialsRepository securityCredentialsRepository;

    public CustomUserDetailService(SecurityCredentialsRepository securityCredentialsRepository) {
        this.securityCredentialsRepository = securityCredentialsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SecurityCredentials> securityCredentials = securityCredentialsRepository.findByUserLogin(username);
        if(securityCredentials.isEmpty()){
            throw new UsernameNotFoundException(username);//401 нужно кидать
        }
        return User
                .withUsername(securityCredentials.get().getUserLogin())
                .password(securityCredentials.get().getUserPassword())
                .roles(securityCredentials.get().getUserRole().toString())
                .build();
    }
}
