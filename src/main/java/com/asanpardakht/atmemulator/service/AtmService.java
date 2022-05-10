package com.asanpardakht.atmemulator.service;

import com.asanpardakht.atmemulator.dto.LoginRequestDto;
import com.asanpardakht.atmemulator.entity.Card;
import com.asanpardakht.atmemulator.repository.CardRepository;
import com.asanpardakht.atmemulator.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AtmService {
    private final CardRepository cardRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AtmService(CardRepository cardRepository, JwtTokenProvider jwtTokenProvider) {
        this.cardRepository = cardRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public ResponseEntity login(LoginRequestDto request) {
        Optional<Card> cardOptional = cardRepository.findByPanAndPin(request.getPan(), request.getPin());
        if (cardOptional.isEmpty()) {
            return new ResponseEntity("Invalid customer!", HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(jwtTokenProvider.createToken(request.getPan(), request.getPin()));
    }

    public ResponseEntity getCustomer(String pan) {
        return ResponseEntity.ok("SUCCESS");
    }
}
