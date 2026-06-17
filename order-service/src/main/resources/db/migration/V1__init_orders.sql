CREATE TABLE orders (
                        id BIGINT PRIMARY KEY,
                        customer_name VARCHAR(255),
                        status VARCHAR(50),
                        amount DECIMAL(10,2)
);

INSERT INTO orders(id, customer_name, status, amount)
VALUES (123, 'Adam', 'PAID', 249.99);

INSERT INTO orders(id, customer_name, status, amount)
VALUES (124, 'Jan', 'PROCESSING', 499.99);

INSERT INTO orders(id, customer_name, status, amount)
VALUES (125, 'Anna', 'CANCELLED', 99.99);