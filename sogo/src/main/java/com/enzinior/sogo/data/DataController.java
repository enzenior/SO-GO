package com.enzinior.sogo.data;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/data")
public class DataController {

	private final DataService dataService;

	@PostMapping("")
	public void data() {
		dataService.dataSaving();
	}


}
