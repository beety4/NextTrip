package service;

import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import config.ApiConn;
import dto.RandomTourSpotDTO;

public class ApiService {
	private ApiConn apiConn = new ApiConn();
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public ArrayList<RandomTourSpotDTO> getIndexTour() {
		StringBuilder sb = new StringBuilder();
		sb.append("/locationBasedList1");
		sb.append("?numOfRows=3");
		sb.append("&pageNo=1");
		sb.append("&listYN=Y");
		sb.append("&arrange=A");
		sb.append("&mapX=126.6572");
		sb.append("&mapY=37.44913");
		sb.append("&radius=2500");
		sb.append("&contentTypeId=12");
		
		ArrayList<RandomTourSpotDTO> randomTourSpotList = new ArrayList<>();
		String apiData = apiConn.getJson(sb.toString());
		System.out.println(apiData);
		try {
			Map<String, Object> jsonMap = objectMapper.readValue(apiData, Map.class);
			Map<String, Object> 
			
			
			RandomTourSpotDTO randomTourSpotDTO = objectMapper.readValue(apiData,  RandomTourSpotDTO.class);
			randomTourSpotList.add(randomTourSpotDTO);
		}catch(Exception e) { 
			e.printStackTrace();
		}
		
		return randomTourSpotList;
	}
	



}
