package com.techie.librarymanagementsystem.config;

import com.techie.librarymanagementsystem.entities.Authority;
import com.techie.librarymanagementsystem.entities.User;
import com.techie.librarymanagementsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LmsAuthProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public LmsAuthProvider(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("No user registered with this email!"));

        if (passwordEncoder.matches(password, user.getPassword())) {
            Set<String> userAuthorities = user.getAuthorities().stream()
                    .map(Authority::getAuthority)
                    .collect(Collectors.toSet());
            List<GrantedAuthority> authorities = userAuthorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(email, password, authorities);
        } else {
            throw new BadCredentialsException("Invalid password!");
        }
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
