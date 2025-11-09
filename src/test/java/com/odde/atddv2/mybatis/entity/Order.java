package com.odde.atddv2.mybatis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Accessors(chain = true)
public class Order {
    private Long id;
    private String code;
    private String productName;
    private String recipientName;
    private String recipientMobile;
    private String recipientAddress;
    private String deliverNo;
    private com.odde.atddv2.entity.Order.OrderStatus status;
    private BigDecimal total;
    private Instant deliveredAt;
}
