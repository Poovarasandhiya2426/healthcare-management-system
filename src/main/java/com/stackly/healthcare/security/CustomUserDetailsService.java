package com.stackly.healthcare.security;

import com.stackly.healthcare.entity.AppUser;
import com.stackly.healthcare.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        AppUser user = appUserRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found"));

        return new User(

                user.getUsername(),

                user.getPassword(),

                List.of(new SimpleGrantedAuthority(user.getRole()))

        );

    }

}