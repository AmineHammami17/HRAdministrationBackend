package com.example.HRApplication.Services;

import com.example.HRApplication.DTO.ReqRes;
import com.example.HRApplication.DTO.SignInDTO;
import com.example.HRApplication.Models.SalaryHistory;
import com.example.HRApplication.Models.User;
import com.example.HRApplication.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SalaryHistoryService salaryHistoryService;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User ID not Found"));
    }

    public User updateUser(User userInfo) {
        User user = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new IllegalArgumentException("User ID not Found"));

        if (userInfo.getBaseSalary() != null && !userInfo.getBaseSalary().equals(user.getBaseSalary())) {
            double oldSalary = user.getBaseSalary();
            double newSalary = userInfo.getBaseSalary();
            double percentageChange = calculatePercentageChange(oldSalary, newSalary);

            SalaryHistory history = new SalaryHistory(
                    null,
                    user,
                    "Base Salary Change",
                    new Date(),
                    "Base Salary",
                    oldSalary,
                    newSalary,
                    percentageChange
            );
            salaryHistoryService.saveSalaryHistory(history);

            user.setBaseSalary(newSalary);
        }

        if (userInfo.getFirstname() != null) {
            user.setFirstname(userInfo.getFirstname());
        }
        if (userInfo.getLastname() != null) {
            user.setLastname(userInfo.getLastname());
        }
        if (userInfo.getDatejoined() != null) {
            user.setDatejoined(userInfo.getDatejoined());
        }
        if (userInfo.getJob() != null) {
            user.setJob(userInfo.getJob());
        }

        return userRepository.save(user);
    }

    private double calculatePercentageChange(double oldSalary, double newSalary) {
        return ((newSalary - oldSalary) / oldSalary) * 100;
    }
    public ReqRes signUp(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();
        try {
            Optional<User> existingUser = userRepository.findByEmail(registrationRequest.getEmail());
            if (existingUser.isPresent()) {
                resp.setMessage("Email already in use");
                resp.setStatusCode(400);
                return resp;
            }

            User user = new User();
            user.setEmail(registrationRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRole(registrationRequest.getRole());
            user.setJob(registrationRequest.getUser().getJob());
            user.setDatejoined(registrationRequest.getUser().getDatejoined());
            user.setBaseSalary(registrationRequest.getUser().getBaseSalary());
            User userResult = userRepository.save(user);

            if (userResult != null && userResult.getId() > 0) {
                resp.setUser(userResult);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }
        } catch (DataIntegrityViolationException e) {
            resp.setStatusCode(400);
            resp.setMessage("Email already in use");
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes signIn(SignInDTO signinRequest) {
        ReqRes response = new ReqRes();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
            var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow();
            System.out.println("USER IS: " + user);
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Signed In");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }


    public ReqRes refreshToken(ReqRes refreshTokenRequest) {
        ReqRes response = new ReqRes();
        String ourEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(ourEmail).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refreshed Token");
        } else {
            response.setStatusCode(500);
            response.setMessage("Invalid Refresh Token");
        }
        return response;
    }

    public boolean deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
}
