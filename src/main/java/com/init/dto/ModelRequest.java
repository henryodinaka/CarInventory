package com.init.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
