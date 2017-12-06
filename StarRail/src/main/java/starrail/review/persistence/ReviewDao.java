package starrail.review.persistence;

import java.util.List;
import java.util.Map;

import starrail.review.domain.FileVO;
import starrail.review.domain.Hash_SearchVO;
import starrail.review.domain.ReviewVO;
import starrail.review.domain.ReviewCriteria;
import starrail.review.domain.ReviewSearchCriteria;


public interface ReviewDao {

	//후기게시판 등록
	public void insertReview(ReviewVO review) throws Exception;
	//후기게시판 상세보기
	public ReviewVO selectReview(Integer r_no) throws Exception;
	//후기게시판 수정
	public void updateReview(ReviewVO review) throws Exception;
	//후기게시판 삭제
	public void deleteReview(Integer r_no) throws Exception;
	//전체 게시판
	public List<ReviewVO> listReview() throws Exception;
	
	public List<ReviewVO> listPage(int Page) throws Exception;
	//전체게시판 + 페이징처리
	public List<ReviewVO> listCriteria(ReviewCriteria cri) throws Exception;
	//전체게시판 + 페이징 + 검색
	public List<ReviewVO> listSearch(ReviewSearchCriteria cri) throws Exception;
	
	public int listSearchCount(ReviewSearchCriteria cri) throws Exception;
	
	public Integer selectR_no() throws Exception;
	//총 게시물이 몇개인지 
	public int countPaging(ReviewCriteria cri) throws Exception;

	//파일 저장
	public void addAttach(FileVO rf_fullname) throws Exception;
	int getR_no() throws Exception;
	//파일 불러오기
	public List<String> getAttach(Integer r_no) throws Exception;
	//파일 삭제
	public void deleteAttach(Integer r_no) throws Exception;
	//파일 수정
	public void repalceAttach(String rf_fullName, Integer r_no) throws Exception;
	//게시물 조회수
	public void updateR_hit(Integer r_no) throws Exception;
	//태그 추가
//	public void tagAdd(Integer h_no, Integer r_no, String r_hash) throws Exception; 
	public void tagAdd(Map<String, Object> paramMap) throws Exception; 
	
	public Integer hash_no() throws Exception;
	//전체 태그 조회
	public List<String> HashSearch() throws Exception;
	
	//게시판 상세보기에 해시태그 가져오기
	public List<String> myHash(int r_no) throws Exception;
	
	public void updatehash(String r_hash) throws Exception;
	
	public void inserthash(Map<String, Object> map) throws Exception;
	
	public Integer select_hs_no() throws Exception;
}
