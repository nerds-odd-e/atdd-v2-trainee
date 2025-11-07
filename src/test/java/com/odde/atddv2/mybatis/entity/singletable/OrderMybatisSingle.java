package com.odde.atddv2.mybatis.entity.singletable;

import com.odde.atddv2.mybatis.entity.association.OrderLineMybatisWithAssociation;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderMybatisSingle {
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
    private List<OrderLineMybatisWithAssociation> lines = new ArrayList<>();
}
