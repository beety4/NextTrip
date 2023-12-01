package controller;

import java.util.ArrayList;

import dto.PlanDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.PlanService;

public class PlanController implements CommandHandler {
	private PlanService planService;

	public PlanController() {
		planService = new PlanService();
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		// 서비스 묶음
		String uri = request.getRequestURI().substring(10);
		switch(uri) {
			case "makePlan.do": return makePlan(request, response);
			case "showPlan.do": return showPlan(request, response);
			case "changePlanName.do": return changePlanName(request, response);
			case "myPlan.do": return myPlan(request, response);
			default: return null;
		}
	}

	
	// RequestMapping(value = "makePlan.do")
	private String makePlan(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("sID");

		if(id == null) {
			return "redirect:error.do?msg=401";
		}
		int planNo = planService.makePlan(id);
		return "redirect:showPlan.do?planNo=" + planNo;
	}

	
	
	// RequestMapping(value = "showPlan.do")
	private String showPlan(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("sID");
		int planNo = Integer.parseInt(request.getParameter("planNo"));

		PlanDTO planDTO = planService.getPlan(planNo);
		if(id.equals(planDTO.getId()) == false) {
			return "redirect:error.do?msg=402";
		}
		
		request.setAttribute("planDTO", planDTO);
		return "showPlan";
	}

	
	
	// RequestMapping(value = "changePlanName.do")
	private String changePlanName(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("sID");
		String planName = request.getParameter("planName");
		if(id == null) {
			return "redirect:error.do?msg=401";
		}
		int result = planService.setPlanName(id, planName);
		return String.valueOf(result);
	}

	
	
	// RequestMapping(value = "myPlan.do")
	private String myPlan(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("sID");

		if(id == null) {
			return "redirect:error.do?msg=401";
		}
		
		ArrayList<PlanDTO> planList = planService.getPlanList(id);
		request.setAttribute("planList", planList);
		return "myPlan";
	}
}
