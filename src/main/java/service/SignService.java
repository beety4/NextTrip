package service;

import dao.UserDAO;
import dto.UserDTO;

public class SignService {
	private UserDAO userDAO;
	
	public SignService() {
		userDAO = new UserDAO();
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
}
