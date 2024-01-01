package com.timemanagement.timemanagementrest.repository;

import com.timemanagement.timemanagementrest.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true,value = "SELECT * FROM users WHERE last_name = :ln")
    Optional<User> findByLastName(@Param("ln") String ln);

    @Query(nativeQuery = true,value = "SELECT * FROM users  WHERE first_name = :fn")
    Optional<User> findByFirstName(@Param("fn") String fn);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE id = :userId")
    Optional<User> findByIdUser(Long userId);

    @Query(nativeQuery = true, value = "SELECT * FROM users")
    List<User> findAllUsers();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO users(id, first_name, last_name, created, email, average_progress) VALUES (:#{#user.id}, :#{#user.firstName}, :#{#user.lastName}, :#{#user.createdAt}, :#{#user.email}, :#{#user.average_progress})")
    void saveUser(@Param("user") User user);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE users SET first_name = :#{#user.firstName}, last_name = :#{#user.lastName}, created = :#{#user.createdAt}, email = :#{#user.email}, average_progress = :#{#user.average_progress} WHERE id = :#{#currentId}")
    void saveAndFlushUser(@Param("user") User user, Long currentId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM users WHERE id = :id")
    void deleteByIdUser(@Param("id") Long id);


}
