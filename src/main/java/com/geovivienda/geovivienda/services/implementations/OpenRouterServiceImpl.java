package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.dtos.ChatMessageDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenRouterServiceImpl {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_KEY = "Aqui debes colocar tu API Key de OpenRouter (https://openrouter.ai/keys)";
    private final String URL = "https://openrouter.ai/api/v1/chat/completions";

    public String askOpenRouter(List<ChatMessageDTO> messages) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.set("HTTP-Referer", "https://localhost:4200");
        headers.set("X-Title", "ChatBotEva");

        Map<String, Object> request = new HashMap<>();
        request.put("model", "openai/gpt-3.5-turbo");
        request.put("messages", messages);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(URL, entity, Map.class);
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return (String) message.get("content");

        } catch (HttpClientErrorException e) {
            System.err.println("Error: " + e.getStatusCode());
            System.err.println("Respuesta: " + e.getResponseBodyAsString());
            return "⚠️ Error al conectar con OpenRouter.";
        }
    }
}
