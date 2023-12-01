package controller;

import java.io.IOException;
import java.util.ArrayList;

import dto.CommentDTO;
import dto.ReviewDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.ReviewService;

public class ReviewController implements CommandHandler {
	private ReviewService reviewService;
	
	public ReviewController() {
		reviewService = new ReviewService();
	}
	
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		// 서비스 묶음
		String uri = request.getRequestURI().substring(10);
		try {
			switch(uri) {
				case "tripReview.do": return tripReview(request, response);
				case "tripReviewSearch.do": return tripReviewSearch(request, response);
				case "tripReviewDetail.do": return tripReviewDetail(request, response);
				case "addReview.do": return addReview(request, response);
				case "editReview.do": return editReview(request, response);
				case "deleteReview.do": return deleteReview(request, response);
				case "addReviewComment.do": return addReviewComment(request, response);
				case "deleteReviewComment.do": return deleteReviewComment(request, response);
				case "addReviewLike.do": return addReviewLike(request, response);
				case "isLike.do": return isLike(request, response);
				default: return null;
			}
		}catch(Exception e) {
			return null;
		}
	}
	
	
	// RequestMapping(value = "tripReview.do")
	private String tripReview(HttpServletRequest request, HttpServletResponse response) {
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		
		// 페이징 처리
		ArrayList<ReviewDTO> reviewList = reviewService.getReviewList(pageNo, 0, null);
		request.setAttribute("reviewList", reviewList);
		request.setAttribute("totalPage", (int)Math.ceil((double)reviewService.getReviewCnt() / 10));
		request.setAttribute("pageNo", pageNo);
		return "tripReview";
	}
	
	// RequestMapping(value = "tripReviewSearch.do")
	private String tripReviewSearch(HttpServletRequest request, HttpServletResponse response) {
		int search = Integer.parseInt(request.getParameter("search"));
		String searchIt = request.getParameter("searchIt");
		
		if(searchIt == null) {
			return "redirect:error.do?msg=505";
		}

		// 페이징 처리
		ArrayList<ReviewDTO> reviewList = reviewService.getReviewList(1, search, searchIt);
		request.setAttribute("reviewList", reviewList);
		request.setAttribute("totalPage", 1);
		request.setAttribute("pageNo", 1);
		request.setAttribute("isSearch", true);
		return "tripReview";
	}
	
	
	// RequestMapping(value = "tripReviweDetail.do")
	private String tripReviewDetail(HttpServletRequest request, HttpServletResponse response) {
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
		
		ReviewDTO reviewDTO = reviewService.getReviewDetail(reviewNo);
		if(reviewDTO == null) {
			return "redirect:error.do?msg=504";
		}
		
		reviewDTO.setContent(reviewDTO.getContent().replace("\r\n", "<br>"));
		reviewService.addView(reviewNo);
		request.setAttribute("reviewDTO", reviewDTO);
		
		ArrayList<CommentDTO> commentList = reviewService.getCommentList(reviewNo);
		request.setAttribute("commentList", commentList);
		return "tripReviewDetail";
	}
	
	// RequestMapping(value = "addReview.do")
	private String addReview(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String name = (String)session.getAttribute("sNAME");
		if(name == null) {
			return "redirect:error.do?msg=401";
		}
		
		// Method = Get
		if ("GET".equals(request.getMethod())) {
			return "tripReviewAdd";
		}
		
		// Method = Post
		ReviewDTO reviewDTO = ReviewDTO.builder()
				.name((String)session.getAttribute("sNAME"))
				.title(request.getParameter("title"))
				.region(request.getParameter("region"))
				.content(request.getParameter("content")).build();
		
		reviewService.addReview(reviewDTO);
		return "redirect:tripReview.do?pageNo=1";
	}
	
	
	// RequestMapping(value = "editReview.do")
	private String editReview(HttpServletRequest request, HttpServletResponse response) {
		// Method = Get
		if ("GET".equals(request.getMethod())) {
			HttpSession session = request.getSession();
			int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
			String name = reviewService.getReviewDetail(reviewNo).getName();
			
			// 작성자가 본인이 아닌경우 수정 불가
			if(session.getAttribute("sNAME").equals(name) == false) {
				return "redirect:error.do?msg=503";
			}
			
			ReviewDTO reviewDTO = reviewService.getReviewDetail(reviewNo);
			request.setAttribute("reviewDTO", reviewDTO);
			request.setAttribute("isEdit", true);
			return "tripReviewAdd";
		}
		// Method = Post
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
		ReviewDTO reviewDTO = ReviewDTO.builder()
				.reviewNo(reviewNo)
				.title(request.getParameter("title"))
				.region(request.getParameter("region"))
				.content(request.getParameter("content")).build();
		
		reviewService.editReview(reviewDTO);
		return "redirect:tripReviewDetail.do?reviewNo=" + reviewNo;
	}
	
	
	// RequestMapping(value = "deleteReview.do")
	private String deleteReview(HttpServletRequest request, HttpServletResponse response) {
		// Method = Get
		if ("GET".equals(request.getMethod())) {
			// POST 용이니 GET요청은 거부
			return "redirect:tripReview.do?pageNo=1";
		}
		// Method = Post
		HttpSession session = request.getSession();
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
		String name = reviewService.getReviewDetail(reviewNo).getName();
		
		// 작성자가 본인이 아닌경우 수정 불가
		if(session.getAttribute("sNAME").equals(name) == false) {
			return "redirect:error.do?msg=503";
		}
		
		reviewService.deleteReview(reviewNo);
		return "redirect:tripReview.do?pageNo=1";
	}
	
	
	
	
	
	// RequestMapping(value = "addReviewComment.do")
	private String addReviewComment(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
		String content = request.getParameter("comment");
		
		if(session.getAttribute("sNAME") == null) {
			return "redirect:error.do?msg=401";
		}
		
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setReviewNo(reviewNo);
		commentDTO.setName((String)session.getAttribute("sNAME"));
		commentDTO.setContent(content);		
		
		reviewService.addReviewComment(commentDTO);
		return "redirect:tripReviewDetail.do?reviewNo=" + reviewNo;
	}
	
	// RequestMapping(value = "deleteReviewComment.do")
	private String deleteReviewComment(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
		int commentNo = Integer.parseInt(request.getParameter("commentNo"));
		
		// 내가 작성한 댓글인지 확인
		CommentDTO commentDTO = reviewService.getComment(commentNo);
		if(((String)session.getAttribute("sNAME")).equals(commentDTO.getName()) == false) {
			return "redirect:error.do?msg=506";
		}
		
		reviewService.deleteReviewComment(commentNo);
		return "redirect:tripReviewDetail.do?reviewNo=" + reviewNo;
	}
	
	

	// ResponseBody
	// RequestMapping(value = "addReviewLike.do")
	private String addReviewLike(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
		
		if(session.getAttribute("sNAME") == null) {
			return "redirect:error.do?msg=401";
		}
		String name = (String)session.getAttribute("sNAME");
		
		// 바뀐 좋아요 개수 반환
		int likeCnt = reviewService.likeAction(reviewNo, name);
		return Integer.toString(likeCnt);
	}
	
	// ResponseBody
	// RequestMapping(value = "isLike.do")
	private String isLike(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
		
		if(session.getAttribute("sNAME") == null) {
			return "redirect:error.do?msg=401";
		}
		String name = (String) session.getAttribute("sNAME");

		// 좋아요 여부 반환 1: yes , 2 : no
		return reviewService.isLike(reviewNo, name) ? "1" : "2";
	}
	
	
	
	
	
}
