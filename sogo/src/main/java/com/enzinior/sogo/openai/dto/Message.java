package com.enzinior.sogo.openai.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Message{
	private String role;
	private String content;
}
