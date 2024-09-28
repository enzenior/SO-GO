package com.enzinior.sogo.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "dataClient", url = "http://apis.data.go.kr/B551011")
public interface DataClient {

	@GetMapping(value = "/KorService1/areaBasedList1") // 지역 기반 리스트 조회
	ResponseApi findDefaultInfo(@RequestParam("MobileOS") String MobileOS, @RequestParam("MobileApp") String MobileApp,
		@RequestParam("serviceKey") String serviceKey, @RequestParam("contentTypeId") String contentTypeId,
		@RequestParam("_type") String returnType, @RequestParam("areaCode") String areaCode,
		@RequestParam("sigunguCode") String sigunguCode, @RequestParam("pageNo") int pageNo, @RequestParam("numOfRows") int numOfRows);

	@GetMapping(value = "/KorService1/detailIntro1") // 소개 정보 조회
	ResponseApiInfo findIntroInfo(@RequestParam("MobileOS") String MobileOS, @RequestParam("MobileApp") String MobileApp,
		@RequestParam("serviceKey") String serviceKey, @RequestParam("contentTypeId") String contentTypeId, @RequestParam("contentId") String contentId,
		@RequestParam("_type") String returnType);

	@GetMapping(value = "/KorWithService/detailWithTour1") // 무장애
	ResponseApiWith findWithInfo(@RequestParam("MobileOS") String MobileOS, @RequestParam("MobileApp") String MobileApp,
		@RequestParam("serviceKey") String serviceKey, @RequestParam("contentId") String contentId,
		@RequestParam("_type") String returnType);

	// @GetMapping(value = "/detailCommon1") // 공통 정보 조회

	// @GetMapping(value = "/detailInfo1") // 반복 정보 조회

	// @GetMapping(value = "/detailImage1") // 이미지정보 조회

}
