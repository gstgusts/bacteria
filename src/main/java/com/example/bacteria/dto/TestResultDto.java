package com.example.bacteria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestResultDto {
    private int id;
    private String bacteriaName;
    private int categoryLimit;
    private int value;
    private int bacteriaId;
}
