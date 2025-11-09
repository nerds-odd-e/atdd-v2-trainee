package com.odde.atddv2.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderLine {
    private Long id;
    private String itemName;
    private BigDecimal price;
    private int quantity;
    private Long orderId;
}
