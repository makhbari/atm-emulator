package com.asanpardakht.atmemulator.controller;

import com.asanpardakht.atmemulator.dto.LoginRequestDto;
import com.asanpardakht.atmemulator.service.AtmService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/atm")
@Slf4j
public class AtmController {

    private final AtmService atmService;

    public AtmController(AtmService atmService) {
        this.atmService = atmService;
    }

    @PostMapping("/login")
    @ApiOperation(value = "login customer by pan and pin")
    public ResponseEntity login(@RequestBody @Valid LoginRequestDto request) {
        log.info("START login");
        return atmService.login(request);
    }

    @GetMapping("/customer/{pan}")
    @ApiOperation(value = "get customer by pan")
    public ResponseEntity getCustomer(@PathVariable String pan) {
        log.info("START getCustomer");
        return atmService.getCustomer(pan);
    }
}
