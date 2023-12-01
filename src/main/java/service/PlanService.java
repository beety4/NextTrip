package service;

import java.util.ArrayList;

import dao.PlanDAO;
import dto.PlanDTO;

public class PlanService {
	private PlanDAO planDAO;
	
	public PlanService() {
		planDAO = new PlanDAO();
	}
	
	// 마지막 planNo 가져오기
	public int getLastNo(String id) {
		return planDAO.getLastNo(id);
	}
	
	// plan 만들기
	public int makePlan(String id) {
		return planDAO.makePlan(id);
	}
	
	// plan 이름 설정
	public int setPlanName(String id, String planName) {
		return planDAO.setPlanName(id, planName);
	}
	
	// plan 정보 가져오기
	public PlanDTO getPlan(int planNo) {
		return planDAO.getPlan(planNo);
	}
	
	// plan 목록 가져오기
	public ArrayList<PlanDTO> getPlanList(String id) {
		return planDAO.getPlanList(id);
	}
	
}
