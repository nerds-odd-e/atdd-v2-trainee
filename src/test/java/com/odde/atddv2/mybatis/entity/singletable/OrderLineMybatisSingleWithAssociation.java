package com.odde.atddv2.mybatis.entity.singletable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderLineMybatisSingleWithAssociation extends OrderLineMybatisSingle {
    private OrderMybatisSingle order;

    @Override
    public Long getOrderId() {
        return order.getId();
    }
}
