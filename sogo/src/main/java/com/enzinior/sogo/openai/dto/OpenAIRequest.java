package com.enzinior.sogo.openai.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenAIRequest {
	private String model = "gpt-3.5-turbo";
	private List<Message> messages;
	private int max_tokens;
	private double temperature;

}

