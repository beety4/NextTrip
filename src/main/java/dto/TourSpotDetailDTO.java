package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
// DTO에 선언한 파라미터만 가져오고 나머진 무시
public class TourSpotDetailDTO {
	private String contentid;
	private String infocenter;
	private String opendate;
	private String restdate;
	private String usetime;
	private String parking;
	
	private String title;
	private String homepage;
	private String firstimage;
	private String firstimage2;
	private String addr1;
	private String zipcode;
	private String mapx;
	private String mapy;
	private String overview;
}
