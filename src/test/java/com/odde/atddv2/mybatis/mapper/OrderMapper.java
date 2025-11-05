package com.odde.atddv2.mybatis.mapper;

import com.odde.atddv2.mybatis.entity.OrderLinePo;
import com.odde.atddv2.mybatis.entity.OrderPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    void insertOrder(OrderPo order);

    void insertOrderLine(OrderLinePo line);

    List<OrderPo> findAll();

    List<OrderLinePo> findAllLines();
}
