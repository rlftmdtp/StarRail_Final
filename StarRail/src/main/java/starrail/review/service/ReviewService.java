package starrail.review.service;

import java.util.List;
import java.util.Map;

import starrail.review.domain.ReviewVO;
import starrail.review.domain.Hash_SearchVO;
import starrail.review.domain.Member_RecommendVO;
import starrail.review.domain.ReviewCriteria;
import starrail.review.domain.ReviewSearchCriteria;


public interface ReviewService {
	
	// 추천 시작
	public List<Integer> preferList_service(int m_no) throws Exception;
	public List<Integer> recommender_service(int m_no);
	public List<Hash_SearchVO> tagRecommend_service(List<Integer> list);
	public List<ReviewVO> reviewRecommend_service(String tag, ReviewSearchCriteria cri);
	public Integer reviewRecommendCount_service(String tag);
	public Integer selectMr_no_service();
	public List<Integer> selectCheckR_no_service(Member_RecommendVO mr);
	public void registMemberRecommend_service(Member_RecommendVO mr);
	public List<Map<String, Integer>> list_MemberRecommend_service();
	public List<Integer> list_reviewRecommend(int m_no) throws Exception;
	public void updateMr_count_service(Member_RecommendVO mr);
	public List<ReviewVO> list_userBased_servie(List<Integer> r_noList);
	// 추천 끝
	
	
	
	
	
	
	
	
	

	//후기게시판 등록
	public void register(ReviewVO review) throws Exception;
	//후기게시판 상세보기
	public ReviewVO read(Integer r_no) throws Exception;
	//후기게시판 수정
	public void modify(ReviewVO review) throws Exception;
	//후기게시판 삭제
	public void remove(Integer r_no) throws Exception;
	//전체 게시판
	public List<ReviewVO> list() throws Exception;
	//전체게시판 + 페이징처리
	public List<ReviewVO> listCriteria(ReviewCriteria cri) throws Exception;
	
	public int listCountCriteria(ReviewCriteria cri) throws Exception;
	//전체게시판 + 페이징 + 검색
	public List<ReviewVO> listSearchCriteria(ReviewSearchCriteria cri) throws Exception;
	
	public int listSearchCount(ReviewSearchCriteria cri) throws Exception;
	//파일 불러오기
	public List<String> getAttach(Integer r_no) throws Exception;
	
	public int hash_no()throws Exception;

	public Integer selectR_no() throws Exception;
	
	public List<String> hashtagInsert(ReviewVO review, Hash_SearchVO searchVO) throws Exception;
	
	public String specialCharacter_replace(String str) throws Exception;
	//전체 태그 가져오기
	public List<String> hashSearch() throws Exception;
	
	//게시판 상세보기에 해시태그 가져오기
	public List<String> myHash(int r_no) throws Exception;
	
	public void updateHash(String r_hash) throws Exception;
	public void insertHash(String r_hash) throws Exception;
	
}
