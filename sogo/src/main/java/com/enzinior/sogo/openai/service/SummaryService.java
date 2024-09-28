package com.enzinior.sogo.openai.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.enzinior.sogo.openai.client.OpenAIClient;
import com.enzinior.sogo.openai.dto.Message;
import com.enzinior.sogo.openai.dto.OpenAIRequest;
import com.enzinior.sogo.openai.dto.OpenAIResponse;

@Service
public class SummaryService {

	@Autowired
	private OpenAIClient openAIClient;

	@Value("${openai.api.key}")
	private String apiKey;

	@Value("${openai.model}")
	private String model;

	String systemPrompt = "너는 이제부터 여행 사이트를 운영하는 운영자야.\n"
		+ "너는 새로운 장소에 대한 정보가 들어올때마다 해당 장소에 대한 한줄 요약과 2개의 태그를 작성해서 넘겨야해.\n"
		+ "한줄 요약에는 해당 장소에 대해 사람들이 읽고 어떤 장소인지 유추할 수 있을 정도로 정보를 축약해서 넣어야해.\n"
		+ "그래서 너는 다음과 같은 과정을 거쳐 설명과 태그를 생성하려해.\n"
		+ "\n"
		+ "한줄 요약에는 어떤 장소인지에 대한 설명이 들어가 있어야 해. 길이는 길지 않게 15 글자 내로 작성해줘. 장소의 종류에 대한 설명과 특별한 부분이 있다면 그런 내용이 보여질 수 있도록 작성해줘. 한줄 요약이라는 말은 굳이 앞에 붙일 필요 없이 내용만 줘\n"
		+ "\n"
		+ "그리고 해당 한줄 설명을 \"#단어,#단어\" 형태로 줄여서 줘. '단어' 부분에 5글자 이하의 장소를 표현할 수 있는 단어를 넣어줘 줘. 형태는 반드시 \"#단어,#단어\"의 \"\"안의 형태여야 해.\n"
		+ "\"\"는 전부 빼서 제공할거야.\n"
		+ "\n"
		+ "앞으로 들어오는 설명들에 대해 위의 형식으로 반환해주고, 꼭 중간에 한 줄 띄우지 말고 바로 아랫줄에 나오도록 반환해줘\n"
		+ "\n"
		+ "한줄요약\n"
		+ "#단어,#단어\n"
		+ "\n"
		+ "위처럼 반환해줘.\n"
		+ "\n";

	public String generateSummary(String inputText) {

		List<Message> prompts = new ArrayList<>();

		prompts.add(Message.builder()
			.role("system")
			.content(systemPrompt)
			.build());
		prompts.add(Message.builder()
			.role("user")
			.content(inputText)
			.build());

		OpenAIRequest request = OpenAIRequest.builder()
			.model(model)
			.messages(prompts)
			.max_tokens(50)
			.temperature(0.7)
			.build();

		OpenAIResponse response = openAIClient.generateSummary("Bearer " + apiKey, request);

		if (response != null && !response.getChoices().isEmpty()) {
			return response.getChoices().get(0).getMessage().getContent();
		}
		return "장소요약\n#태그1,#태그2";
	}
}
