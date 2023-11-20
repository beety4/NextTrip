package controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PlanController implements CommandHandler {
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		// 서비스 묶음
		String uri = request.getRequestURI().substring(10);
		return switch(uri) {
			case "makePlan.do" -> makePlan(request, response);
			default -> null;
		};
	}
	
	
	// RequestMapping(value = "makePlan.do")
	private String makePlan(HttpServletRequest request, HttpServletResponse response) {
		return "makePlan";
	}
	
}
