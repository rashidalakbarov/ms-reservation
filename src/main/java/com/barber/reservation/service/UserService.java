package com.barber.reservation.service;

import com.barber.reservation.domain.User;
import com.barber.reservation.dto.request.LoginRequestDTO;
import com.barber.reservation.dto.request.UserRequestDTO;
import com.barber.reservation.dto.response.UserResponseDTO;
import com.barber.reservation.exception.DuplicateResourceException;
import com.barber.reservation.exception.ResourceNotFoundException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.barber.reservation.mapper.UserMapper;
import com.barber.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.barber.reservation.constant.MessageConstant.EMAIL_ALREADY_IN_USE;
import static com.barber.reservation.constant.MessageConstant.INVALID_PHONE_NUMBER_OR_PASSWORD;
import static com.barber.reservation.constant.MessageConstant.PHONE_NUMBER_ALREADY_IN_USE;
import static com.barber.reservation.constant.MessageConstant.USER_NOT_FOUND_WITH_EMAIL_OR_PHONE;
import static com.barber.reservation.constant.MessageConstant.USER_NOT_FOUND_WITH_ID;

@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "users")
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Helper method to validate user email and phone number uniqueness
     *
     * @param email        Email to validate
     * @param phoneNumber  Phone number to validate
     * @param existingUser Existing user (for updates)
     */
    private void validateUserUniqueness(String email, String phoneNumber, User existingUser) {
        if (email != null && (existingUser == null || !email.equals(existingUser.getEmail()))
                && userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException(EMAIL_ALREADY_IN_USE.getMessage());
        }

        if (phoneNumber != null && (existingUser == null || !phoneNumber.equals(existingUser.getPhoneNumber()))
                && userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DuplicateResourceException(PHONE_NUMBER_ALREADY_IN_USE.getMessage());
        }
    }

    public UserResponseDTO createUser(UserRequestDTO dto) {
        // Use helper method for validation
        validateUserUniqueness(dto.getEmail(), dto.getPhoneNumber(), null);

        User user = userMapper.toEntity(dto);
        // Encode password for security
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User savedUser = userRepository.save(user);
        log.info("User created: ID={}, Phone={}", savedUser.getId(), savedUser.getPhoneNumber());
        return userMapper.toUserResponseDTO(savedUser);
    }

    public UserResponseDTO loginWithPhoneNumber(LoginRequestDTO dto) {
        User user = userRepository.findByPhoneNumber(dto.getPhoneNumber())
                .orElseThrow(() -> new ResourceNotFoundException(INVALID_PHONE_NUMBER_OR_PASSWORD.getMessage()));

        // Proper password verification using password encoder
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException(INVALID_PHONE_NUMBER_OR_PASSWORD.getMessage());
        }

        log.info("User logged in: ID={}, Phone={}", user.getId(), dto.getPhoneNumber());
        return userMapper.toUserResponseDTO(user);
    }

    @CacheEvict(key = "#id")
    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID.getMessage() + id));

        // Use helper method for validation
        validateUserUniqueness(dto.getEmail(), dto.getPhoneNumber(), user);

        boolean isModified = false;

        if (dto.getUsername() != null && !dto.getUsername().equals(user.getUsername())) {
            user.setUsername(dto.getUsername());
            isModified = true;
        }

        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
            user.setEmail(dto.getEmail());
            isModified = true;
        }

        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().equals(user.getPhoneNumber())) {
            user.setPhoneNumber(dto.getPhoneNumber());
            isModified = true;
        }

        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            isModified = true;
        }

        // Only save if something changed
        User updated = isModified ? userRepository.save(user) : user;
        log.info("User updated: ID={}, Email={}", updated.getId(), updated.getEmail());

        return userMapper.toUserResponseDTO(updated);
    }

    @CacheEvict(key = "#id")
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

    /**
     * Get all users with pagination support for better performance with large datasets
     *
     * @param pageable Pagination information
     * @return Page of user response DTOs
     */
    public Page<UserResponseDTO> getAllUsersPaged(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toUserResponseDTO);
    }

    @Cacheable(key = "#id")
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