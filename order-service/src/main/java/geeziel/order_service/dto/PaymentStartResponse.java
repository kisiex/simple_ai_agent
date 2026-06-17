package geeziel.order_service.dto;

import geeziel.payment.grpc.StartPaymentResponse;

public record PaymentStartResponse(
        String paymentId,
        PaymentStatus status
) {
    public static PaymentStartResponse from(StartPaymentResponse response) {
        return new PaymentStartResponse(
                response.getPaymentId(),
                PaymentStatus.valueOf(response.getStatus())
        );
    }




}