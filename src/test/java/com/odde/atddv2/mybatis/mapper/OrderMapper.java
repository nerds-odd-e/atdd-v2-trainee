package com.odde.atddv2.mybatis.mapper;

import com.odde.atddv2.mybatis.entity.OrderLineMybatisWithAssociation;
import com.odde.atddv2.mybatis.entity.OrderMybatisWithAssociation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    void insertOrder(OrderMybatisWithAssociation order);

    void insertOrderLine(OrderLineMybatisWithAssociation line);

    List<OrderMybatisWithAssociation> findAll();

    List<OrderLineMybatisWithAssociation> findAllLines();
}
