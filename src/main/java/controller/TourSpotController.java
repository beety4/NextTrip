package controller;

import java.net.URLEncoder;
import java.util.ArrayList;

import dto.tourAPI.TourSpotDetailDTO;
import dto.tourAPI.TourSpotSearchDTO;
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
		// Method = Get
		if ("GET".equals(request.getMethod())) {
			return "showTourSpot";
		}
		// Method = Post
		String keyword = null;
		try {
			keyword = URLEncoder.encode(request.getParameter("keyword"), "UTF-8");	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		String contentTypeId = request.getParameter("contentTypeId");
		ArrayList<TourSpotSearchDTO> tourSpotSearchList = apiService.getTourSpotSearch(keyword, contentTypeId);
		request.setAttribute("tourSpotSearchList", tourSpotSearchList);
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
