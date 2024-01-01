package com.timemanagement.timemanagementrest.controller;

import com.timemanagement.timemanagementrest.domain.User;
import com.timemanagement.timemanagementrest.exception.RequestBodyNotFullException;
import com.timemanagement.timemanagementrest.exception.UserNotFoundException;
import com.timemanagement.timemanagementrest.security.service.SecurityService;
import com.timemanagement.timemanagementrest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "User Controller", description = "Makes all operations with users")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final SecurityService securityService;

    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all users(for admins)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all users has worked successfully"),
            @ApiResponse(responseCode = "404", description = "No users found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public ResponseEntity<List<User>> getUsers(Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            List<User> users = userService.getUsers(principal);
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(users, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "get user by last name(for all users)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user has worked successfully"),
            @ApiResponse(responseCode = "404", description = "No user found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/last")
    public ResponseEntity<User> getUserByLastName(@RequestParam String lastName) {
        User user = userService.findUserByLastName(lastName).orElseThrow(UserNotFoundException::new);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "get user by first name(for all users)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user has worked successfully"),
            @ApiResponse(responseCode = "404", description = "No user found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/first")
    public ResponseEntity<User> getUserByFirstName(@RequestParam String firstName) {
        User user = userService.findUserByFirstName(firstName).orElseThrow(UserNotFoundException::new);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "get user (for current user and admins)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user has worked successfully"),
            @ApiResponse(responseCode = "404", description = "No user found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id, Principal principal) {
        User user = userService.getUser(id, principal);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    //maybe should be deleted(or only for admins)
//    @Operation(summary = "create user (for authorized users)")
//    @PostMapping
//    public ResponseEntity<HttpStatus> createUser(@RequestBody User user) {
//        userService.createUser(user);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }


    @Operation(summary = "update user (for current user and admins)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User updated successfully"),
        @ApiResponse(responseCode = "400", description = "Request body provided through method of controller is not full!!!, you have to provide all columns of the entity"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@Valid @RequestBody User user, Principal principal, BindingResult result) {
        Long resultId;
        Boolean check = (securityService.getUserIdByLogin(principal.getName()) == user.getId());
        Long currentId = securityService.getUserIdByLogin(principal.getName());
        if (result.hasErrors()) {
            throw new RequestBodyNotFullException();
        }
        if(user.getId() == 0){
            resultId = currentId;
        } else {
            resultId = user.getId();
        }
        if (securityService.checkIfAdmin(principal.getName()) || check) {
            userService.updateUser(user, resultId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete user (for current user and admins)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted user successfully"),
            @ApiResponse(responseCode = "404", description = "No user found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName()) || (securityService.getUserIdByLogin(principal.getName()) == id)) {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
