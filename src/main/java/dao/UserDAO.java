package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.CryptoModule;
import config.Mysql;
import dto.UserDTO;

public class UserDAO {
	private Connection conn = Mysql.getConnection();
	private CryptoModule cryptoModule = new CryptoModule();

	// 로그인 DAO
	public int login(String id, String password) {
		String query = "SELECT password FROM user_table WHERE id = ?";
		ResultSet rs = null;
		String digest = cryptoModule.getSHA256(password);
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString(1).equals(digest)) {
					return 1;
				} else {
					return 0;
				}
			}
			return -2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	// ID 으로 UserDTO를 가져오는 DAO
	public UserDTO getUserDTO(String id) {
		String query = "SELECT * FROM user_table WHERE id = ?";
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				UserDTO userDTO = new UserDTO();
				userDTO.setId(rs.getString(1));
				userDTO.setName(rs.getString(3));
				userDTO.setBirth(rs.getString(4));
				userDTO.setGender(rs.getInt(5));
				userDTO.setEmail(rs.getString(6));
				userDTO.setJoindate(rs.getString(7));
				userDTO.setImg(rs.getString(8));
				return userDTO;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// ID 으로 UserDTO를 가져오는 DAO - 메소드 오버로딩
	public UserDTO getUserDTO(String id, String principal) {
		String query = "SELECT * FROM user_table WHERE id = ? AND principal = ?";
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, principal);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				UserDTO userDTO = new UserDTO();
				userDTO.setId(rs.getString(1));
				userDTO.setName(rs.getString(3));
				userDTO.setBirth(rs.getString(4));
				userDTO.setGender(rs.getInt(5));
				userDTO.setEmail(rs.getString(6));
				userDTO.setJoindate(rs.getString(7));
				userDTO.setImg(rs.getString(8));
				return userDTO;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 회원가입 DAO
	public int register(UserDTO userDTO) {
		String query = "INSERT INTO user_table(id,password,name,birth,gender,email,img,principal) VALUES(?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			String digest = cryptoModule.getSHA256(userDTO.getPassword());
			
			if(userDTO.getImg() == null) {
				userDTO.setImg("assets/img/profileIMG/default.png");
			}
			
			pstmt.setString(1, userDTO.getId());
			pstmt.setString(2, digest);
			pstmt.setString(3, userDTO.getName());
			pstmt.setString(4, userDTO.getBirth());
			pstmt.setInt(5, userDTO.getGender());
			pstmt.setString(6, userDTO.getEmail());
			pstmt.setString(7, userDTO.getImg());
			pstmt.setString(8, userDTO.getPrincipal());
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	
	
	// 메소드 오버로딩을 통한 프로필 편집 ( name, password )
	public int editProfile(UserDTO userDTO) {
		String SQL = "UPDATE user_table SET name = ?, password = ?, img = ? WHERE id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			String digest = cryptoModule.getSHA256(userDTO.getPassword());
			pstmt.setString(1, userDTO.getName());
			pstmt.setString(2, digest);
			pstmt.setString(3, userDTO.getImg());
			pstmt.setString(4, userDTO.getId());

			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	

}
