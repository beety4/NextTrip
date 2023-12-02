package service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import config.RandModule;
import dao.SignDAO;
import dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SignService {
	private SignDAO signDAO;
	private ObjectMapper objectMapper;
	
	public SignService() {
		signDAO = new SignDAO();
		objectMapper = new ObjectMapper();
	}
	
	public int login(String id, String password) {
		return signDAO.login(id, password);
	}
	
	public UserDTO getUserDTO(String id) {
		return signDAO.getUserDTO(id);
	}
	
	public int register(UserDTO userDTO) {
		return signDAO.register(userDTO);
	}
	
	// 프로필 편집 
	public void editProfile(HttpServletRequest request, HttpServletResponse response) {
		String uploadPath = request.getServletContext().getRealPath("/") + "\\assets\\img\\profileIMG";
		int maxSize = 3 * 1024 * 1024;	// 3MB 제한
		
		try {
			// FileItem 오브젝트 생성용 객체
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setRepository(new File(uploadPath));
			factory.setSizeThreshold(maxSize);

			// 파일 업로드 핸들러 생성
			ServletFileUpload upload = new ServletFileUpload(factory);

			// request 객체의 데이터를 Map<String, List<FileItem> 형태로 파싱 이후 파라미터 가져오기
			Map<String, List<FileItem>> formMap = upload.parseParameterMap(request);
			String name = formMap.get("name").get(0).getString("UTF-8");
			String password = formMap.get("password").get(0).getString("UTF-8");
			String fileName = null;
			
			// 파일 첨부 시
			FileItem receiveFile = formMap.get("file").get(0);
			if(receiveFile.getSize() > 0) {
				// 업로드 된 파일 처리
				fileName = receiveFile.getName();
				File saveFile = new File(uploadPath + "\\" + fileName);
				receiveFile.write(saveFile);	// 업로드-저장
			}
			
			// DB 저장을 위한 객체 생성
			UserDTO userDTO = new UserDTO();
			HttpSession session = request.getSession();
			String id = (String)session.getAttribute("sID");
			userDTO.setId(id);
			userDTO.setName(name);
			userDTO.setPassword(password);
			userDTO.setImg((fileName == null) ? "assets\\img\\profileIMG\\default.png" : "assets\\img\\profileIMG\\" + fileName);
			
			// 내용 DB에 저장
			signDAO.editProfile(userDTO);
			
			// 세션값 재설정
			UserDTO changeDTO = getUserDTO(id);
			session.setAttribute("sID", changeDTO.getId());
			session.setAttribute("sNAME", changeDTO.getName());
			session.setAttribute("sIMG", changeDTO.getImg());
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		UserDTO dbUserDTO = signDAO.getUserDTO(sub, "GOOGLE");
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
		return signDAO.getUserDTO(sub, "GOOGLE");
	}
}
