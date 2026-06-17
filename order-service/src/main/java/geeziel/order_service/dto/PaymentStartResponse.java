package geeziel.order_service.dto;

public record PaymentStartResponse(
        String paymentId,
        String status
) {}