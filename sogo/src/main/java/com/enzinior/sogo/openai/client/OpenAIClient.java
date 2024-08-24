package com.enzinior.sogo.openai.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.enzinior.sogo.openai.dto.OpenAIRequest;
import com.enzinior.sogo.openai.dto.OpenAIResponse;

@FeignClient(name = "openAiClient", url = "https://api.openai.com/v1")
public interface OpenAIClient {

	@PostMapping("/completions")
	OpenAIResponse generateSummary(@RequestHeader("Authorization") String token, @RequestBody OpenAIRequest request);
}
