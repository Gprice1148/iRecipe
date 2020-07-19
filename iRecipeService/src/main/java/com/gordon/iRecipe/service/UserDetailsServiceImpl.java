package com.gordon.iRecipe.service;

import static java.util.Collections.singletonList;

import com.gordon.iRecipe.exception.IRecipeException;
import com.gordon.iRecipe.model.User;
import com.gordon.iRecipe.repository.UserRepository;
import java.util.Collection;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional
            .orElseThrow(
                () -> new IRecipeException("No user found with the username: " + username));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
            user.getPassword(),
            user.isEnabled(), true, true,
            true, getAuthorities("USER"));

    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
