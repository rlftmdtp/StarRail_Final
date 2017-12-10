package starrail.sharetext.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.sharetext.domain.SearchShareTextCriteria;
import starrail.sharetext.domain.ShareTextCriteria;
import starrail.sharetext.domain.ShareTextVO;
import starrail.sharetext.persistence.ShareTextDAO;

@Service
public class ShareTextServiceImpl implements ShareTextService {

	
	@Inject
	private ShareTextDAO dao;
	
	@Override
	public void regist(ShareTextVO share) throws Exception {
		dao.create(share);

	}

	@Transactional(isolation=Isolation.READ_COMMITTED)
	@Override
	public ShareTextVO read(Integer sh_no) throws Exception {
		System.out.println("dao : " + sh_no);
		dao.updateViewCnt(sh_no);
		return dao.read(sh_no);
	}

	@Override
	public void modify(ShareTextVO share) throws Exception {
		dao.update(share);

	}

	@Override
	public void remove(Integer sh_no) throws Exception {
		dao.delete(sh_no);

	}

	@Override
	public List<ShareTextVO> listAll() throws Exception {
	
		return dao.listAll();
	}

	@Override
	public List<ShareTextVO> listCriteria(SearchShareTextCriteria scri) throws Exception {
	
		return dao.listCriteria(scri);
	}

	@Override
	public int listCountCriteria(SearchShareTextCriteria scri) throws Exception {
		
		return dao.countPaging(scri);
	}

	@Override
	public List<HashMap<String, Object>> courselistAll(int c_id) throws Exception {
		
		return dao.courseDetailList(c_id);
	}

	@Override
	public List<CourseDetailVO> courseDetailList2(Integer sh_no) throws Exception {
		
		return dao.courseDetailList2(sh_no);
	}

	@Override
	public List<CourseDetailVO> courseDetailList3(String m_id) throws Exception {
		
		return dao.courseDetailList3(m_id);
	}

	@Override
	public List<CourseVO> selectCourse(String m_id) throws Exception {
		return dao.selectCourse(m_id);
	}

	@Override
	public int listsearchCount(SearchShareTextCriteria scri) throws Exception {
		
		return dao.listSearchCount(scri);
	}

	@Override
	public int listSearchCount(SearchShareTextCriteria scri) throws Exception {
		
		return dao.listSearchCount(scri);
	}

}
