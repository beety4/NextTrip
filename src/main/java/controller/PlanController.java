package controller;

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
		return switch (uri) {
			case "makePlan.do" -> makePlan(request, response);
			case "showPlan.do" -> showPlan(request, response);
			case "changePlanName.do" -> changePlanName(request, response);
			default -> null;
		};
	}

	
	// RequestMapping(value = "makePlan.do")
	private String makePlan(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("sID");

		if(id == null) {
			return "redirect:index.do?msg=401";
		}
		int planNo = planService.makePlan(id);
		return "redirect:showPlan.do?planNo=" + planNo;
	}

	
	
	// RequestMapping(value = "showPlan.do")
	private String showPlan(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("sID");
		String planNo = request.getParameter("planNo");

		if(id == "getPlanID") {
			return "redirect:index.do?msg=402";
		}
		
		// get planDTO
		return "showPlan";
	}

	
	
	// RequestMapping(value = "changePlanName.do")
	private String changePlanName(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("sID");
		String planName = request.getParameter("planName");
		if(id == null) {
			return "redirect:index.do?msg=401";
		}
		int result = planService.setPlanName(id, planName);
		return String.valueOf(result);
	}

}
