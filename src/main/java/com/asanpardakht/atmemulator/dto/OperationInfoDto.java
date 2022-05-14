package com.asanpardakht.atmemulator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationInfoDto {
    private AtmOperation operation;
    private String operationTitle;
}
