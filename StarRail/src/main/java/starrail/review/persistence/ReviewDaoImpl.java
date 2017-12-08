package starrail.review.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import starrail.review.domain.FileVO;
import starrail.review.domain.Hash_SearchVO;
import starrail.review.domain.Member_RecommendVO;
import starrail.review.domain.ReviewVO;
import starrail.review.domain.ReviewCriteria;
import starrail.review.domain.ReviewSearchCriteria;


@Repository
public class ReviewDaoImpl implements ReviewDao{

	@Inject
	public SqlSession session;
	
	private static String namespace = "railro.review.mapper.ReviewMapper";
	
	
	@Override	//후기게시판 등록
	public void insertReview(ReviewVO review) throws Exception{
		session.insert(namespace + ".insertReview", review);
	}
	
	@Override	//후기게시판 상세보기
	public ReviewVO selectReview(Integer r_no) throws Exception{
		return session.selectOne(namespace + ".detailReview", r_no);
	}
	
	@Override	//후기게시판 수정
	public void updateReview(ReviewVO review) throws Exception{
		session.update(namespace + ".updateReview", review);
	}
	
	@Override	//후기게시판 삭제
	public void deleteReview(Integer r_no) throws Exception{
		session.delete(namespace + ".deleteReview", r_no);
	}
	
	@Override	//전체 게시판
	public List<ReviewVO> listReview() throws Exception{
		return session.selectList(namespace+".listReview");
	}

	@Override //글번호 + 1
	public Integer selectR_no() {
		return session.selectOne(namespace+".selectR_no");
	}

	@Override	//페이징
	public List<ReviewVO> listPage(int Page) throws Exception {
		if(Page <= 0){
			Page = 1;
		}
		
		Page = (Page -1) *10;
		return session.selectList(namespace+".listPage", Page, new RowBounds(Page, 10));
	}

	@Override	//전체게시판 + 페이징처리
	public List<ReviewVO> listCriteria(ReviewCriteria cri) throws Exception {
		return session.selectList(namespace+".listCriteria", cri, new RowBounds(cri.getPageStart(), cri.getPerPageNum()));
	}

	@Override	//총 게시물이 몇개인지 
	public int countPaging(ReviewCriteria cri) throws Exception {
		return session.selectOne(namespace+".countPaging", cri);
	}

	@Override	//전체게시판 + 페이징 + 검색
	public List<ReviewVO> listSearch(ReviewSearchCriteria cri) throws Exception {
		return session.selectList(namespace+".listSearch", cri, new RowBounds(cri.getPageStart(), cri.getPerPageNum()));
	}

	@Override
	public int listSearchCount(ReviewSearchCriteria cri) throws Exception {
		return session.selectOne(namespace+".listSearchCount", cri);
	}

	@Override	//파일 저장
	public void addAttach(FileVO fileVO) throws Exception {
		session.insert(namespace+".addAttach", fileVO);
	}

	@Override	//파일 불러오기 
	public List<String> getAttach(Integer r_no) throws Exception {
		return session.selectList(namespace+".getAttach", r_no);
	}

	@Override	//파일 삭제
	public void deleteAttach(Integer r_no) throws Exception {
		session.delete(namespace+".deleteAttach", r_no);
	}

	@Override	//파일 수정
	public void repalceAttach(String rf_fullname, Integer r_no) throws Exception {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		paramMap.put("r_no", r_no);
		paramMap.put("rf_fullname", rf_fullname);
		
		session.insert(namespace+".replaceAttach", paramMap);
	}

	
	@Override	//게시물 조회수
	public void updateR_hit(Integer r_no) throws Exception {
		session.update(namespace+".updateR_hit", r_no);
	}

	@Override	//글번호+1
	public int getR_no() throws Exception {
		return session.selectOne(namespace+".maxNum");
	}

	@Override	//내 태그 추가하기
	public void tagAdd(Map<String, Object>paramMap) throws Exception {
		System.out.println("dao paramMap : " + paramMap);
		session.insert(namespace+".addHash", paramMap);
	}

	@Override
	public Integer hash_no() throws Exception {
		return session.selectOne(namespace+".selectH_no");
	}

	@Override	//전체 태그 가져오기
	public List<String> HashSearch() throws Exception {
		return session.selectList(namespace+".tagGet");
	}

	@Override
	public List<String> myHash(int r_no) throws Exception {
		return session.selectList(namespace+".myhash", r_no);
	}

	@Override
	public void updatehash(String r_hash) throws Exception {
		session.update(namespace+".updatehash", r_hash);
	}

	@Override
	public void inserthash(Map<String, Object> map) throws Exception {
		session.insert(namespace+".inserthash", map);
	}

	@Override
	public Integer select_hs_no() throws Exception {
		return session.selectOne(namespace+".select_hs_no");
	}

	//-------------------------------------------------------------------------------------------------------------

	// 추천 시작
	@Override
	public List<Map<String, Integer>> preferList() {
		return session.selectList(namespace+".prefer_Select");
	}

	@Override
	public List<Hash_SearchVO> tagRecommend(List<Integer> list) {
		//System.out.println("dao : "+ session.selectList(namespace+".tagRecommend_Select", list));
		return session.selectList(namespace+".tagRecommend_Select", list);
	}
	
	@Override
	public List<ReviewVO> reviewRecommend(String tag, ReviewSearchCriteria cri) {
		return session.selectList(namespace+".reviewRecommend_Select", tag, new RowBounds(cri.getPageStart(), cri.getPerPageNum()));
	}
	
	@Override
	public Integer reviewRecommendCount(String tag) {
		return session.selectOne(namespace+".reviewRecommendCount_Select", tag);
	}
	


	@Override
	public Integer selectMr_no() {
		return session.selectOne(namespace+".mr_noSelect");
	}
	
	@Override
	public void insertMemberRecommend(Member_RecommendVO mr) {
		session.insert(namespace+".member_recommend_Insert", mr);
		System.out.println("dao입니다");
	}
	
	@Override
	public List<Integer> selectCheckR_no(int r_no) {
		System.out.println("ddddd");
		System.out.println("dao 테스트  :  " + session.selectList(namespace+".MemberRecommend_r_noSelect", r_no));
		return session.selectList(namespace+".MemberRecommend_r_noSelect", r_no);
	}
	
	@Override
	public List<Map<String, Integer>> list_MemberRecommend() {
		return session.selectList(namespace+".member_recommendList_Select");
	}
	
	@Override
	public void updateMr_count(Member_RecommendVO mr) {
		session.update(namespace+".Mr_count_Update", mr);
	}
	
	// 추천 끝











	
}
