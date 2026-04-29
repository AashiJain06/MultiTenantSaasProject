package com.aashi.saas.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service

public class AIService {
	
	ChatClient chatClient;
	   public AIService(ChatClient.Builder chatClient)
	   {
		   this.chatClient = chatClient.build();
	   }


    public String getAiSummary(String prompt) {
        return chatClient.prompt()
                .user("Say hi in spanish")
                .call()
                .content();
    }
    
    
    

}
