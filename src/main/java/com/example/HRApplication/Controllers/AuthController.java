package com.example.HRApplication.Controllers;

import com.example.HRApplication.DTO.ReqRes;
import com.example.HRApplication.DTO.ResetPasswordRequest;
import com.example.HRApplication.DTO.SignInDTO;
import com.example.HRApplication.Models.Complaint;
import com.example.HRApplication.Models.Enums.Roles;
import com.example.HRApplication.Models.User;
import com.example.HRApplication.Services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@Tag(name="Authentication + Authorization")

public class AuthController {

    @Autowired
    private AuthService authService;


    @GetMapping("/user/employee")
    @PreAuthorize("hasRole('ADMINHR')")
    public ResponseEntity<List<User>> getAllEmployees() {
        List<User> users = authService.getAllUsers().stream()
                .filter(user -> Roles.ROLE_EMPLOYEE.equals(user.getRole()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/employee/{id}")
    @PreAuthorize("hasRole('ADMINHR')")

    public ResponseEntity<User> getEmployeeById(@PathVariable Integer id){
        User user = authService.getUserById(id);
        if (user == null || !Roles.ROLE_EMPLOYEE.equals(user.getRole())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }


    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = authService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Integer id){
        User user = authService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);

    }

    @PutMapping("/user/update-user/")
    public User updateUser(@RequestBody User user) {
        return authService.updateUser(user);
    }



    @PostMapping("/signup")
    public ResponseEntity<ReqRes> signUp(@RequestBody ReqRes signUpRequest){
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<ReqRes> signIn(@RequestBody SignInDTO signInRequest) {
        return authService.signIn(signInRequest);
    }


    @PostMapping("/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        boolean isDeleted = authService.deleteUser(id);
        if (!isDeleted) {
            return ResponseEntity.status(404).body("User not found");
        }
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody String email) {
        String response = authService.forgotPassword(email);
        if (response.equals("Email sent")) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        String response = authService.resetPassword(resetPasswordRequest);
        if ("Password reset successfully".equals(response)) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/user/authenticiated")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ADMINHR') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<User> getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = authService.getUserByEmail(email);
        return ResponseEntity.ok(user);

    }

    @GetMapping("/count-users")
    public ResponseEntity<Long> countTotalUsers() {
        long userCount = authService.countTotalUsers();
        return ResponseEntity.ok(userCount);
    }



}


