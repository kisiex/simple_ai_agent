package geeziel.order_service.controller;

import geeziel.order_service.service.OrderRulesSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rag")
public class RagController {

    private final OrderRulesSearchService searchService;

    @GetMapping("/search")
    public List<String> search(@RequestParam String q) {
        return searchService.search(q)
                .stream()
                .map(Document::getText)
                .toList();
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String q) {
        return searchService.askWithRules(q);
    }
}
