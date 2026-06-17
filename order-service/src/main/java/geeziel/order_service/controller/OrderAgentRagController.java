package geeziel.order_service.controller;

import geeziel.order_service.service.OrderAgentRagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rag-agent")
@RequiredArgsConstructor
public class OrderAgentRagController {

    private final OrderAgentRagService orderAgentRagService;

    @GetMapping("/ask")
    public String ask(@RequestParam String q) {
        return orderAgentRagService.ask(q);
    }
}
