package com.odde.atddv2.spec;

import com.github.leeonky.jfactory.Spec;
import com.github.leeonky.jfactory.Trait;
import com.odde.atddv2.entity.Order;
import com.odde.atddv2.entity.OrderLine;
import com.odde.atddv2.mybatis.entity.association.OrderLineMybatisWithAssociation;
import com.odde.atddv2.mybatis.entity.association.OrderMybatisWithAssociation;
import com.odde.atddv2.mybatis.entity.singletable.OrderLineMybatisSingleWithAssociation;
import com.odde.atddv2.mybatis.entity.singletable.OrderMybatisSingle;

import static com.odde.atddv2.entity.Order.OrderStatus.toBeDelivered;

public class Orders {
    public static class 订单 extends Spec<Order> {
        @Override
        public void main() {
            property("id").ignore();
        }

        @Trait
        public void 未发货的() {
            property("status").value(toBeDelivered);
            property("deliverNo").value(null);
            property("deliveredAt").value(null);
        }
    }

    public static class 订单项 extends Spec<OrderLine> {
        @Override
        public void main() {
            property("id").ignore();
        }
    }

    public static class Mybatis带关系_订单 extends Spec<OrderMybatisWithAssociation> {
        @Override
        public void main() {
            property("id").ignore();
            property("lines").reverseAssociation("order");
        }

        @Trait
        public void 未发货的() {
            property("status").value(toBeDelivered);
            property("deliverNo").value(null);
            property("deliveredAt").value(null);
        }
    }

    public static class Mybatis带关系_订单项 extends Spec<OrderLineMybatisWithAssociation> {
        @Override
        public void main() {
            property("id").ignore();
        }
    }


    public static class Mybatis单表_订单 extends Spec<OrderMybatisSingle> {
        @Override
        public void main() {
            property("id").ignore();
//            property("lines").reverseAssociation("order");
        }

        @Trait
        public void 未发货的() {
            property("status").value(toBeDelivered);
            property("deliverNo").value(null);
            property("deliveredAt").value(null);
        }
    }

    public static class Mybatis单表_订单项 extends Spec<OrderLineMybatisSingleWithAssociation> {
        @Override
        public void main() {
            property("id").ignore();
        }
    }
}
