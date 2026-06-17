package geeziel.order_service.service;

import geeziel.order_service.tools.OrderPolicyTools;
import geeziel.order_service.tools.OrderTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AiAgentService {

    private final ChatClient chatClient;
    private final OrderTools orderTools;
    private final OrderPolicyTools orderPolicyTools;

    public AiAgentService(ChatClient.Builder chatClientBuilder,
                          OrderTools orderTools,
                          OrderPolicyTools orderPolicyTools) {
        this.chatClient = chatClientBuilder
                .defaultSystem("""
                        You are an order assistant.
                        Use tools whenever user asks about orders.
                        Never invent order data.
                        Use only allowed order statuses:
                        NEW, PAYMENT_PENDING, PROCESSING, PAID, CANCELLED.
                        Answer in Polish.
                        """)
                .build();
        this.orderTools = orderTools;
        this.orderPolicyTools = orderPolicyTools;
    }

    public String ask(String question) {
        ChatResponse response = chatClient
                .prompt()
                .user(question)
                .tools(orderTools, orderPolicyTools)
                .call()
                .chatResponse();

        log.info("ChatResponse: {}", response);

        return response.getResult().getOutput().getText();
    }
}
