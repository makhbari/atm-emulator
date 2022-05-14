package com.asanpardakht.atmemulator.service;

import com.asanpardakht.atmemulator.dto.WithdrawResponseDto;
import com.asanpardakht.atmemulator.model.Currency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FiveHundredThousandWithdrawal implements WithdrawChain {
    private WithdrawChain chain;

    @Override
    public void setNextChain(WithdrawChain nextChain) {
        this.chain = nextChain;
    }

    @Override
    public void withdraw(com.asanpardakht.atmemulator.model.Currency currency, WithdrawResponseDto responseDto) {
        if (currency.getAmount() >= 500000) {
            int num = currency.getAmount() / 500000;
            int remainder = currency.getAmount() % 500000;
            log.info("Withdrawing " + num + " 500000 rials note");
            responseDto.setFiveHundredThousandNotesCount(num);
            if (remainder != 0) {
                this.chain.withdraw(new Currency(remainder), responseDto);
            }
        } else {
            this.chain.withdraw(currency, responseDto);
        }
    }
}
