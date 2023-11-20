package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import config.Mysql;
import dto.PlanDTO;

public class PlanDAO {
	private Connection conn = Mysql.getConnection();

	// 해당 유저의 마지막 planNo 가져오기
	public int getLastNo(String id) {
		String query = "SELECT MAX(planNo) FROM plan_table WHERE id = ?";
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	// 계획 만들기 시 입력 되는 기본 폼
	public int makePlan(String id) {
		String query = "INSERT INTO plan_table(id) VALUES(?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			return getLastNo(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// PlanName 설정
	public int setPlanName(String id, String planName) { 
		String SQL = "UPDATE plan_table SET planName = ? WHERE planNo = ?";
		int lastNo = getLastNo(id);
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, planName);
			pstmt.setInt(2, lastNo);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// PlanDTO 가져오기
	public PlanDTO getPlan(int planNo) {
		String query = "SELECT * FROM plan_table WHERE planNo = ?";
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, planNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				PlanDTO planDTO = new PlanDTO();
				planDTO.setPlanNo(planNo);
				planDTO.setPlanName(rs.getString(2));
				planDTO.setPlanImg(rs.getString(3));
				planDTO.setId(rs.getString(4));
				planDTO.setStartDate(rs.getString(5));
				planDTO.setEndDate(rs.getString(6));
				return planDTO;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 사용자 id의 PlanDTO 가져오기
	public ArrayList<PlanDTO> getPlanList(String id) {
		String query = "SELECT * FROM plan_table WHERE id = ?";
		ArrayList<PlanDTO> planList = new ArrayList<>();
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				PlanDTO planDTO = new PlanDTO();
				planDTO.setPlanNo(rs.getInt(1));
				planDTO.setPlanName(rs.getString(2));
				planDTO.setPlanImg(rs.getString(3));
				planDTO.setId(rs.getString(4));
				planDTO.setStartDate(rs.getString(5));
				planDTO.setEndDate(rs.getString(6));
				planList.add(planDTO);
			}
			return planList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

}
