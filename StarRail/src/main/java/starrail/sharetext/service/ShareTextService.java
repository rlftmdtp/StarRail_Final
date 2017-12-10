package starrail.sharetext.service;

import java.util.HashMap;
import java.util.List;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.sharetext.domain.SearchShareTextCriteria;
import starrail.sharetext.domain.ShareTextCriteria;
import starrail.sharetext.domain.ShareTextVO;

public interface ShareTextService {

	public void regist(ShareTextVO share)throws Exception;
	
	public ShareTextVO read(Integer sh_no)throws Exception;
	
	public void modify(ShareTextVO share)throws Exception;
	
	public void remove(Integer sh_no)throws Exception;
	
	public List<ShareTextVO> listAll()throws Exception;
	
	public List<ShareTextVO> listCriteria(SearchShareTextCriteria scri)throws Exception;
	
	public int listCountCriteria(SearchShareTextCriteria scri)throws Exception;
	
	public List<HashMap<String, Object>> courselistAll(int c_id)throws Exception;
	
	public List<CourseDetailVO> courseDetailList2(Integer sh_no)throws Exception;
	
	public List<CourseDetailVO> courseDetailList3(String m_id)throws Exception;
	
	public List<CourseVO> selectCourse(String m_id) throws Exception;

	public int listsearchCount(SearchShareTextCriteria scri)throws Exception;
	
	public int listSearchCount(SearchShareTextCriteria scri)throws  Exception;	
}
