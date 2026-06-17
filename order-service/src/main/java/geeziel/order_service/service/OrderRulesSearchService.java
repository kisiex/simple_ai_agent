package geeziel.order_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderRulesSearchService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    @Autowired
    public OrderRulesSearchService(
            ChatClient.Builder chatClientBuilder,
            VectorStore vectorStore) {
        this.chatClient = chatClientBuilder.build();
        this.vectorStore = vectorStore;
    }

    public List<Document> search(String question) {
        return vectorStore.similaritySearch(question);
    }

    public String askWithRules(String question) {
        List<Document> documents = vectorStore.similaritySearch(question);

        String context = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        return chatClient.prompt()
                .system("""
                        Answer using only the provided context.
                        If the answer is not in the context, say that you don't know.
                        
                        Context:
                        %s
                        """.formatted(context))
                .user(question)
                .call()
                .content();
    }
}
