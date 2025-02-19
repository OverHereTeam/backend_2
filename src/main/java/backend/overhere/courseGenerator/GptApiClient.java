package backend.overhere.courseGenerator;


import backend.overhere.domain.TouristAttraction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class GptApiClient {
    @Value("${gpt.key}")
    private String API_KEY;

    private static final String ENDPOINT = "https://api.openai.com/v1/chat/completions";  // GPT API 엔드포인트

    public String sendRequest(List<TouristAttraction> selectedSpots) {
        try {
            // HTTP 클라이언트 생성
            HttpClient client = HttpClient.newHttpClient();

            // ObjectMapper 생성 (Jackson을 위한 JSON 처리 객체)
            ObjectMapper objectMapper = new ObjectMapper();

            TouristAttraction touristSpot1 = selectedSpots.get(0);
            TouristAttraction touristSpot2 = selectedSpots.get(1);
            TouristAttraction touristSpot3 = selectedSpots.get(2);
            TouristAttraction touristSpot4 = selectedSpots.get(3);
            TouristAttraction touristSpot5 = selectedSpots.get(4);

            String text = generateJson("prompt.txt", touristSpot1, touristSpot2, touristSpot3, touristSpot4,touristSpot5);


            // GPT 요청에 필요한 메시지 목록을 생성
            List<Message> messages = generateMessages("You are a helpful assistant.", text);

            // 요청 바디 생성
            String requestBody = objectMapper.writeValueAsString(new RequestBody("gpt-3.5-turbo", messages, 4096));

            // HTTP 요청 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ENDPOINT))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();


            //GPT API 호출 Limit 기다리기
            //Thread.sleep(5000);

            // 응답 받기
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 응답 출력
            if (response.statusCode() == 200) {
                // Jackson을 사용하여 JSON 응답 파싱
                JsonNode jsonResponse = objectMapper.readTree(response.body());
                String responseText = jsonResponse.path("choices").get(0).path("message").path("content").asText();
                System.out.println("GPT Response: " + responseText);
                return responseText;
            } else {
                System.out.println("Error: " + response.statusCode() + " - " + response.body());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("GPT API Error");
        }
    }


    // GPT API 요청 바디를 위한 클래스
    @Getter
    public static class RequestBody {
        private String model;
        private List<Message> messages;
        private int max_tokens;

        public RequestBody(String model,  List<Message> messages, int max_tokens) {
            this.model = model;
            this.messages = messages;
            this.max_tokens = max_tokens;
        }

        // 기본 생성자, 게터/세터는 Jackson이 자동으로 처리할 수 있도록 필요할 때 추가
    }
    // 템플릿 파일을 읽고, 동적으로 값을 삽입하는 메서드
    public static String generateJson(String templateFilePath, TouristAttraction... values) {
        try {
            // 템플릿 파일 읽기
            BufferedReader reader = new BufferedReader(new FileReader(templateFilePath));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();

            // 템플릿에 값 삽입
            return String.format(content.toString(),
                    values[0].getTitle(), values[0].getOverview(), values[0].getContentTypeId(),
                    values[1].getTitle(), values[1].getOverview(), values[1].getContentTypeId(),
                    values[2].getTitle(), values[2].getOverview(), values[2].getContentTypeId(),
                    values[3].getTitle(), values[3].getOverview(), values[3].getContentTypeId(),
                    values[4].getTitle(), values[4].getOverview(), values[4].getContentTypeId()
            );


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // GPT 메시지 객체 클래스
    @Getter
    public static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

    // 메시지 객체 리스트 생성
    private List<Message> generateMessages(String systemMessage, String userMessage) {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("system", systemMessage));
        messages.add(new Message("user", userMessage));
        return messages;
    }


}
