package com.example.HRApplication.Services;

import com.example.HRApplication.DTO.ReqRes;
import com.example.HRApplication.DTO.ResetPasswordRequest;
import com.example.HRApplication.DTO.SignInDTO;
import com.example.HRApplication.Models.EmailDetails;
import com.example.HRApplication.Models.SalaryHistory;
import com.example.HRApplication.Models.User;
import com.example.HRApplication.Repositories.EmailService;
import com.example.HRApplication.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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


    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;


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
            System.out.println("Received Registration Request: " + registrationRequest);

            Optional<User> existingUser = userRepository.findByEmail(registrationRequest.getEmail());
            if (existingUser.isPresent()) {
                resp.setMessage("Email already in use");
                resp.setStatusCode(400);
                return resp;
            }

            User user = new User();
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setEmail(registrationRequest.getEmail());
            user.setRole(registrationRequest.getRole());

            if (registrationRequest.getUser() != null) {
                User requestUser = registrationRequest.getUser();
                user.setFirstname(requestUser.getFirstname());
                user.setLastname(requestUser.getLastname());
                user.setJob(requestUser.getJob());
                user.setDatejoined(requestUser.getDatejoined());
                user.setStatus(requestUser.getStatus());
                user.setBaseSalary(requestUser.getBaseSalary());
            } else {
                resp.setMessage("User details are missing");
                resp.setStatusCode(400);
                return resp;
            }

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


    public ResponseEntity<ReqRes> signIn(SignInDTO signinRequest) {
        ReqRes response = new ReqRes();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
            var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Signed In");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
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

    public String forgotPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            userRepository.save(user);

            String resetLink = "http://localhost:4200/reset-password?token=" + token;
            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient(user.getEmail());
            emailDetails.setSubject("Password Reset");
            emailDetails.setMsgBody("Click the link to reset your password: " + resetLink);
            emailService.sendSimpleMail(emailDetails);

            return "Email sent";
        }
        return "Please Verify Your Email";
    }



    public String resetPassword(ResetPasswordRequest resetPasswordRequest) {
        Optional<User> userOptional = userRepository.findByResetToken(resetPasswordRequest.getToken());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
            user.setResetToken(null);
            userRepository.save(user);
            return "Password reset successfully";
        }
        return "Invalid token";
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public long countTotalUsers() {
        return userRepository.count();
    }

    public User uploadUserProfilePicture(Integer userId, MultipartFile file) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User ID not Found"));

        String filename = file.getOriginalFilename();
        fileSystemStorageService.store(file);

        user.setDisplayPictureFilename(filename);

        return userRepository.save(user);
    }

    public byte[] getImage(String filename) {
        try {
            Resource resource = fileSystemStorageService.loadAsResource(filename);
            return resource.getInputStream().readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file", e);
        }
    }



}
