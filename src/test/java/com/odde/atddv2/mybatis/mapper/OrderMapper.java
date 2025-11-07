package com.odde.atddv2.mybatis.mapper;

import com.odde.atddv2.mybatis.entity.association.OrderLineMybatisWithAssociation;
import com.odde.atddv2.mybatis.entity.association.OrderMybatisWithAssociation;
import com.odde.atddv2.mybatis.entity.singletable.OrderLineMybatisSingle;
import com.odde.atddv2.mybatis.entity.singletable.OrderMybatisSingle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    void insertOrder(OrderMybatisWithAssociation order);

    void insertOrderLine(OrderLineMybatisWithAssociation line);

    List<OrderMybatisWithAssociation> findAll();

    List<OrderLineMybatisWithAssociation> findAllLines();

    void insertOrder2(OrderMybatisSingle order);

    void insertOrderLine2(OrderLineMybatisSingle line);

    List<OrderMybatisSingle> findAll2();

    List<OrderLineMybatisSingle> findAllLines2();
}
