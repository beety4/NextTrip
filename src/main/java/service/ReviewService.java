package service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import dao.ReviewDAO;
import dto.CommentDTO;
import dto.ReviewDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


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
	

	// review 작성하기 with fileUpload -> 시도과정 맨아래 주석 참고
	public void addReview(HttpServletRequest request, HttpServletResponse response) {
		String uploadPath = request.getServletContext().getRealPath("/") + "\\assets\\img\\reviewIMG";
		int maxSize = 3 * 1024 * 1024;	// 3MB 제한
		
		try {
			// FileItem 오브젝트 생성용 객체
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setRepository(new File(uploadPath));
			factory.setSizeThreshold(maxSize);

			// 파일 업로드 핸들러 생성
			ServletFileUpload upload = new ServletFileUpload(factory);

			// request 객체의 데이터를 Map<String, List<FileItem> 형태로 파싱 이후 파라미터 가져오기
			Map<String, List<FileItem>> formMap = upload.parseParameterMap(request);
			String title = formMap.get("title").get(0).getString("UTF-8");
			String region = formMap.get("region").get(0).getString("UTF-8");
			String content = formMap.get("content").get(0).getString("UTF-8");
			String fileName = null;
			
			// 파일 첨부 시
			FileItem receiveFile = formMap.get("file").get(0);
			if(receiveFile.getSize() > 0) {
				// 업로드 된 파일 처리
				fileName = receiveFile.getName();
				File saveFile = new File(uploadPath + "\\" + fileName);
				receiveFile.write(saveFile);	// 업로드-저장
			}
			
			// DB 저장을 위한 객체 생성
			ReviewDTO reviewDTO = ReviewDTO.builder()
					.name((String)request.getSession().getAttribute("sNAME"))
					.title(title)
					.region(region)
					.content(content)
					.img((fileName == null) ? null :  "assets\\img\\reviewIMG\\"+fileName).build();
			
			// 내용 DB에 저장
			reviewDAO.addReview(reviewDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
 * == 파일 업로드 오류 처리 로그(해결!!) ==
 * 1. cos.jar 라이브러리를 이용하여 MultipartRequest 로 파일 업로드 처리 시도
 * 1-1. 에러 -> 현재 개발환경 Tomcat v10.0에선 cos 지원하지 않음.
 * 	           Tomcat을 9.0으로 다운그레이드
 *             JDK 버전도 1.8로 다운그레이드가 필요
 *             Servlet에러 발생( 보류 ) -> 메인 controller에서 문제 발생 왜일까..
 *             수정! - 버전 다운그레이드로 인한 jakarta -> javax 로 서블릿 경로 모두 수정 필요
 *                    수정 후 정상 작동 확인 완료(4번 에러까지 다 시도 하고 확인함..ㅠ)
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
 * 4. tomcat10에서 제공하는 org.apache.tomcat....ServletFileUpload 발견
 * 4-1. 에러 -> 해당객체의 메소드가 인터넷 자료들과 많이 다름
 *             parseRequest를 통해 List<FileItem> 형태로 파싱 하지만 해당 메소드가 없음
 *             parseParameterMap 메소드로 대체 가능 -> Map<String, List<FileItem> 형태로 저장
 *             인터넷 참고하며 다시 코드 빌드
 *             성공~!
 *             
 */