package com.barber.reservation.config;

import com.barber.reservation.domain.User;
import com.barber.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String phoneOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrPhoneNumber(phoneOrEmail, phoneOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email or phone: " + phoneOrEmail));

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // Add basic :user role
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));


        // You might implement :admin role differently based on your business logic
        // This is just an example
        if ("admin@example.com".equals(user.getEmail())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getPhoneNumber(),
                user.getPassword(),
                authorities
        );
    }
}