package com.barber.reservation.config;

import com.barber.reservation.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public UserMapper userMapper(){

        return UserMapper.INSTANCE;
    }
}
