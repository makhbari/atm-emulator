package com.asanpardakht.atmemulator.controller;

import com.asanpardakht.atmemulator.dto.CashDepositRequestDto;
import com.asanpardakht.atmemulator.dto.CashWithdrawalRequestDto;
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

    @GetMapping("/operations")
    @ApiOperation(value = "get operations")
    public ResponseEntity getOperations() {
        log.info("START getOperations");
        return atmService.getOperations();
    }

    @PostMapping("/withdraw")
    @ApiOperation(value = "cash withdrawal")
    public ResponseEntity withdraw(@RequestBody @Valid CashWithdrawalRequestDto request) {
        log.info("START withdraw");
        return atmService.withdraw(request);
    }

    @PostMapping("/deposit")
    @ApiOperation(value = "cash deposit")
    public ResponseEntity deposit(@RequestBody @Valid CashDepositRequestDto request) {
        log.info("START deposit");
        return atmService.deposit(request);
    }

    @GetMapping("/balance")
    @ApiOperation(value = "check balance")
    public ResponseEntity getBalance() {
        log.info("START getBalance");
        return atmService.getBalance();
    }
}
