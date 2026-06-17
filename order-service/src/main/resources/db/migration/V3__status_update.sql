UPDATE orders
SET status = 'PAID'
WHERE status = 'paid';

UPDATE orders
SET status = 'PROCESSING'
WHERE status = 'processing';

UPDATE orders
SET status = 'CANCELLED'
WHERE status = 'cancel';