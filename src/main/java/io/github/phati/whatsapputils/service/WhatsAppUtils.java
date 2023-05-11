package io.github.phati.whatsapputils.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WhatsAppUtils {

    @Value("${communications.whatsapp.token}")
    private String token;

    public String sendWhatsAppTextMessage(String message, String recipient) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("recipient_type", "individual");
        requestBody.put("to", recipient);
        requestBody.put("type", "text");
        Map<String, String> text = new HashMap<>();
        text.put("body", message);
        requestBody.put("text", text);
        HttpEntity<Map> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange("https://whatsapp.turn.io/v1/messages", HttpMethod.POST, entity, String.class);
        String responseBody = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> data = null;
        try {
            data = mapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String id = null;
        if (data != null)
            id = ((List<Map<String, String>>) data.get("messages")).get(0).get("id");

        return id;
    }
}