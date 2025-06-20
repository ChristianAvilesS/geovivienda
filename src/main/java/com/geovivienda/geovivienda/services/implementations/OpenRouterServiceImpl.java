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
    private final String API_KEY = "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";
    private final String URL = "https://openrouter.ai/api/v1/chat/completions";

    public String askOpenRouter(List<ChatMessageDTO> messages) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.set("HTTP-Referer", "https://localhost:4200"); // Requerido por OpenRouter
        headers.set("X-Title", "MiAppChatbot");              // Título personalizado (opcional)

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
