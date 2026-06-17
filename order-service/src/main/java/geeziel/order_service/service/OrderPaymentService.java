package geeziel.order_service.service;

import geeziel.order_service.dto.PaymentStartResponse;
import geeziel.order_service.entity.Order;
import geeziel.order_service.grpc.PaymentClient;
import geeziel.order_service.repository.OrderRepository;
import geeziel.payment.grpc.StartPaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderPaymentService {

    private final OrderRepository repository;
    private final PaymentClient paymentClient;

    public PaymentStartResponse startPayment(Long orderId) {

        Order order = repository.findById(orderId)
                .orElseThrow();

        StartPaymentResponse grpcResponse = paymentClient.startPayment(order);

        return new PaymentStartResponse(
                grpcResponse.getPaymentId(),
                grpcResponse.getStatus()
        );
    }
}
