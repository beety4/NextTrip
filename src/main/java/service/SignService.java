package service;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.UserDAO;
import dto.UserDTO;
import config.RandModule;

public class SignService {
	private UserDAO userDAO;
	private ObjectMapper objectMapper;
	
	public SignService() {
		userDAO = new UserDAO();
		objectMapper = new ObjectMapper();
	}
	
	public int login(String id, String password) {
		return userDAO.login(id, password);
	}
	
	public UserDTO getUserDTO(String id) {
		return userDAO.getUserDTO(id);
	}
	
	public int register(UserDTO userDTO) {
		return userDAO.register(userDTO);
	}
	
	// 메소드 오버로딩을 통한 프로필 편집 ( name, password )
	public int editProfile(String id, String name, String password) {
		return userDAO.editProfile(id, name, password);
	}
	
	// 메소드 오버로딩을 통한 프로필 편집 ( name, password, img )
	public int editProfile(String id, String name, String password, String img) {
		return userDAO.editProfile(id, name, password, img);
	}
	
	public UserDTO oAuthLogin(String userInfo) {
		RandModule randModule = new RandModule();
		String sub = null;
		String email = null;
		String picture = null;
		try {
			Map<String, Object> jsonMap = objectMapper.readValue(userInfo, Map.class);
			sub = (String)jsonMap.get("sub");
			email = (String)jsonMap.get("email");
			picture = (String)jsonMap.get("picture");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		// 이미 DB에 회원가입 되어 있다면
		UserDTO dbUserDTO = userDAO.getUserDTO(sub, "GOOGLE");
		if(dbUserDTO != null) {
			return dbUserDTO;
		}
		
		// 처음 로그인하는 유저라면 회원가입 진행
		UserDTO userDTO = UserDTO.builder()
				.id(sub)
				.password(randModule.getRndVal(7))	// 어짜피 google에서 인증을 받아옥에 랜덤값 처리
				.name(email.split("@")[0])
				.email(email)
				.img(picture)
				.principal("GOOGLE").build();
		
		register(userDTO);
		return userDAO.getUserDTO(sub, "GOOGLE");
	}
}
