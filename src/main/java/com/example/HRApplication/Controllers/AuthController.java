package com.example.HRApplication.Controllers;

import com.example.HRApplication.DTO.ReqRes;
import com.example.HRApplication.Models.Complaint;
import com.example.HRApplication.Models.Enums.Roles;
import com.example.HRApplication.Models.User;
import com.example.HRApplication.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;


    @GetMapping("/hradmin/auth/user/employee")
    public ResponseEntity<List<User>> getAllEmployees() {
        List<User> users = authService.getAllUsers().stream()
                .filter(user -> Roles.EMPLOYEE.equals(user.getRole()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/hradmin/auth/user/employee/{id}")
    public ResponseEntity<User> getEmployeeById(@PathVariable Integer id){
        User user = authService.getUserById(id);
        if (user == null || !Roles.EMPLOYEE.equals(user.getRole())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }


    @GetMapping("admin/auth/user")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = authService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("admin/auth/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id){
        User user = authService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);

    }

    @PutMapping("public/auth/user/update-user/")
    public User updateUser(@RequestBody User user) {
        return authService.updateUser(user);
    }



    @PostMapping("public/auth/signup")
    public ResponseEntity<ReqRes> signUp(@RequestBody ReqRes signUpRequest){
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("public/auth/signin")
    public ResponseEntity<ReqRes> signIn(@RequestBody ReqRes signInRequest){
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }

    @PostMapping("public/auth/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @DeleteMapping("admin/auth/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        boolean isDeleted = authService.deleteUser(id);
        if (!isDeleted) {
            return ResponseEntity.status(404).body("User not found");
        }
        return ResponseEntity.noContent().build();
    }
}