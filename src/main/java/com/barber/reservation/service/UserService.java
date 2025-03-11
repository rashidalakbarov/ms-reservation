package com.barber.reservation.service;

import java.util.List;
import java.util.stream.Collectors;
import com.barber.reservation.domain.User;
import com.barber.reservation.dto.UserDto;
import com.barber.reservation.exception.UserAlreadyExistsException;
import com.barber.reservation.mapper.UserMapper;
import com.barber.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    //change permission role
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public User createUser(UserDto userDto) {
        if (userRepository.existsById(userDto.getId())) {
            throw new UserAlreadyExistsException("User with ID " + userDto.getId() + " already exists.");
        }
        User user = userMapper.toUser(userDto);
        return userRepository.save(user);
    }


}