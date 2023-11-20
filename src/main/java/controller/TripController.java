package controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TripController implements CommandHandler {

	public TripController() {
		
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		// 서비스 묶음
		String uri = request.getRequestURI().substring(10);
		return switch(uri) {
			case "myTrip.do" -> myTrip(request, response);
			default -> null;
		};
	}

	// RequestMapping(value = "myTrip.do")
	private String myTrip(HttpServletRequest request, HttpServletResponse response) {
		// Method = Get
		if ("GET".equals(request.getMethod())) {
			return "myTrip";
		}
		// Method = Post
		return "myTrip";
	}
	
	
	
}

