package controller;

import java.util.ArrayList;

import dto.tourAPI.TourSpotDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ApiService;

public class DefaultController implements CommandHandler {
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		// 서비스 묶음
		String uri = request.getRequestURI().substring(10);
		switch(uri) {
			case "index.do": return index(request, response);
			case "error.do": return error(request, response);
			default: return null;
		}
	}
	
	
	// RequestMapping(value = "index.do")
	private String index(HttpServletRequest request, HttpServletResponse response) {
		ApiService apiService = new ApiService();
		ArrayList<TourSpotDTO> randomTourSpotList =  apiService.getIndexTour();
		
		request.setAttribute("randomTourSpotList", randomTourSpotList);
		return "index";
	}
	
	
	// RequestMapping(value = "error.do")
	private String error(HttpServletRequest request, HttpServletResponse response) {
		return "error";
	}
	
}
