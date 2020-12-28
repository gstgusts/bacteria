package com.example.bacteria.dto;

import lombok.Data;

@Data
public class TestUpdateDto {
    private int testValue;
    private int testId;
    private int bacteriaId;
    private String bacteriaName;
    private int categoryLimit;
}
