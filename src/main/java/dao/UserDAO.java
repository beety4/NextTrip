package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.Mysql;
import config.CryptoModule;

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
			if(rs.next()) {
				if(rs.getString(1).equals(digest)) {
					return 1;
				} else {
					return 0;
				}
			}
			return -2;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// ID 으로 Name 가져오는 DAO
	public UserDTO getValueByID(String id) {
		String query = "SELECT * FROM user_table WHERE id = ?";
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			if(rs.next()) {
				UserDTO userDTO = new UserDTO();
				userDTO.setName(rs.getString(3));
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
			String query = "INSERT INTO user_table(id,password,name,birth,gender,email) VALUES(?,?,?,?,?,?)";
			try {
				PreparedStatement pstmt = conn.prepareStatement(query);
				String digest = cryptoModule.getSHA256(userDTO.getPassword());
				
				pstmt.setString(1, userDTO.getId());
				pstmt.setString(2, digest);
				pstmt.setString(3, userDTO.getName());
				pstmt.setString(4, userDTO.getBirth());
				pstmt.setInt(5, userDTO.getGender());
				pstmt.setString(6, userDTO.getEmail());

				return pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return -1;
		}
	
}
