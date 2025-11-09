package com.odde.atddv2.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderWithAssociation extends Order {
    private List<OrderLineWithAssociation> lines = new ArrayList<>();
}
