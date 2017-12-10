package starrail.sharetext.persistence;

import java.util.HashMap;
import java.util.List;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.sharetext.domain.SearchShareTextCriteria;
import starrail.sharetext.domain.ShareTextCriteria;
import starrail.sharetext.domain.ShareTextVO;

public interface ShareTextDAO {

	//글 생성
	public void create(ShareTextVO vo)throws Exception;
	//글 디테일
	public ShareTextVO read(Integer sh_no)throws Exception;
	//글 수정
	public void update(ShareTextVO vo)throws Exception;
	//글 삭제
	public void delete(Integer sh_no)throws Exception;
	//글 목록
	public List<ShareTextVO> listAll()throws Exception;
	//페이지페이지
	public List<ShareTextVO> listPage(int page)throws Exception;
	
	public List<ShareTextVO> listSearch(SearchShareTextCriteria scri)throws Exception;
	
	public int listSearchCount(SearchShareTextCriteria scri)throws Exception;
	
	public List<ShareTextVO> listCriteria(SearchShareTextCriteria scri)throws Exception;
	
	public int countPaging(SearchShareTextCriteria scri)throws Exception;
	//댓글 수 
	public void updateReplyCnt(Integer sh_no, int amount)throws Exception;
	
	public void updateViewCnt(Integer sh_no)throws Exception;
	
	public List<HashMap<String, Object>> courseDetailList(int c_id);
	
	public List<CourseDetailVO>courseDetailList2(Integer sh_no)throws Exception;
	
	public List<CourseDetailVO>courseDetailList3(String m_id)throws Exception;
	
	public List<CourseVO> selectCourse(String m_id) throws Exception;
}
