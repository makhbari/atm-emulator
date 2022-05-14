package com.asanpardakht.atmemulator.dto;

import lombok.Data;

import java.util.List;

@Data
public class OperationResponseDto {
    private List<OperationInfoDto> operations;
}
