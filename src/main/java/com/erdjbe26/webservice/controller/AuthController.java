package com.erdjbe26.webservice.controller;

import com.erdjbe26.webservice.dto.JwtAuthResponse;
import com.erdjbe26.webservice.dto.LoginDto;
import com.erdjbe26.webservice.dto.RegisterUserDto;
import com.erdjbe26.webservice.dto.UserDto;
import com.erdjbe26.webservice.entity.MstUser;
import com.erdjbe26.webservice.repository.MstUserRepository;
import com.erdjbe26.webservice.security.JwtTokenProvider;
import com.erdjbe26.webservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final MstUserRepository mstUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          MstUserRepository mstUserRepository,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mstUserRepository = mstUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) {
        UserDto createdUser = userService.registerUser(registerUserDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/debug/check-admin-password")
    public ResponseEntity<Map<String, Object>> checkAdminPassword() {
        Map<String, Object> response = new HashMap<>();
        Optional<MstUser> adminUserOptional = mstUserRepository.findByUsername("admin");

        if (adminUserOptional.isEmpty()) {
            response.put("error", "User 'admin' not found in the database.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        MstUser adminUser = adminUserOptional.get();
        String storedPasswordHash = adminUser.getPassword();
        String rawPassword = "password";
        String newGeneratedHash = passwordEncoder.encode(rawPassword);
        boolean passwordsMatch = passwordEncoder.matches(rawPassword, storedPasswordHash);

        response.put("username", "admin");
        response.put("rawPasswordToCheck", rawPassword);
        response.put("storedPasswordHash", storedPasswordHash);
        response.put("newlyGeneratedHashForComparison", newGeneratedHash);
        response.put("doPasswordsMatch", passwordsMatch);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/debug/fix-admin-password")
    public ResponseEntity<Map<String, Object>> fixAdminPassword() {
        Map<String, Object> response = new HashMap<>();
        Optional<MstUser> adminUserOptional = mstUserRepository.findByUsername("admin");

        if (adminUserOptional.isEmpty()) {
            response.put("error", "User 'admin' not found in the database.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        MstUser adminUser = adminUserOptional.get();
        String newPassword = "password";
        String newPasswordHash = passwordEncoder.encode(newPassword);
        
        String oldHash = adminUser.getPassword();
        adminUser.setPassword(newPasswordHash);
        mstUserRepository.save(adminUser);

        response.put("message", "Admin password has been updated successfully!");
        response.put("username", "admin");
        response.put("newPassword", newPassword);
        response.put("oldPasswordHash", oldHash);
        response.put("newPasswordHash", newPasswordHash);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/debug/validate-token")
    public ResponseEntity<Map<String, Object>> validateToken(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        String bearerToken = request.getHeader("Authorization");
        
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            response.put("error", "No Bearer token found in Authorization header");
            return ResponseEntity.badRequest().body(response);
        }
        
        String token = bearerToken.substring(7);
        boolean isValid = jwtTokenProvider.validateToken(token);
        
        response.put("token", token);
        response.put("isValid", isValid);
        
        if (isValid) {
            String username = jwtTokenProvider.getUsername(token);
            response.put("username", username);
        }
        
        return ResponseEntity.ok(response);
    }
}
