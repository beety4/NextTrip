package service;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import config.ApiConn;
import config.RandModule;
import dto.DefaultJson;
import dto.TourSpotDTO;
import dto.TourSpotDetailDTO;

public class ApiService {
	private ApiConn apiConn = new ApiConn();
	private ObjectMapper objectMapper = new ObjectMapper();
	
	// Index 화면에 표시될 지역별 랜덤 여행 정보 표시
	public ArrayList<TourSpotDTO> getIndexTour() {
		RandModule randModule = new RandModule();
		// API 요청 보낼 파라미터 설정
		StringBuilder sb = new StringBuilder();
		sb.append("/areaBasedSyncList1");						// 요청 주소
		sb.append("?numOfRows=6");								// 몇개
		sb.append("&pageNo=" + randModule.getRndInt(0, 28));	// 페이지 랜덤 선택
		sb.append("&showflag=1");								// 컨텐츠 표출
		sb.append("&listYN=Y");									// 리스트 목록 보기	
		sb.append("&arrange=O");								// A 제목순
		sb.append("&areaCode=" + randModule.getRndAreaCode());	// 랜덤 지역 코드
		sb.append("&contentTypeId=12");							// 타입 12 -> 관광지
		// 테스트용 인하공전위치 XY : 126.6572   37.44913
		
		// 결과값 담을 List 선언 및 apiConn을 통해 json 가져오기
		List<TourSpotDTO> tourSpotList = null;
		String apiData = apiConn.getJson(sb.toString());
		try {
			// json 데이터 파싱 하여 저장 후 List<T> 변환 하여 저장
			DefaultJson<TourSpotDTO> parsing =  objectMapper.readValue(apiData, new TypeReference<DefaultJson<TourSpotDTO>>() {});
			tourSpotList = parsing.getDTOList();
			System.out.println("Total Content : " + parsing.getTotalCnt());
		}catch(Exception e) { 
			e.printStackTrace();
		}
		
		return (ArrayList<TourSpotDTO>) tourSpotList;
	}
	
	
	// contentId 로 정보 상세보기
	public TourSpotDetailDTO getDetailTourSpot(String contentid) {
		StringBuilder sb = new StringBuilder();
		sb.append("/detailCommon1");
		sb.append("?contentId=" + contentid);
		sb.append("&defaultYN=Y");
		sb.append("&firstImageYN=Y");
		sb.append("&addrinfoYN=Y");
		sb.append("&mapinfoYN=Y");
		sb.append("&overviewYN=Y");
		
		TourSpotDetailDTO tourSpotDetailDTO = null;
		String apiData = apiConn.getJson(sb.toString());
		try {
			DefaultJson<TourSpotDetailDTO> parsing = objectMapper.readValue(apiData, new TypeReference<DefaultJson<TourSpotDetailDTO>>() {});
			tourSpotDetailDTO = parsing.getDTO();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return tourSpotDetailDTO;
	}

}
