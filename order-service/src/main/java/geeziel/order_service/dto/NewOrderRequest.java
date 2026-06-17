package geeziel.order_service.dto;

import java.math.BigDecimal;

public record NewOrderRequest (BigDecimal amount,
                               String customer) {
}
