# Order cancellation rules

An order can be cancelled only if all of the following conditions are true:

1. The order status is not PAID.
2. The order status is not CANCELLED.
3. The order status is not COMPLETED.
4. The order amount is below 500.

Cancellation is forbidden if:

- The order status is PAID.
- The order status is COMPLETED.
- The order amount is 500 or higher.

Cancellation is not needed if:

- The order status is CANCELLED.