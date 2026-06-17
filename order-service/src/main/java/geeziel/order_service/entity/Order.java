package geeziel.order_service.entity;

import geeziel.order_service.dto.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal amount;

    protected Order() {
    }

    public Order(Long id,
                 String customerName,
                 OrderStatus status,
                 BigDecimal amount) {
        this.id = id;
        this.customerName = customerName;
        this.status = status;
        this.amount = amount;
    }

    public Order(String customerName,
                 BigDecimal amount) {
        this.customerName = customerName;
        this.status = OrderStatus.NEW;
        this.amount = amount;
    }

}
