package service;

import dao.UserDAO;
import dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class RegisterService implements CommandHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		// 요청 Method 판별 후 메소드 실행
		if("GET".equals(request.getMethod())) {
			return processGet(request, response);
		} else {
			return processPost(request, response);
		}
	}
	
	// 
	
	// GetMapping("sign-up.do")
	private String processGet(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String sID = (String)session.getAttribute("sID");
		
		// 로그인한 유저가 아니라면 회원가입 화면 이동
		if(sID == null) {
			return "register";
		}
		
		// 이미 로그인한 유저라면 index 화면으로 Redirect
		return "redirect:index.do";
	}
	
	// PostMapping("sign-up.do")
	private String processPost(HttpServletRequest request, HttpServletResponse response) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(request.getParameter("id"));
		userDTO.setPassword(request.getParameter("password"));
		userDTO.setName(request.getParameter("name"));
		userDTO.setBirth(request.getParameter("birth"));
		userDTO.setGender(Integer.parseInt(request.getParameter("gender")));
		userDTO.setEmail(request.getParameter("email"));
		
		
		UserDAO userDAO = new UserDAO();
		int result = userDAO.register(userDTO);
		if(result == 1) {
			// 회원가입 성공 후 login 화면으로 Redirect
			return "redirect:sign-in.do";
		}
	
		// 회원가입 실패 시 Redirect 후, msg 로 에러코드 반환
		return "redirect:sign-up.do?msg=101";
	}
	
}
