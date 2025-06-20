package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.ChatRequestDTO;
import com.geovivienda.geovivienda.services.implementations.OpenRouterServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {

    private final OpenRouterServiceImpl chatService;

    public ChatController(OpenRouterServiceImpl chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<String> chat(@RequestBody ChatRequestDTO request) {
        String reply = chatService.askOpenRouter(request.getMessages());
        return ResponseEntity.ok(reply);
    }
}

