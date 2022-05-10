package com.asanpardakht.atmemulator.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequestDto {
    @NotEmpty(message = "شماره کارت اجباری است.")
    private String pan;
    @NotEmpty(message = "رمز اول کارت اجباری است.")
    private String pin;
}
