package geeziel.events;


public sealed interface PaymentEvent
        permits PaymentCompletedEvent, PaymentFailedEvent {
}