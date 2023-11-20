package controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ReviewController implements CommandHandler {
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		// 서비스 묶음
		String uri = request.getRequestURI().substring(10);
		return switch(uri) {
			case "tripReview.do" -> tripReview(request, response);
			default -> null;
		};
	}
	
	
	// RequestMapping(value = "makePlan.do")
	private String tripReview(HttpServletRequest request, HttpServletResponse response) {
		return "tripReview";
	}
	
}
