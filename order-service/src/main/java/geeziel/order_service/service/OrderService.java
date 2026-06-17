package geeziel.order_service.service;

import geeziel.order_service.dto.ChangeOrderStatusResponse;
import geeziel.order_service.dto.NewOrderRequest;
import geeziel.order_service.dto.OrderStatus;
import geeziel.order_service.entity.Order;
import geeziel.order_service.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order findOrder(Long id) {
        log.debug("Querying for order with id: " + id);

        return repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Order not found: " + id));
    }

    @Transactional
    public ChangeOrderStatusResponse markAsPaid(Long orderId) {

        Order order = repository.findById(orderId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Order not found: " + orderId));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Cancelled order cannot be paid");
        }

        OrderStatus previousStatus = order.getStatus();

        order.setStatus(OrderStatus.PAID);

        return new ChangeOrderStatusResponse(orderId, false, previousStatus, OrderStatus.PAID,
                "Order status change");
    }

    @Transactional
    public ChangeOrderStatusResponse updateStatus(Long orderId, OrderStatus status) {
        log.debug("updating status of order: " + orderId);

        Order order = repository.findById(orderId).orElseThrow();

        OrderStatus previousStatus = order.getStatus();

        if (OrderStatus.CANCELLED.equals(previousStatus) || OrderStatus.COMPLETED.equals(previousStatus)) {
            return new ChangeOrderStatusResponse(orderId, false, previousStatus, previousStatus,
                    "Order cannot be cancelled because status is " + previousStatus);
        }

        order.setStatus(status);

        return new ChangeOrderStatusResponse(orderId, false, previousStatus, status,
                "Order status change");
    }

    @Transactional
    public ChangeOrderStatusResponse cancelOrderIfEligible(Long orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow();

        OrderStatus previousStatus = order.getStatus();

        if (OrderStatus.PAID.equals(previousStatus) || OrderStatus.CANCELLED.equals(previousStatus) || OrderStatus.COMPLETED.equals(previousStatus)) {
            return new ChangeOrderStatusResponse(orderId, false, previousStatus, previousStatus,
                    "Order cannot be cancelled because status is " + previousStatus);
        }

        if (order.getAmount().compareTo(BigDecimal.valueOf(500)) >= 0) {
            return new ChangeOrderStatusResponse(orderId, false, previousStatus, previousStatus,
                    "Order amount is too high");
        }

        order.setStatus(OrderStatus.CANCELLED);

        return new ChangeOrderStatusResponse(orderId, true, previousStatus, order.getStatus(),
                "Order cancelled");
    }

    @Transactional
    public Order createOrder(NewOrderRequest orderRequest) {
        var order = new Order(orderRequest.customer(), orderRequest.amount());
        return repository.save(order);
    }

}
