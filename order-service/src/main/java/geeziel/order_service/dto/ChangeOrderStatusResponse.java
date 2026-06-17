package geeziel.order_service.dto;

public record ChangeOrderStatusResponse(
        Long orderId,
        boolean cancelled,
        OrderStatus previousStatus,
        OrderStatus currentStatus,
        String reason
) {
}
