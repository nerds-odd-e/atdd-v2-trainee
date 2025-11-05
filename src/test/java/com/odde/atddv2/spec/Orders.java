package com.odde.atddv2.spec;

import com.github.leeonky.jfactory.Spec;
import com.github.leeonky.jfactory.Trait;
import com.odde.atddv2.entity.Order;
import com.odde.atddv2.entity.OrderLine;
import com.odde.atddv2.mybatis.entity.OrderLinePo;
import com.odde.atddv2.mybatis.entity.OrderPo;

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

    public static class 订单M extends Spec<OrderPo> {
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

    public static class 订单项M extends Spec<OrderLinePo> {
        @Override
        public void main() {
            property("id").ignore();
        }
    }
}
