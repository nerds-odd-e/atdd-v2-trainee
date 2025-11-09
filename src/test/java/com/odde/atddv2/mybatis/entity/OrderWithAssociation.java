package com.odde.atddv2.mybatis.entity;

import com.odde.atddv2.EntityFactory;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class OrderWithAssociation extends Order {
    private List<OrderLineWithAssociation> lines;

    public Collection<OrderLineWithAssociation> getLines() {
        return lines != null ? lines : EntityFactory.runtimeInstance.type(OrderLineWithAssociation.class).property("order.id", getId()).queryAll();
    }
}
