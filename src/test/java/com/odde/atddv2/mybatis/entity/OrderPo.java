package com.odde.atddv2.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderPo {
    private Long id;
    private String code;
    private String productName;
    private String recipientName;
    private String recipientMobile;
    private String recipientAddress;
    private String deliverNo;
    private String status;
    private BigDecimal total;
    private Instant deliveredAt;
    private List<OrderLinePo> lines = new ArrayList<>();
}
