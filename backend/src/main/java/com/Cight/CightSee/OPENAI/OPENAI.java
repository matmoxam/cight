package com.Cight.CightSee.OPENAI;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class OPENAI {
    private static final String OPENAI_API_KEY = ""; // 🔥 Replace with your OpenAI API Key
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public String describeImage(byte[] imagearray) throws Exception {
    	
    	
        String base64Image = Base64.getEncoder().encodeToString(imagearray);       
        
        String requestBody = "{"
                + "\"model\": \"gpt-4o\","
                + "\"messages\": ["
                + "  {\"role\": \"system\", \"content\": \"Describe the image in detail.\"},"
                + "  {\"role\": \"user\", \"content\": ["
                + "    {"
                + "      \"type\": \"image_url\","
                + "      \"image_url\": { \"url\": \"data:image/jpeg;base64," + base64Image + "\" }"
                + "    }"
                + "  ]}"
                + "],"
                + "\"max_tokens\": 300"
                + "}";

        // ✅ Step 3: Send HTTP Request to OpenAI
        String responseText = sendRequest(requestBody);

        // ✅ Step 4: Parse and Display the Response
        return parseResponse(responseText);
    }

    private String sendRequest(String json) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(OPENAI_API_URL);
            post.setHeader("Authorization", "Bearer " + OPENAI_API_KEY);
            post.setHeader("Content-Type", "application/json");

            post.setEntity(new StringEntity(json));

            try (CloseableHttpResponse response = client.execute(post)) {
                return EntityUtils.toString(response.getEntity());
            }
        }
    }

    private String parseResponse(String response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);

        // ✅ Extract the image description text from the API response
        String description = rootNode.path("choices").get(0).path("message").path("content").asText();
        System.out.println("🖼️ Image Description: " + description);
        return description;
    }
}


