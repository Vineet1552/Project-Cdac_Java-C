package com.cdac.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cdac.dao.UserDao;
import com.cdac.dao.WasherDao;
import com.cdac.entities.UserEntity;
import com.cdac.entities.WasherEntity;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private WasherDao washerDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Check if it's a User
        Optional<UserEntity> user = userDao.findByEmail(email);
        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(),
                user.get().getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.get().getRole().name()))
            );
        }

        // Check if it's a Washer
        Optional<WasherEntity> washer = washerDao.findByEmail(email);
        if (washer.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                washer.get().getEmail(),
                washer.get().getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_WASHER"))
            );
        }

        // If not found
        throw new UsernameNotFoundException("Email not found: " + email);
    }
}
