package geeziel.order_service.tools;


import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class OrderPolicyTools {

    @Tool(description = "Returns business rules for cancelling orders")
    public String getOrderCancellationRules() {
        try (InputStream inputStream = getClass().getResourceAsStream("/rules/order-cancellation-rules.md")) {

            if (inputStream == null) {
                return "Cancellation rules document not found.";
            }

            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new IllegalStateException("Cannot read cancellation rules", e);
        }
    }
}
