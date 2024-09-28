package com.enzinior.sogo.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseApi {

	private ResponseDTO response;

	@Getter
	public static class ResponseDTO {
		private HeaderDTO header;
		private BodyDTO body;

	}

	@Setter
	@Getter
	public static class HeaderDTO {
		private String resultCode;
		private String resultMsg;

	}

	@Getter
	@Setter
	public static class BodyDTO {
		private Item items;
		private int numOfRows;
		private int pageNo;
		private int totalCount;
	}


	@Getter
	@Setter
	public static class Item{
		private List<ItemDTO> item;
	}


	@Getter
	@Setter
	public static class ItemDTO {

		private String title;
		private String addr1;
		private String addr2;
		private String mapy;
		private String mapx;
		private String firstimage;
		private String contenttypeid;
		private String contentid;
		private String tel;

	}

}

