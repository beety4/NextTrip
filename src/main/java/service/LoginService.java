package service;

import dao.UserDAO;
import dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginService implements CommandHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		// 요청 Method 판별 후 메소드 실행
		if("GET".equals(request.getMethod())) {
			return processGet(request, response);
		} else {
			return processPost(request, response);
		}
	}
	
	// GetMapping("sign-in.do")
	private String processGet(HttpServletRequest request, HttpServletResponse response) {
		return "login";
	}
	
	// PostMapping("sign-in.do")
	private String processPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		UserDAO userDAO = new UserDAO();
		int result = userDAO.login(id, password);
		switch(result) {
			case 1:
				UserDTO userDTO = userDAO.getValueByID(id);
				String sNAME = userDTO.getName();
				String sIMG = userDTO.getImg();
				session.setAttribute("sID", id);
				session.setAttribute("sNAME", sNAME);
				session.setAttribute("sIMG", sIMG);
				return "redirect:index.do";
			case 0:
				return "redirect:sign-in.do?msg=201";
			case -2:
				return "redirect:sign-in.do?msg=202";
			case -1:
				return "redirect:sign-in.do?msg=999";
			default:
				return "redirect:sign-in.do?msg=999";
		}
	}

}
