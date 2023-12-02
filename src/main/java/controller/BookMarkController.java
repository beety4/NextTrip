package controller;

import java.util.ArrayList;

import dto.tourAPI.TourSpotDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.BookMarkService;

public class BookMarkController implements CommandHandler {
	private BookMarkService bookMarkService;

	public BookMarkController() {
		bookMarkService = new BookMarkService();
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		// 서비스 묶음
		String uri = request.getRequestURI().substring(10);
		switch(uri) {
			case "bookmark.do": return bookmark(request, response);
			case "bookmarkAction.do": return bookmarkAction(request, response);
			case "bookmarkCheck.do": return bookmarkCheck(request, response);
			default: return null;
		}
	}

	
	// RequestMapping(value = "bookmark.do")
	private String bookmark(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("sNAME");

		if(name == null) {
			return "redirect:error.do?msg=401";
		}
		
		ArrayList<TourSpotDTO> bookmarkList = bookMarkService.getBookMarkList(name);
		request.setAttribute("bookmarkList", bookmarkList);
		return "bookmark";
	}
	
	
	// ResponseBody
	// RequestMapping(value = "bookmarkAction.do")
	private String bookmarkAction(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("sNAME");

		if(name == null) {
			return "2";
		}
		
		String contentid = request.getParameter("contentid");
		String firstimage = request.getParameter("firstimage");
		String addr1 = request.getParameter("addr1");
		String title = request.getParameter("title");
		
		TourSpotDTO tourSpotDTO = new TourSpotDTO();
		tourSpotDTO.setContentid(contentid);
		tourSpotDTO.setFirstimage(firstimage);
		tourSpotDTO.setAddr1(addr1);
		tourSpotDTO.setTitle(title);
		
		int result = bookMarkService.bookmarkAction(tourSpotDTO, name);
		return Integer.toString(result);
	}

	
	// ResponseBody
	// RequestMapping(value = "bookmarkCheck.do")
	private String bookmarkCheck(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("sNAME");

		if(name == null) {
			return "0";
		}
		
		String contentid = request.getParameter("contentid");
		return (bookMarkService.isCheck(contentid, name)) ? "1" : "0";
	}
	
	
	

	
}
