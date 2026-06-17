package geeziel.order_service.controller;

import geeziel.order_service.dto.AgentRequest;
import geeziel.order_service.dto.AgentResponse;
import geeziel.order_service.service.AiAgentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agent")
public class AiAgentController {

    private final AiAgentService aiAgentService;

    public AiAgentController(AiAgentService aiAgentService) {
        this.aiAgentService = aiAgentService;
    }

    @PostMapping("/ask")
    public AgentResponse ask(@RequestBody AgentRequest request) {
        return new AgentResponse(aiAgentService.ask(request.question()));
    }

}
