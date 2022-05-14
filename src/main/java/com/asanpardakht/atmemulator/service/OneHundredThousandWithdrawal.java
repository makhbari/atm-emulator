package com.asanpardakht.atmemulator.service;

import com.asanpardakht.atmemulator.dto.WithdrawResponseDto;
import com.asanpardakht.atmemulator.model.Currency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OneHundredThousandWithdrawal implements WithdrawChain {
    private WithdrawChain chain;

    @Override
    public void setNextChain(WithdrawChain nextChain) {
        this.chain = nextChain;
    }

    @Override
    public void withdraw(com.asanpardakht.atmemulator.model.Currency currency, WithdrawResponseDto responseDto) {
        if (currency.getAmount() >= 100000) {
            int num = currency.getAmount() / 100000;
            int remainder = currency.getAmount() % 100000;
            log.info("Withdrawing " + num + " 100000 rials note");
            responseDto.setOneHundredThousandNotesCount(num);
            if (remainder != 0) this.chain.withdraw(new Currency(remainder), responseDto);
        } else {
            this.chain.withdraw(currency, responseDto);
        }
    }
}
