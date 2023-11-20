package service;

import java.util.ArrayList;

import dao.PlanDAO;
import dto.PlanDTO;

public class PlanService {
	private PlanDAO planDAO;
	
	public PlanService() {
		planDAO = new PlanDAO();
	}
	
	public int getLastNo(String id) {
		return planDAO.getLastNo(id);
	}
	
	public int makePlan(String id) {
		return planDAO.makePlan(id);
	}
	
	public int setPlanName(String id, String planName) {
		return planDAO.setPlanName(id, planName);
	}
	
	public PlanDTO getPlan(int planNo) {
		return planDAO.getPlan(planNo);
	}
	
	public ArrayList<PlanDTO> getPlanList(String id) {
		return planDAO.getPlanList(id);
	}
	
}
