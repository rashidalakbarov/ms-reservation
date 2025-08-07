package com.barber.reservation.controller;

import com.barber.reservation.dto.request.LoginRequestDTO;
import com.barber.reservation.dto.request.UserRequestDTO;
import com.barber.reservation.dto.response.UserResponseDTO;
import com.barber.reservation.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public UserResponseDTO singUp(@Valid @RequestBody UserRequestDTO dto) {
        return userService.singUp(dto);
    }

    @PostMapping("/sing-in")
    public UserResponseDTO singIn(@Valid @RequestBody LoginRequestDTO dto) {
        return userService.singIn(dto);
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateProfile(@PathVariable Long id, @Valid @RequestBody UserRequestDTO dto) {
        return userService.updateProfile(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        userService.delete(id);
    }

}