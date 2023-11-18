package service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProfileService implements CommandHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		// 요청 Method 판별 후 메소드 실행
		if("GET".equals(request.getMethod())) {
			return processGet(request, response);
		} else {
			return processPost(request, response);
		}
	}
	
	// GetMapping("myTrip.do")
	private String processGet(HttpServletRequest request, HttpServletResponse response) {
		return "myProfile";
	}
	
	// PostMapping("myTrip.do")
	private String processPost(HttpServletRequest request, HttpServletResponse response) {
		return "myProfile";
	}

}
