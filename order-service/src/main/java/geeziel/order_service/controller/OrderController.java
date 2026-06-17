package geeziel.order_service.controller;

import geeziel.order_service.dto.PaymentStartResponse;
import geeziel.order_service.service.OrderPaymentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderPaymentService orderPaymentService;

    public OrderController(OrderPaymentService orderPaymentService) {
        this.orderPaymentService = orderPaymentService;
    }

    @PostMapping("/orders/{orderId}/pay")
    public PaymentStartResponse pay(@PathVariable Long orderId) {
        return orderPaymentService.startPayment(orderId);
    }
}
