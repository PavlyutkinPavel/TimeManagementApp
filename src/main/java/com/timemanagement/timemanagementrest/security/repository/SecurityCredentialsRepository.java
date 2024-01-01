package com.timemanagement.timemanagementrest.security.repository;

import com.timemanagement.timemanagementrest.security.domain.SecurityCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityCredentialsRepository extends JpaRepository<SecurityCredentials, Long> {
    Optional<SecurityCredentials> findByUserLogin(String login);

    @Query(nativeQuery = true, value = "SELECT * FROM security_credentials WHERE user_login=:login")
    Optional<SecurityCredentials> findUserIdByLogin(String login);

    @Query(nativeQuery = true, value = "UPDATE security_credentials SET user_role = 'ADMIN' WHERE user_login = :login")
    void setUserRoleAdmin(String login);
}
