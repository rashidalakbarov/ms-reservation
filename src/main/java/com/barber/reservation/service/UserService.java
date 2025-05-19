package com.barber.reservation.service;

import com.barber.reservation.domain.User;
import com.barber.reservation.dto.request.LoginRequestDTO;
import com.barber.reservation.dto.request.UserRequestDTO;
import com.barber.reservation.dto.response.UserResponseDTO;
import com.barber.reservation.exception.DuplicateResourceException;
import com.barber.reservation.exception.ResourceNotFoundException;
import com.barber.reservation.mapper.UserMapper;
import com.barber.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static com.barber.reservation.constant.MessageConstant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public UserResponseDTO singUp(UserRequestDTO dto) {
        validateUserUniqueness(dto.getEmail(), dto.getPhoneNumber(), null);

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User saved = userRepository.save(user);
        log.info("User created: ID={}, Phone={}", saved.getId(), saved.getPhoneNumber());
        return userMapper.toUserResponseDTO(saved);
    }


    public UserResponseDTO singIn(LoginRequestDTO dto) {
        User user = userRepository.findByPhoneNumber(dto.getPhoneNumber())
                .orElseThrow(() -> new BadCredentialsException(INVALID_PHONE_NUMBER_OR_PASSWORD.getMessage()));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException(INVALID_PHONE_NUMBER_OR_PASSWORD.getMessage());
        }

        log.info("User logged in: ID={}, Phone={}", user.getId(), dto.getPhoneNumber());
        return userMapper.toUserResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO updateProfile(Long id, UserRequestDTO dto) {
        User user = findUserOrThrow(id);
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
        if (dto.getPassword() != null && !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            isModified = true;
        }

        User updated = isModified ? userRepository.save(user) : user;
        log.info("User updated: ID={}, Email={}", updated.getId(), updated.getEmail());
        return userMapper.toUserResponseDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        User user = findUserOrThrow(id);
        userRepository.delete(user);
        log.info("User deleted: ID={}, Phone={}", user.getId(), user.getPhoneNumber());
    }

    /* ——— Köməkçi metodlar ——— */

    /**
     * ID-yə görə User axtarır, tapılmadıqda ResourceNotFoundException atır.
     */
    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID.getMessage() + id)
                );
    }

    /**
     * Email və telefon nömrəsinin unikallığını yoxlayır.
     */
    private void validateUserUniqueness(String email, String phoneNumber, User existingUser) {
        if (email != null
                && (existingUser == null || !email.equals(existingUser.getEmail()))
                && userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException(EMAIL_ALREADY_IN_USE.getMessage());
        }

        if (phoneNumber != null
                && (existingUser == null || !phoneNumber.equals(existingUser.getPhoneNumber()))
                && userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DuplicateResourceException(PHONE_NUMBER_ALREADY_IN_USE.getMessage());
        }
    }
}
