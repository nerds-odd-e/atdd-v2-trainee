package com.odde.atddv2.mybatis.entity;

import com.odde.atddv2.EntityFactory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderLineWithAssociation extends OrderLine {
    private OrderWithAssociation order;

    @Override
    public Long getOrderId() {
        Order order = getOrder();
        return order != null ? order.getId() : getOrderId();
    }

    public OrderWithAssociation getOrder() {
        return order != null ? order : EntityFactory.runtimeInstance.type(OrderWithAssociation.class).property("id", getOrderId()).query();
    }
}
