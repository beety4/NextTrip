package service;

import java.util.ArrayList;

import dao.ReviewDAO;
import dto.CommentDTO;
import dto.ReviewDTO;

public class ReviewService {
	private ReviewDAO reviewDAO;
	
	public ReviewService() {
		reviewDAO = new ReviewDAO();
	}
	
	
	// 마지막 review 번호 가져오기
	public int getLastNo() {
		return reviewDAO.getLastNo();
	}
	
	// 게시글 총 개수
	public int getReviewCnt() {
		return reviewDAO.getReviewCnt();
	}
	
	
	// 총 페이지의 개수
	public int getTotalPage() {
		int reviewCnt = reviewDAO.getReviewCnt();
		int pageCnt = 10 / reviewCnt;
		return (reviewCnt % 10 != 0) ? ++pageCnt : pageCnt;
	}
	
	
	// review 목록 가져오기
	public ArrayList<ReviewDTO> getReviewList(int pageNo, int search, String searchIt) {
		return reviewDAO.getReviewList(pageNo, search, searchIt);
	}
	
	// review 상세보기
	public ReviewDTO getReviewDetail(int reviewNo) {
		return reviewDAO.getReviewDetail(reviewNo);
	}
	
	// review 작성하기 -> 맨 아래 주석 참고
	public int addReview(ReviewDTO reviewDTO) {
		return reviewDAO.addReview(reviewDTO);
	}
	
	// review 삭제하기
	public int deleteReview(int reviewNo) {
		return reviewDAO.deleteReview(reviewNo);
	}
	
	// review 수정하기
	public int editReview(ReviewDTO reviewDTO) {
		return reviewDAO.editReview(reviewDTO);
	}
	
	
	
	// 댓글 작성
	public void addReviewComment(CommentDTO commentDTO) {
		reviewDAO.addReviewComment(commentDTO);
	}
	
	// 댓글 삭제
	public void deleteReviewComment(int commentNo) {
		reviewDAO.deleteReviewComment(commentNo);
	}
	
	// 댓글 목록 가져오기
	public ArrayList<CommentDTO> getCommentList(int reviewNo) {
		return reviewDAO.getCommentList(reviewNo);
	}
	
	// 댓글 정보 가져오기
	public CommentDTO getComment(int commentNo) {
		return reviewDAO.getComment(commentNo);
	}
	
	
	// 조회수
	public void addView(int reviewNo) {
		reviewDAO.addView(reviewNo);
	}
	
	
	
	// 좋아요 추가
	public void addReviewLike(int reviewNo, String name) {
		reviewDAO.addReviewLike(reviewNo, name);
	}
	
	// 좋아요 삭제
	public void deleteReviewLike(int reviewNo, String name) {
		reviewDAO.deleteReviewLike(reviewNo, name);
	}
	
	// 좋아요 여부 확인
	public boolean isLike(int reviewNo, String name) {
		return reviewDAO.isLike(reviewNo, name);
	}
	
	// 현재 좋아요 개수
	public int getLikeCnt(int reviewNo) {
		return reviewDAO.getLikeCnt(reviewNo);
	}
	
	// 좋아요 버튼 동작
	public int likeAction(int reviewNo, String name) {
		if(isLike(reviewNo, name)) {
			deleteReviewLike(reviewNo, name);
		} else {
			addReviewLike(reviewNo, name);
		}
		return getLikeCnt(reviewNo);
	}
	
}

/*
 * == 파일 업로드 오류 처리 로그(미해결) ==
 * 1. cos.jar 라이브러리를 이용하여 MultipartRequest 로 파일 업로드 처리 시도
 * 1-1. 에러 -> 현재 개발환경 Tomcat v10.0에선 cos 지원하지 않음.
 * 	           Tomcat을 9.0으로 다운그레이드
 *             JDK 버전도 1.8로 다운그레이드가 필요
 *             Servlet에러 발생( 보류 )
 * 
 * 2. apache common fileupload 라이브러리를 이용하여 파일 업로드 처리 시도
 * 2-1. 에러 -> 현재 개발환경 JDK17과 동시에 모든 서블릿 관련 파일(HttpServletRequest 등..)을 javax -> jakarta로 사용중
 *             common-fileupload.jar 디코딩하여 소스코드 분석 하니 import javax.servlet.http.HttpServletRequest를 사용중인것을 확인
 *             jakrata.servlet.http.HttpServletRequest로 변경 후 관련 클래스 파일과 함께 인코딩하였으나 에러 발생
 *             서칭 도중 공식문서에서 commons-fileupload2-jakarta-2.0.0-M1.jar 에 Jakarta를 지원하는 파일 발견
 *             해당 파일과 core 를 같이 첨부하여 시도 하였으나 DiskFileItemFactory 생성자 생성의 실패
 *         
 * 3. 시간 상 파일 업로드 보류 후 진행
 * 3-1. 제거 기능 -> 게시글 단일, 다중 파일 업로드
 *                프로필 사진 업로드 및 수정 기능
 * 
 */