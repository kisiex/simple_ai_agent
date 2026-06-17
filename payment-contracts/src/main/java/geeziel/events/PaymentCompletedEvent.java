package geeziel.events;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentCompletedEvent(
        Long orderId,
        String paymentId,
        BigDecimal amount,
        String currency,
        Instant occurredAt
) implements PaymentEvent {}
