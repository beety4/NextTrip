package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.Mysql;
import dto.ReviewDTO;

public class ReviewDAO {
	private Connection conn = Mysql.getConnection();

	// Review 게시글의 마지막 번호 가져오기
	public int getLastNo() {
		String query = "SELECT MAX(planNo) FROM review_table WHERE available = '1'";
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			return rs.next() ? rs.getInt(1)+1 : 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// 게시글 작성하기
	public int addReview(ReviewDTO reviewDTO) {
		String query = "INSERT INTO plan_table(name, title, category, content) "
					 + "VALUES(?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, reviewDTO.getName());
			pstmt.setString(2, reviewDTO.getTitle());
			pstmt.setString(3, reviewDTO.getCategory());
			pstmt.setString(4, reviewDTO.getContent());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	
}
