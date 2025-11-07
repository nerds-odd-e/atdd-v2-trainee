package com.odde.atddv2.mybatis.entity.singletable;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderMybatisSingleWithAssociation extends OrderMybatisSingle {
    private List<OrderLineMybatisSingleWithAssociation> lines = new ArrayList<>();
}
