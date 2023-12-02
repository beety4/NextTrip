package service;

import java.util.ArrayList;

import dao.BookMarkDAO;
import dto.tourAPI.TourSpotDTO;

public class BookMarkService {
	private BookMarkDAO bookMarkDAO;
	
	public BookMarkService() {
		bookMarkDAO = new BookMarkDAO();
	}
	
	// 북마크 추가
	public void addBookMark(TourSpotDTO tourSpotDTO, String name) {
		bookMarkDAO.addBookMark(tourSpotDTO, name);
	}
	
	// 북마크 삭제
	public void deleteBookMark(String contentid, String name) {
		bookMarkDAO.deleteBookMark(contentid, name);
	}
	
	// 북마크 체크 확인
	public boolean isCheck(String contentid, String name) {
		return bookMarkDAO.isCheck(contentid, name);
	}
	
	// 북마크 리스트 출력
	public ArrayList<TourSpotDTO> getBookMarkList(String name) {
		return bookMarkDAO.getBookMarkList(name);
	}
	
	
	// 북마크 액션
	public int bookmarkAction(TourSpotDTO tourSpotDTO, String name) {
		if(isCheck(tourSpotDTO.getContentid(), name)) {
			deleteBookMark(tourSpotDTO.getContentid(), name);
			return 0;
		} else {
			addBookMark(tourSpotDTO, name);
			return 1;
		}
	}
	
}
