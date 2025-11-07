package com.odde.atddv2.spec;

import com.github.leeonky.jfactory.Spec;
import com.github.leeonky.jfactory.Trait;
import com.odde.atddv2.DALMockServer;
import com.odde.atddv2.ExpressResponse;
import com.odde.atddv2.entity.Order;

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

    public static class DefaultResponseBuilder extends Spec<DALMockServer.ResponseBuilder> {
        @Override
        public void main() {
            property("code").value(200);
            property("times").value(0);
        }
    }

    public static class 快递API响应 extends Spec<ExpressResponse> {
        @Override
        public void main() {
            property("status").value(0);
            property("msg").value("ok");
            property("result").byFactory();
        }
    }
}
