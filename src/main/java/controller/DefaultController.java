package controller;

import java.util.ArrayList;

import dto.TourSpotDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ApiService;

public class DefaultController implements CommandHandler {
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		// 서비스 묶음
		String uri = request.getRequestURI().substring(10);
		return switch(uri) {
			case "index.do" -> index(request, response);
			default -> null;
		};
	}
	
	
	// RequestMapping(value = "index.do")
	private String index(HttpServletRequest request, HttpServletResponse response) {
		ApiService apiService = new ApiService();
		ArrayList<TourSpotDTO> randomTourSpotList =  apiService.getIndexTour();
		
		request.setAttribute("randomTourSpotList", randomTourSpotList);
		return "index";
	}
	
	
}
