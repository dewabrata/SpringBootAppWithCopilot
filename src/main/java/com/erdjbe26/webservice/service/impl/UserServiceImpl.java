package com.erdjbe26.webservice.service.impl;

import com.erdjbe26.webservice.dto.RegisterUserDto;
import com.erdjbe26.webservice.dto.UserDto;
import com.erdjbe26.webservice.entity.MstAkses;
import com.erdjbe26.webservice.entity.MstUser;
import com.erdjbe26.webservice.exception.ResourceNotFoundException;
import com.erdjbe26.webservice.exception.BadRequestException;
import com.erdjbe26.webservice.mapper.UserMapper;
import com.erdjbe26.webservice.repository.MstAksesRepository;
import com.erdjbe26.webservice.repository.MstUserRepository;
import com.erdjbe26.webservice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final MstUserRepository mstUserRepository;
    private final MstAksesRepository mstAksesRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(MstUserRepository mstUserRepository, MstAksesRepository mstAksesRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.mstUserRepository = mstUserRepository;
        this.mstAksesRepository = mstAksesRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserDto registerUser(RegisterUserDto registerUserDto) {
        mstUserRepository.findByUsername(registerUserDto.getUsername()).ifPresent(u -> {
            throw new BadRequestException("Username already exists");
        });

        mstUserRepository.findByEmail(registerUserDto.getEmail()).ifPresent(u -> {
            throw new BadRequestException("Email already exists");
        });

        MstAkses akses = mstAksesRepository.findById(registerUserDto.getIdAkses())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + registerUserDto.getIdAkses()));

        MstUser newUser = userMapper.toEntity(registerUserDto);
        newUser.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        newUser.setAkses(akses);
        newUser.setIsRegistered(true);
        newUser.setCreatedBy(1L); // System user for now
        newUser.setCreatedDate(LocalDateTime.now());

        MstUser savedUser = mstUserRepository.save(newUser);

        return userMapper.toDto(savedUser);
    }
}
