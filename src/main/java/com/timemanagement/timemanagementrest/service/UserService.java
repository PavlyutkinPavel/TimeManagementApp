package com.timemanagement.timemanagementrest.service;

import com.timemanagement.timemanagementrest.domain.Role;
import com.timemanagement.timemanagementrest.domain.User;
import com.timemanagement.timemanagementrest.exception.UserNotFoundException;
import com.timemanagement.timemanagementrest.repository.UserRepository;
import com.timemanagement.timemanagementrest.security.domain.SecurityCredentials;
import com.timemanagement.timemanagementrest.security.repository.SecurityCredentialsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SecurityCredentialsRepository securityCredentialsRepository;

    public UserService(UserRepository userRepository, SecurityCredentialsRepository securityCredentialsRepository) {
        this.userRepository = userRepository;
        this.securityCredentialsRepository = securityCredentialsRepository;
    }

    public List<User> getUsers(Principal principal) {
        return userRepository.findAllUsers();
    }

    public Optional<User> findUserByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    public Optional<User> findUserByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    public User getUser(Long id, Principal principal) {
        SecurityCredentials credentials = securityCredentialsRepository.findUserIdByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        Long currentUserId = credentials.getUserId();
        User user =  userRepository.findByIdUser(id).orElseThrow(UserNotFoundException::new);
        Role role = credentials.getUserRole();
        if((currentUserId == user.getId()) || (role.toString() == "ADMIN")){
            return user;
        }else{
            return null;
        }
    }

//    public void createUser(User user) {
//        user.setCreatedAt(LocalDateTime.now());
//        userRepository.saveUser(user);
//    }

    public void updateUser(User user, Long currentId) {
        userRepository.saveAndFlushUser(user, currentId);
    }

    @Transactional
    public void deleteUserById(Long id){
        userRepository.deleteByIdUser(id);
    }

}
