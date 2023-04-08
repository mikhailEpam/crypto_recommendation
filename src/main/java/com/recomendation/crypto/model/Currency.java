package com.recomendation.crypto.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Currency {

    @CsvBindByName
    private Long timestamp;
    @CsvBindByName(column = "symbol")
    private String name;
    @CsvBindByName
    private BigDecimal price;
}
