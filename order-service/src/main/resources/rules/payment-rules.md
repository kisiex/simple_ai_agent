# Payment rules

Payment can be started only if:

1. The order exists.
2. The order status is NEW or PAYMENT_PENDING.
3. The order amount is greater than 0.
4. The order is not CANCELLED.
5. The order is not already PAID.

Payment should not be started if:

- The order status is PAID.
- The order status is CANCELLED.
- The order amount is 0 or lower.

When payment is completed successfully:

- Payment Service publishes PaymentCompletedEvent.
- Order Service consumes the event.
- Order status should be updated to PAID.

When payment fails:

- Payment Service publishes PaymentFailedEvent.
- Order status should not be changed to PAID.