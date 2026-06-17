package geeziel.paymentservice;

import geeziel.events.PaymentCompletedEvent;
import geeziel.events.PaymentEvent;
import geeziel.events.PaymentFailedEvent;
import geeziel.topics.PaymentTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentEventPublisher {

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public void publishPaymentCompleted(
            Long orderId,
            String paymentId,
            BigDecimal amount) {

        PaymentCompletedEvent event = new PaymentCompletedEvent(
                orderId,
                paymentId,
                amount,
                "PLN",
                Instant.now()
        );

        publish(orderId, event);
    }

    public void publishPaymentFailed(
            Long orderId,
            String paymentId,
            String reason) {

        PaymentFailedEvent event = new PaymentFailedEvent(
                orderId,
                paymentId,
                reason,
                Instant.now()
        );

        publish(orderId, event);
    }

    private void publish(Long orderId, PaymentEvent event) {
        kafkaTemplate.send(
                PaymentTopics.PAYMENT_EVENTS,
                orderId.toString(),
                event
        ).whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to publish payment event. orderId={}, event={}", orderId, event, ex);
            } else {
                log.info("Published payment event. orderId={}, topic={}, partition={}, offset={}",
                        orderId,
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });
    }
}