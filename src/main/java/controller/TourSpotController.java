package controller;

import java.util.ArrayList;

import dto.TourSpotDTO;
import dto.TourSpotDetailDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ApiService;

public class TourSpotController implements CommandHandler {
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		// 서비스 묶음
		String uri = request.getRequestURI().substring(10);
		switch(uri) {
			case "showTourSpot.do": return showTourSpot(request, response);
			case "showTourSpotDetail.do": return showTourSpotDetail(request, response);
			default: return null;
		}
	}
	
	
	// RequestMapping(value = "showTourSpot.do")
	private String showTourSpot(HttpServletRequest request, HttpServletResponse response) {
		ApiService apiService = new ApiService();
		ArrayList<TourSpotDTO> randomTourSpotList =  apiService.getIndexTour();
		
		request.setAttribute("randomTourSpotList", randomTourSpotList);
		return "showTourSpot";
	}
	
	// RequestMapping(value = "showTourSpotDetail.do")
	private String showTourSpotDetail(HttpServletRequest request, HttpServletResponse response) {
		ApiService apiService = new ApiService();
		String contentid = request.getParameter("contentid");		
		TourSpotDetailDTO tourSpotDetailDTO = apiService.getDetailTourSpot(contentid);

		request.setAttribute("tourSpotDetailDTO", tourSpotDetailDTO);
		return "showTourSpotDetail";
	}
	
	
}
