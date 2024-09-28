package com.enzinior.sogo.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseInfo{
	private ResponseInfoDTO response;

	@Getter
	public static class ResponseInfoDTO{
		private HeaderInfoDTO header;
		private BodyWithInfoDTO body;


	}
	@Setter
	@Getter
	public static class HeaderInfoDTO {
		private String resultCode;
		private String resultMsg;

	}
	@Getter
	@Setter
	public static class BodyWithInfoDTO {
		private ItemInfo items;
		private int numOfRows;
		private int pageNo;
		private int totalCount;
	}
	@Getter
	@Setter
	public static class ItemInfo{
		private List<ItemInfoDTO> item;
	}


	@Getter
	@Setter
	public static class ItemInfoDTO {

		// 12
		private String usetime;
		private String parking;
		private String restdate;
		private String chkpet;

		//14
		private String restdateculture;
		private String parkingculture;
		private String usetimeculture;
		private String chkpetculture;

		//34
		private String reservationurl;
		private String parkinglodging;

		//39
		private String restdatefood;
		private String opentimefood;
		private String parkingfood;

	}
}












