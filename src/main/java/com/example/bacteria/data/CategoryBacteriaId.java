package com.example.bacteria.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryBacteriaId implements Serializable {
    private int category;
    private int bacteria;
}
