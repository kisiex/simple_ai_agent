package geeziel.order_service.kafka;

import geeziel.events.PaymentCompletedEvent;
import geeziel.order_service.service.OrderService;
import geeziel.topics.PaymentTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final OrderService orderService;

    @KafkaListener(
            topics = PaymentTopics.PAYMENT_EVENTS,
            groupId = "order-service"
    )
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        log.info("Received payment completed event: {}", event);
        orderService.markAsPaid(event.orderId());
    }
}
