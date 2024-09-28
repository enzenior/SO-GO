package com.enzinior.sogo.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enzinior.sogo.openai.service.SummaryService;
import com.enzinior.sogo.place.entity.Place;
import com.enzinior.sogo.place.repository.PlaceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DataService {

	private final PlaceRepository placeRepository;
	private final SummaryService summaryService;
	private final DataClient dataClient;

	private String MobileOS = "AND";
	private String MobileApp = "SOGO";
	@Value("${data.openapi.key}")
	private String serviceKey;
	private int numOfRows = 20;



	public void dataSaving() {

		int[] areaCode = new int[40];
		areaCode[1] = 25;

		String[] contentType = new String[4];
		contentType[0] = "12"; // 관광지
		contentType[1] = "14"; // 문화시설
		contentType[2] = "32"; // 숙박
		contentType[3] = "39"; // 음식점

		for (int idx = 1; idx<40; idx++) {
			int code = areaCode[idx];
			if (code > 0) {
				for (int i = 1; i <= 1; i++) {
					for (int j = 0; j < 1; j++) {
						int type = 4;
						if (j == 0 || j == 1) {
							type = 0;
						} else if (j == 2) {
							type = 2;
						} else {
							type = 1;
						}
						ResponseApi response = dataClient.findDefaultInfo(MobileOS, MobileApp, serviceKey,
							contentType[j], "json", String.valueOf(idx), String.valueOf(i), 1, numOfRows);
						List<ResponseApi.ItemDTO> list = response.getResponse().getBody().getItems().getItem();
						int cnt = 0;
						for (ResponseApi.ItemDTO itemDTO : list) {
							Place place = new Place();
							place.setAddress(itemDTO.getAddr1() + " " + itemDTO.getAddr2());
							place.setPlaceName(itemDTO.getTitle());
							place.setLat(Double.parseDouble(itemDTO.getMapy()));
							place.setLng(Double.parseDouble(itemDTO.getMapx()));
							place.setPlaceImgs(itemDTO.getFirstimage());
							place.setContentId(itemDTO.getContentid());
							place.setContentTypeId(itemDTO.getContenttypeid());
							place.setNumber(itemDTO.getTel());
							place.setType(type);

							String description =
								"장소 이름 : " + place.getPlaceName() + "\n" + "장소 상세 주소" + place.getAddress();
							String summary = summaryService.generateSummary(description);
							String[] summaryArray = summary.split("\n");
							if (summaryArray.length > 1) {
								place.setSummary(summaryArray[0].trim());
								place.setTag(summaryArray[1].trim());
							} else{
								place.setSummary("요약정보없음");
								place.setTag("#x,#x");
							}

							ResponseApiInfo responseApiInfo = dataClient.findIntroInfo(MobileOS, MobileApp, serviceKey,
								contentType[j], itemDTO.getContentid(), "json");
							if (responseApiInfo.getResponse().getBody().getItems() != null) {
								List<ResponseApiInfo.ItemInfoDTO> listInfo = responseApiInfo.getResponse().getBody().getItems().getItem();
								if (j == 0) {
									for (ResponseApiInfo.ItemInfoDTO itemInfoDTO : listInfo) {
										place.setTime(itemInfoDTO.getUsetime());
										place.setDate(itemInfoDTO.getRestdate());
										if (itemInfoDTO.getParking() != null) {
											place.setParking(true);
										}
										if (itemInfoDTO.getChkpet() != null) {
											place.setPet(true);
										}
									}
								} else if (j == 1) {
									for (ResponseApiInfo.ItemInfoDTO itemInfoDTO : listInfo) {
										place.setTime(itemInfoDTO.getUsetimeculture());
										place.setDate(itemInfoDTO.getRestdateculture());
										if (itemInfoDTO.getParkingculture() != null) {
											place.setParking(true);
										}
										if (itemInfoDTO.getChkpetculture() != null) {
											place.setPet(true);
										}
									}
								} else if (j == 2) {
									for (ResponseApiInfo.ItemInfoDTO itemInfoDTO : listInfo) {
										if (itemInfoDTO.getParkinglodging() != null) {
											place.setParking(true);
										}
										place.setWebsite(itemInfoDTO.getReservationurl());
									}
								} else {
									for (ResponseApiInfo.ItemInfoDTO itemInfoDTO : listInfo) {
										place.setTime(itemInfoDTO.getOpentimefood());
										place.setDate(itemInfoDTO.getRestdatefood());
										if (itemInfoDTO.getParkingfood() != null) {
											place.setParking(true);
										}
									}
								}
							}

							ResponseApiWith responseWith = dataClient.findWithInfo(MobileOS, MobileApp, serviceKey,
								itemDTO.getContentid(), "json");
							if (responseWith.getResponse().getBody().getItems() != null) {
								List<ResponseApiWith.ItemWithDTO> listInfo = responseWith.getResponse().getBody().getItems().getItem();

								for (ResponseApiWith.ItemWithDTO itemWithDTO : listInfo) {
									if (itemWithDTO.getParking() != null) {
										place.setParking(true);
									}
									if (itemWithDTO.getWheelchair() != null) {
										place.setWheelchair(true);
									}
									if (itemWithDTO.getElevator() != null) {
										place.setElevator(true);
									}
								}
							}

							placeRepository.save(place);
							System.out.println(cnt+"저장함");
							cnt++;
						}

					}

				}
			}
		}

	}

}

// areaCode[1] = 25;
// areaCode[2] = 10;
// areaCode[3] = 5;
// areaCode[4] = 9;
// areaCode[5] = 5;
// areaCode[6] = 16;
// areaCode[7] = 5;
// areaCode[8] = 1;
// areaCode[31] = 31;
// areaCode[32] = 18;
// areaCode[33] = 12;
// areaCode[34] = 15;
// areaCode[35] = 22;
// areaCode[36] = 20;
// areaCode[37] = 14;
// areaCode[38] = 22;
// areaCode[39] = 4;
