package com.asanpardakht.atmemulator.security;

import com.asanpardakht.atmemulator.entity.Card;
import com.asanpardakht.atmemulator.exception.AtmException;
import com.asanpardakht.atmemulator.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerDetails implements UserDetailsService {
    private final CardRepository cardRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String pan = username.substring(0, 15);
        String pin = username.substring(15, 19);
        final Optional<Card> cardOptional = cardRepository.findByPanAndPin(pan, pin);

        if (cardOptional.isEmpty()) {
            throw new AtmException("User '" + username + "' not found", HttpStatus.UNAUTHORIZED);
        }

        return org.springframework.security.core.userdetails.User//
                .withUsername(username)//
                .password(pin)//
                .authorities("USER")//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }

}
