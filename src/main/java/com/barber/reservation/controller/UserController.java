package com.barber.reservation.controller;

import com.barber.reservation.dto.request.LoginRequestDTO;
import com.barber.reservation.dto.request.UserRequestDTO;
import com.barber.reservation.dto.response.UserResponseDTO;
import com.barber.reservation.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDTO register(@Valid @RequestBody UserRequestDTO dto) {
        return userService.createUser(dto);
    }

    @PostMapping("/login")
    public UserResponseDTO login(@Valid @RequestBody LoginRequestDTO dto) {
        return userService.loginWithPhoneNumber(dto);
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id,
                                      @Valid @RequestBody UserRequestDTO dto) {
        return userService.updateUser(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    //todo admin panel controller
    @GetMapping("/by-contact")
    public UserResponseDTO getUserByEmailOrPhone(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone
    ) {
        return userService.getUserByEmailOrPhone(email, phone);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}