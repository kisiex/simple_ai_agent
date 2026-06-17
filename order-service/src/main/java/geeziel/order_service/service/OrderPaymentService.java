package geeziel.order_service.service;

import geeziel.order_service.dto.OrderStatus;
import geeziel.order_service.dto.PaymentStartResponse;
import geeziel.order_service.entity.Order;
import geeziel.order_service.grpc.PaymentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static geeziel.order_service.dto.PaymentStatus.ORDER_ALREADY_PAID;
import static geeziel.order_service.dto.PaymentStatus.ORDER_CANCELLED;

@Service
@RequiredArgsConstructor
public class OrderPaymentService {

    private final OrderService orderService;
    private final PaymentClient paymentClient;

    public PaymentStartResponse payForOrder(Long orderId) {
        final Order order = orderService.findOrder(orderId);

        orderService.markAsProcessing(orderId);

        if (order.getStatus() == OrderStatus.PAID) {
            return new PaymentStartResponse(null, ORDER_ALREADY_PAID);
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            return new PaymentStartResponse(null, ORDER_CANCELLED);
        }

        return PaymentStartResponse.from(paymentClient.startPayment(order));
    }

}
