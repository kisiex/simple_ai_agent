package geeziel.paymentservice;

import geeziel.payment.grpc.PaymentServiceGrpc;
import geeziel.payment.grpc.StartPaymentRequest;
import geeziel.payment.grpc.StartPaymentResponse;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

import java.math.BigDecimal;
import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class PaymentGrpcService extends PaymentServiceGrpc.PaymentServiceImplBase {

    private final PaymentEventPublisher paymentEventPublisher;

    @Override
    public void startPayment(StartPaymentRequest request,
                             StreamObserver<StartPaymentResponse> responseObserver) {

        String paymentId = UUID.randomUUID().toString();

        paymentEventPublisher.publishPaymentCompleted(
                request.getOrderId(),
                paymentId,
                BigDecimal.valueOf(request.getAmount())
        );

        StartPaymentResponse response = StartPaymentResponse.newBuilder()
                .setPaymentId(paymentId)
                .setStatus("COMPLETED")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
