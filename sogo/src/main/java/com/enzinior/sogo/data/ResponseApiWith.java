package com.enzinior.sogo.data;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseApiWith {
	private ResponseWithDTO response;

	@Getter
	public static class ResponseWithDTO {
		private HeaderWithDTO header;
		private BodyWithDTO body;

	}

	@Setter
	@Getter
	public static class HeaderWithDTO {
		private String resultCode;
		private String resultMsg;

	}

	@Getter
	@Setter
	public static class BodyWithDTO {
		private Optional<ItemWith> items;
		private int numOfRows;
		private int pageNo;
		private int totalCount;
	}

	@Getter
	@Setter
	public static class ItemWith{
		private List<ItemWithDTO> item;
	}

	@Getter
	@Setter
	public static class ItemWithDTO {

		private String parking;
		private String wheelchair;
		private String elevator;

	}

}



