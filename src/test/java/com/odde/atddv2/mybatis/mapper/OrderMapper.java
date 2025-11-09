package com.odde.atddv2.mybatis.mapper;

import com.odde.atddv2.mybatis.entity.Order;
import com.odde.atddv2.mybatis.entity.OrderLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    void insertOrder(Order order);

    void insertOrderLine(OrderLine line);

    List<Order> findAll();

    List<OrderLine> findAllLines();
}
