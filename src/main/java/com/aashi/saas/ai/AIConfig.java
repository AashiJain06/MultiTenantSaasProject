package com.aashi.saas.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {
	@Bean
    public OpenAiApi openAiApi(
            @Value("${spring.ai.openai.api-key}") String apiKey) {

        return OpenAiApi.builder()
                .apiKey(apiKey)
                .baseUrl("https://openrouter.ai/api")
//                .defaultHeaders(Map.of(
//                        "HTTP-Referer", "http://localhost:8080",
//                        "X-Title", "Debug Assistant"
//                ))
                .build();
    }


	    

}
