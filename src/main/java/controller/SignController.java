package controller;

import dto.UserDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.MailService;
import service.SignService;

public class SignController implements CommandHandler {
	private SignService signService;
	private MailService mailService;

	public SignController() {
		signService = new SignService();
		mailService = new MailService();
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		// 서비스 묶음
		String uri = request.getRequestURI().substring(10);
		switch(uri) {
			case "sign-in.do": return signin(request, response);
			case "sign-up.do": return signup(request, response);
			case "myProfile.do": return myProfile(request, response);
			case "logout.do": return logout(request, response);
			case "sendMail.do": return sendMail(request, response);
			case "login/oauth2/code/google.do": return googleOAuth2(request,response);
			default: return null;
		}
	}

	
	
	// RequestMapping(value = "sign-in.do")
	private String signin(HttpServletRequest request, HttpServletResponse response) {
		// Method = Get
		if ("GET".equals(request.getMethod())) {
			HttpSession session = request.getSession();
			String sID = (String)session.getAttribute("sID");

			if(sID == null) {
				return "login";
			}
			return "redirect:index.do";
		}
		// Method = Post
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		int result = signService.login(id, password);
		switch (result) {
		case 1:
			UserDTO userDTO = signService.getUserDTO(id);
			String sNAME = userDTO.getName();
			String sIMG = userDTO.getImg();
			session.setAttribute("sID", id);
			session.setAttribute("sNAME", sNAME);
			session.setAttribute("sIMG", sIMG);
			session.setMaxInactiveInterval(60 * 60); // 60초 * 60 -> 1시간
			
			// 자동 로그인 체크 시 쿠키 저장
			String isMaintain = request.getParameter("tf");
			if(isMaintain != null) {
				Cookie cookie = new Cookie("cID", id);
				response.addCookie(cookie);
			}
			
			return "redirect:index.do";
		case 0:  return "redirect:error.do?msg=201";
		case -2: return "redirect:error.do?msg=202";
		case -1: return "redirect:error.do?msg=999";
		default: return "redirect:error.do?msg=999";
		}
	}

	
	
	// RequestMapping(value = "sign-up.do")
	private String signup(HttpServletRequest request, HttpServletResponse response) {
		// Method = Get
		if ("GET".equals(request.getMethod())) {
			HttpSession session = request.getSession();
			String sID = (String)session.getAttribute("sID");

			if(sID == null) {
				return "register";
			}
			return "redirect:index.do";
		}
		// Method = Post
		UserDTO userDTO = new UserDTO();
		userDTO.setId(request.getParameter("id"));
		userDTO.setPassword(request.getParameter("password"));
		userDTO.setName(request.getParameter("name"));
		userDTO.setBirth(request.getParameter("birth"));
		userDTO.setGender(Integer.parseInt(request.getParameter("gender")));
		userDTO.setEmail(request.getParameter("email"));
		
		int result = signService.register(userDTO);
		if(result == 1) {
			// 회원가입 성공 후 login 화면으로 Redirect
			return "redirect:sign-in.do";
		}
	
		// 회원가입 실패 시 Redirect 후, msg 로 에러코드 반환
		return "redirect:error.do?msg=101";
	}

	
	
	// RequestMapping(value = "myProfile.do")
	private String myProfile(HttpServletRequest request, HttpServletResponse response) {
		// Method = Get
		if ("GET".equals(request.getMethod())) {
			HttpSession session = request.getSession();
			String sID = (String)session.getAttribute("sID");
			if(sID == null) {
				return "redirect:error.do?msg=401";
			}
			
			UserDTO userDTO = signService.getUserDTO(sID);
			request.setAttribute("userDTO", userDTO);
			return "myProfile";
		}
		// Method = Post
		signService.editProfile(request, response);
		return "redirect:index.do";
	}

	
	
	// RequestMapping(value = "logout.do")
	private String logout(HttpServletRequest request, HttpServletResponse response) {
		// 세션 로그아웃 처리
		HttpSession session = request.getSession();
		session.invalidate();
		
		// 쿠키 로그아웃 처리
		Cookie[] cookies = request.getCookies();
	    if(cookies != null && cookies.length > 0){
	    	for(Cookie c : cookies){
	    		if(c.getName().equals("cID")){
	    			Cookie cookie = new Cookie("cID", "");
	    			cookie.setMaxAge(0);
	                response.addCookie(cookie);
	            }
	        }
	    }
	    
		return "redirect:index.do";
	}
	
	
	
	// ResponseBody
	// RequestMapping(value = "sendMail.do")
	private String sendMail(HttpServletRequest request, HttpServletResponse response) {
		// Method = POST
		if ("POST".equals(request.getMethod())) {
			String email = request.getParameter("email");
			String authKey = mailService.sendtoMail(email);
			return authKey;
		}
		return null;
	}
	
	
	// RequestMapping(value = "login/oauth2/code/google.do")
	private String googleOAuth2(HttpServletRequest request, HttpServletResponse response) {
		// Method = Get
		if ("GET".equals(request.getMethod())) {
			// redirectURL 로 code를 가지고 돌아온다면 login.jsp의 js 실행을 위해
			// 다시 login 으로 request를 forward하여 로그인 정보 받아오기
			return "login";
		}
		// Method = Post
		HttpSession session = request.getSession();
		String userInfo = request.getParameter("userInfo");
		UserDTO userDTO = signService.oAuthLogin(userInfo);
		
		session.setAttribute("sID", userDTO.getId());
		session.setAttribute("sNAME", userDTO.getName());
		session.setAttribute("sIMG", userDTO.getImg());
		session.setMaxInactiveInterval(60 * 60); // 60초 * 60 -> 1시간
		
		return "1";
	}

}
