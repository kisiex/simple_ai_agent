package geeziel.order_service.service;

import geeziel.order_service.tools.OrderTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderAgentRagService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;
    private final OrderTools orderTools;

    public OrderAgentRagService(
            ChatClient.Builder chatClientBuilder,
            VectorStore vectorStore,
            OrderTools orderTools
    ) {
        this.vectorStore = vectorStore;
        this.orderTools = orderTools;
        this.chatClient = chatClientBuilder.build();
    }

    public String ask(String question) {
        List<Document> documents = vectorStore.similaritySearch(question);

        String context = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        return chatClient
                .prompt()
                .system("""
                        You are an order assistant.

                        Use the provided business rules as context.
                        Use tools when you need real order data or need to perform actions.
                        Do not invent order data.
                        Business rules context:

                        %s
                        """.formatted(context))
                .user(question)
                .tools(orderTools)
                .call()
                .content();
    }
}