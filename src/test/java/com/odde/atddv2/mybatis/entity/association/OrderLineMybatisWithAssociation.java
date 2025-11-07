package com.odde.atddv2.mybatis.entity.association;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderLineMybatisWithAssociation {
    private Long id;
    private String itemName;
    private BigDecimal price;
    private int quantity;
    private OrderMybatisWithAssociation order;
}
