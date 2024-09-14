package com.enzinior.sogo.openai.dto;

import lombok.Data;

import java.util.List;

@Data
public class OpenAIResponse {
	private List<Choice> choices;

	@Data
	public static class Choice {
		private Message message;
	}
}
