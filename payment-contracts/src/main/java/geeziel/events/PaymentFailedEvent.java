package geeziel.events;

import java.time.Instant;

public record PaymentFailedEvent(
        Long orderId,
        String paymentId,
        String reason,
        Instant occurredAt
) implements PaymentEvent {}