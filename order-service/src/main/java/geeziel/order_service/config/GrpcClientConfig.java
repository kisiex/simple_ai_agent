package geeziel.order_service.config;

import geeziel.payment.grpc.PaymentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Value("${grpc.payment.host}")
    private String host;

    @Value("${grpc.payment.port}")
    private int port;

    @Bean
    public ManagedChannel paymentChannel() {
        return ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
    }

    @Bean
    public PaymentServiceGrpc.PaymentServiceBlockingStub paymentStub(
            ManagedChannel paymentChannel) {

        return PaymentServiceGrpc.newBlockingStub(paymentChannel);
    }
}
