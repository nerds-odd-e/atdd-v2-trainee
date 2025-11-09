package com.odde.atddv2.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderLineWithAssociation extends OrderLine {
    private OrderWithAssociation order;

    @Override
    public Long getOrderId() {
        return order.getId();
    }
}
