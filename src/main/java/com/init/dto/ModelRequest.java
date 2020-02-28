package com.init.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ModelRequest {

    private Long id;
    private Long brandId;
    private String name;
    private String year;
    private String exterior;
    private String interior;
    private int quantityInStock;
//    private String condition;
    private String price ;
}
