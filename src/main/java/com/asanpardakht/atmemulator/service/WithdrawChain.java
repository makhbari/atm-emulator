package com.asanpardakht.atmemulator.service;


import com.asanpardakht.atmemulator.dto.WithdrawResponseDto;

public interface WithdrawChain {
    void setNextChain(WithdrawChain nextChain);

    void withdraw(com.asanpardakht.atmemulator.model.Currency currency, WithdrawResponseDto responseDto);

}
