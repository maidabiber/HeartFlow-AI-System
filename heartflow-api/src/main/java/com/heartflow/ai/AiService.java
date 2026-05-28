package com.heartflow.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartflow.ai.dto.AiRiskRequest;
import com.heartflow.ai.dto.AiRiskResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public AiRiskResponse analyzeRisk(AiRiskRequest request) {
    log.info("Pokrećem LOKALNU AI analizu sa povećanim timeoutom...");
    String prompt = buildPrompt(request);

    try {
        String url = "http://localhost:11434/api/generate";

        Map<String, Object> requestBodyMap = Map.of(
            "model", "llama3",
            "prompt", prompt,
            "stream", false,
            "format", "json"
        );
        
        String requestBody = objectMapper.writeValueAsString(requestBodyMap);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(java.time.Duration.ofMinutes(2)) 
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpClient httpClientWithTimeout = HttpClient.newBuilder()
                .connectTimeout(java.time.Duration.ofMinutes(1))
                .build();

        HttpResponse<String> response = httpClientWithTimeout.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            log.error("Ollama vratio grešku (Status {}): {}", response.statusCode(), response.body());
            throw new RuntimeException("Ollama greška: " + response.body());
        }

        return parseResponse(response.body());

    } catch (Exception e) {
        log.error("Greška pri pozivu lokalnog AI-ja", e);
        throw new RuntimeException("Greška pri analizi rizika: " + e.getMessage());
    }
}
    
    private String buildPrompt(AiRiskRequest r) {
        return """
                Ti si kardiološki AI asistent. Analiziraj sljedeće podatke pacijenta i vrati procjenu kardiovaskularnog rizika.

                PODACI PACIJENTA:
                - Starost: %d godina
                - Spol: %s
                - Krvni pritisak: %s mmHg
                - Srčana frekvencija: %d bpm
                - BMI: %.1f

                LABORATORIJSKI NALAZI:
                - Ukupni holesterol: %.1f mmol/L
                - LDL holesterol: %.1f mmol/L
                - HDL holesterol: %.1f mmol/L
                - Trigliceridi: %.1f mmol/L
                - Glikemija: %.1f mmol/L

                FAKTORI RIZIKA:
                - Pušač: %s
                - Dijabetičar: %s
                - Hipertenzija: %s
                - Porodična historija srčanih bolesti: %s

                Odgovori ISKLJUČIVO u sljedećem JSON formatu, bez ikakvog dodatnog teksta, bez uvodnih rečenica i bez markdown oznaka (nemoj stavljati ```json na početak niti tri kvačice na kraj):
                {
                  "riskLevel": "NIZAK",
                  "riskScore": 45,
                  "riskFactors": ["faktor1", "faktor2"],
                  "recommendations": ["preporuka1", "preporuka2"],
                  "summary": "Kratki sažetak od 2-3 rečenice za ljekara na bosanskom jeziku."
                }
                
                Napomena za 'riskLevel': koristi isključivo jednu od ovih vrijednosti: NIZAK, UMJEREN, VISOK, VRLO_VISOK.
                """
                .formatted(
                        r.getAge(),
                        "MALE".equals(r.getGender()) ? "Muški" : "Ženski",
                        r.getBloodPressure(),
                        r.getHeartRate(),
                        r.getBmi(),
                        nullSafe(r.getCholesterolTotal()),
                        nullSafe(r.getCholesterolLdl()),
                        nullSafe(r.getCholesterolHdl()),
                        nullSafe(r.getTriglycerides()),
                        nullSafe(r.getBloodSugar()),
                        bool(r.getSmoker()),
                        bool(r.getDiabetic()),
                        bool(r.getHypertension()),
                        bool(r.getFamilyHistory()));
    }

    private AiRiskResponse parseResponse(String responseBody) throws Exception {
    log.info("Primljen odgovor od lokalnog AI-ja.");
    
    System.out.println("==================================================");
    System.out.println("====== SIROVI ODGOVOR OD LLAME DIREKTNO: ======");
    System.out.println(responseBody);
    System.out.println("==================================================");

    JsonNode root = objectMapper.readTree(responseBody);
    String aiJson = root.path("response").asText().trim();
    
    System.out.println("====== IZVUČENI JSON ZA PARSIRANJE: ======");
    System.out.println(aiJson);
    System.out.println("==================================================");

    aiJson = aiJson.replaceAll("```json", "").replaceAll("```", "").trim();

    return objectMapper.readValue(aiJson, AiRiskResponse.class);
}

    private double nullSafe(Double value) {
        return value != null ? value : 0.0;
    }

    private String bool(Boolean value) {
        if (value == null)
            return "Nepoznato";
        return value ? "Da" : "Ne";
    }
}