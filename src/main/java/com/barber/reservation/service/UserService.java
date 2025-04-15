package com.barber.reservation.service;

import com.barber.reservation.domain.User;
import com.barber.reservation.dto.request.UserRequestDTO;
import com.barber.reservation.dto.response.UserResponseDTO;
import com.barber.reservation.exception.DuplicateResourceException;
import com.barber.reservation.exception.ResourceNotFoundException;
import com.barber.reservation.mapper.UserMapper;
import com.barber.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.barber.reservation.constant.MessageConstant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail()))
            throw new DuplicateResourceException(EMAIL_ALREADY_IN_USE.getMessage());

        if (userRepository.existsByPhoneNumber(dto.getPhoneNumber()))
            throw new DuplicateResourceException(PHONE_NUMBER_ALREADY_IN_USE.getMessage());

        User user = userMapper.toEntity(dto);
        User savedUser = userRepository.save(user);
        log.info("User created: ID={}, Phone={}", savedUser.getId(), savedUser.getPhoneNumber());

        return userMapper.toUserResponseDTO(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO loginWithPhoneNumber(String phoneNumber, String password) {

        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException(INVALID_PHONE_NUMBER_OR_PASSWORD.getMessage()));

        //todo Replace this with proper password hashing and comparison
        if (!user.getPassword().equals(password)) {
            throw new ResourceNotFoundException(INVALID_PHONE_NUMBER_OR_PASSWORD.getMessage());
        }

        log.info("User logged in: ID={}, Phone={}", user.getId(), phoneNumber);
        return userMapper.toUserResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID.getMessage() + id));

        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail()) &&
                userRepository.existsByEmail(dto.getEmail()))
            throw new DuplicateResourceException(EMAIL_ALREADY_IN_USE.getMessage());

        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().equals(user.getPhoneNumber()) &&
                userRepository.existsByPhoneNumber(dto.getPhoneNumber()))
            throw new DuplicateResourceException(PHONE_NUMBER_ALREADY_IN_USE.getMessage());

        if (dto.getUsername() != null) user.setUsername(dto.getUsername());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) user.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getPassword() != null) user.setPassword(dto.getPassword());
        user.setBarber(dto.isBarber());

        User updated = userRepository.save(user);
        log.info("User updated: ID={}, Email={}", updated.getId(), updated.getEmail());

        return userMapper.toUserResponseDTO(updated);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID.getMessage() + id));

        userRepository.delete(user);
        log.info("User deleted: ID={}, phoneNumber={}", user.getId(), user.getPhoneNumber());
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponseDTO)
                .toList();
    }

    public UserResponseDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_EMAIL_OR_PHONE.getMessage() + id));
    }

    public UserResponseDTO getUserByEmailOrPhone(String email, String phone) {
        return userRepository.findByEmailOrPhoneNumber(email, phone)
                .map(userMapper::toUserResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_EMAIL_OR_PHONE.getMessage()));
    }
}
