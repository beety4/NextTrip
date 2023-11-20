package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.Mysql;

public class PlanDAO {
	private Connection conn = Mysql.getConnection();

	// Plan 의 마지막 번호 가져오기
	public int getLastNo() {
		String query = "SELECT MAX(planNo) FROM plan_table";
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	// 계획 만들기 시 입력 되는
	public int register(String id) {
		String query = "INSERT INTO plan_table(id) VALUES(?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	

}
