package controller;

import dto.UserDTO;
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
		return switch(uri) {
			case "sign-in.do" -> signin(request, response);
			case "sign-up.do" -> signup(request, response);
			case "myProfile.do" -> myProfile(request, response);
			case "logout.do" -> logout(request, response);
			case "sendMail.do" -> sendMail(request, response);
			default -> null;
		};
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
			return "redirect:index.do";
		case 0:  return "redirect:sign-in.do?msg=201";
		case -2: return "redirect:sign-in.do?msg=202";
		case -1: return "redirect:sign-in.do?msg=999";
		default: return "redirect:sign-in.do?msg=999";
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
		return "redirect:sign-up.do?msg=101";
	}

	
	
	// RequestMapping(value = "myProfile.do")
	private String myProfile(HttpServletRequest request, HttpServletResponse response) {
		// Method = Get
		if ("GET".equals(request.getMethod())) {
			HttpSession session = request.getSession();
			String sID = (String)session.getAttribute("sID");
			if(sID == null) {
				return "redirect:index.do";
			}
			
			UserDTO userDTO = signService.getUserDTO(sID);
			request.setAttribute("userDTO", userDTO);
			return "myProfile";
		}
		// Method = Post
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("sID");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String file = (String)request.getAttribute("file");
		
		if(file == null) {
			// 파일이 비어있으면 -> 이름,비번만 변경
			int result = signService.editProfile(id, name, password);
			if(result == 1) {
				return "redirect:myProfile.do";
			}
			return "redirect:myProfile.do?msg=301";
		}
		// 파일이 있으면 ->  파일 업로드 및 이름,비번 변경
		
		int result = signService.editProfile(id, name, password, file);
		if(result == 1) {
			return "redirect:myProfile.do";
		}
		return "redirect:myProfile.do?msg=301";
	}

	
	
	// RequestMapping(value = "logout.do")
	private String logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:index.do";
	}
	
	
	
	//
	private String sendMail(HttpServletRequest request, HttpServletResponse response) {
		// Method = POST
		if ("POST".equals(request.getMethod())) {
			String email = request.getParameter("email");
			String authKey = mailService.sendtoMail(email);
			return authKey;
		}
		return null;
	}

}
