package geeziel.order_service.loaders;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class RulesLoader {

    private final VectorStore vectorStore;

    private static final Map<String, String> RULE_FILES = Map.of(
            "Order cancellation rules", "/rules/order-cancellation-rules.md",
            "Payment rules", "/rules/payment-rules.md"
    );

    @PostConstruct
    public void load() {

        vectorStore.delete("type == 'business-rules'");

        RULE_FILES.forEach((title, path) -> {
            try {
                loadDocument(title, path);
                log.info("Successfully added document to vector store. title={}, path={}", title, path);
            } catch (Exception e) {
                log.error("Exception during loading document. title={}, path={}", title, path, e);
                throw new IllegalStateException("Failed to load rules document: " + title, e);
            }
        });
    }

    private void loadDocument(String title, String path) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {

            if (inputStream == null) {
                throw new IllegalStateException("Rules file not found: " + path);
            }

            String content = new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );

            Document document = new Document(
                    content,
                    Map.of(
                            "type", "business-rules",
                            "title", title,
                            "source", path
                    )
            );

            vectorStore.add(List.of(document));
        }
    }
}