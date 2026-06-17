package geeziel.order_service.dto;

import geeziel.order_service.entity.Order;

import java.math.BigDecimal;

public record OrderDetails(
        String orderId,
        OrderStatus status,
        BigDecimal amount,
        String customer
) {

    public static OrderDetails from(Order order){
        return new OrderDetails(order.getId().toString(), order.getStatus(), order.getAmount(), order.getCustomerName());
    }

}
