package com.erdjbe26.webservice.controller;

import com.erdjbe26.webservice.dto.UserDto;
import com.erdjbe26.webservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        // Untuk sementara, return response sederhana untuk testing JWT
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    public ResponseEntity<String> testAuthentication() {
        return ResponseEntity.ok("JWT Authentication berhasil! Anda sudah terautentikasi.");
    }
}
