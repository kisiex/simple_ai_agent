package geeziel.order_service.tools;

import geeziel.order_service.dto.*;
import geeziel.order_service.service.OrderPaymentService;
import geeziel.order_service.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderTools {

    private final OrderService orderService;
    private final OrderPaymentService orderPaymentService;

    public OrderTools(OrderService orderService, OrderPaymentService orderPaymentService) {
        this.orderService = orderService;
        this.orderPaymentService = orderPaymentService;
    }

    @Tool(description = """
            Find order by id.
            Returns order details including id, customer, status and amount.
            Use this tool when user asks about order existence, status, customer or price.
            """)
    public OrderDetails findOrder(Long orderId) {
        return OrderDetails.from(orderService.findOrder(orderId));
    }

    @Tool(description = """
            Cancel order only if it is eligible according to cancellation policy.
            Backend validates all rules.
            """)
    public ChangeOrderStatusResponse cancelOrderIfEligible(Long orderId) {
        return orderService.cancelOrderIfEligible(orderId);
    }

    @Tool(description = """
            Update order status by id.
            
            Allowed statuses:
            - NEW: new order
            - PAYMENT_PENDING: order is waiting for payment
            - PROCESSING: order is being processed
            - PAID: order has been paid
            - CANCELLED: order has been cancelled
            
            Mapping:
            - "oczekuje na płatność", "awaiting payment", "pending payment", "waiting for payment" -> PAYMENT_PENDING
            
            Rules:
            - NEW status is allowed only for new orders, you cannot change status of order to NEW
            
            Never invent statuses. Use only allowed enum values.
            Returns previous status, current status and update result.
            """)
    public ChangeOrderStatusResponse updateStatus(Long orderId, OrderStatus status) {
        return orderService.updateStatus(orderId, status);
    }

    @Tool(description = """
            Create a new order.
            Required input:
            - customer name
            - amount
            
            New orders are always created with status NEW.
            Returns created order details including generated id.
            """)
    public OrderDetails createOrder(NewOrderRequest orderRequest) {
        return OrderDetails.from(orderService.createOrder(orderRequest));
    }

    @Tool(description = """
        Start payment for an order by id.
        Use this tool when user wants to pay for an order.
        Payment can be started only if order is not PAID and not CANCELLED and not COMPLETED.
        Before process payment for order you have to change its status to PROCESSING
        """)
    public PaymentStartResponse payForOrder(Long orderId) {
        return orderPaymentService.payForOrder(orderId);
    }

}