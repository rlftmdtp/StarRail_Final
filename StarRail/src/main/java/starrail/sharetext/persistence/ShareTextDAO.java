package starrail.sharetext.persistence;

import java.util.HashMap;
import java.util.List;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.sharetext.domain.SearchShareTextCriteria;
import starrail.sharetext.domain.ShareTextCriteria;
import starrail.sharetext.domain.ShareTextVO;

public interface ShareTextDAO {

	//�� ����
	public void create(ShareTextVO vo)throws Exception;
	//�� ������
	public ShareTextVO read(Integer sh_no)throws Exception;
	//�� ����
	public void update(ShareTextVO vo)throws Exception;
	//�� ����
	public void delete(Integer sh_no)throws Exception;
	//�� ���
	public List<ShareTextVO> listAll()throws Exception;
	//������������
	public List<ShareTextVO> listPage(int page)throws Exception;
	
	public List<ShareTextVO> listSearch(SearchShareTextCriteria scri)throws Exception;
	
	public int listSearchCount(SearchShareTextCriteria scri)throws Exception;
	
	public List<ShareTextVO> listCriteria(SearchShareTextCriteria scri)throws Exception;
	
	public int countPaging(SearchShareTextCriteria scri)throws Exception;
	//��� �� 
	public void updateReplyCnt(Integer sh_no, int amount)throws Exception;
	
	public void updateViewCnt(Integer sh_no)throws Exception;
	
	public List<HashMap<String, Object>> courseDetailList(int c_id);
	
	public List<CourseDetailVO>courseDetailList2(Integer sh_no)throws Exception;
	
	public List<CourseDetailVO>courseDetailList3(String m_id)throws Exception;
	
	public List<CourseVO> selectCourse(String m_id) throws Exception;
}
