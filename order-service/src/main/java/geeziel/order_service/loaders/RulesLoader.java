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

    @PostConstruct
    public void load() throws IOException {
        try (InputStream inputStream = getClass()
                .getResourceAsStream("/rules/order-cancellation-rules.md")) {

            if (inputStream == null) {
                throw new IllegalStateException("Rules file not found");
            }

            String content = new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );

            Document document = new Document(
                    content,
                    Map.of(
                            "type", "order-cancellation-rules",
                            "source", "order-cancellation-rules.md"
                    )
            );

            vectorStore.add(List.of(document));

            log.info("Loaded order cancellation rules into vector store");
        }
    }
}