package com.barber.reservation.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public abstract class CustomUserDetailsService implements UserDetailsService {

}