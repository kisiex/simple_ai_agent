package geeziel.order_service.grpc;

import geeziel.order_service.entity.Order;
import geeziel.payment.grpc.PaymentServiceGrpc;
import geeziel.payment.grpc.StartPaymentRequest;
import geeziel.payment.grpc.StartPaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentClient {

    private final PaymentServiceGrpc.PaymentServiceBlockingStub paymentStub;

    public StartPaymentResponse startPayment(Order order) {

        StartPaymentRequest request =
                StartPaymentRequest.newBuilder()
                        .setOrderId(order.getId())
                        .setCustomerName(order.getCustomerName())
                        .setAmount(order.getAmount().doubleValue())
                        .build();

        return paymentStub.startPayment(request);
    }
}