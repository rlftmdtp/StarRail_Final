package starrail.sharetext.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.sharetext.domain.SearchShareTextCriteria;
import starrail.sharetext.domain.ShareTextCriteria;
import starrail.sharetext.domain.ShareTextVO;

@Repository
public class ShareTextDAOImpl implements ShareTextDAO {

	@Inject
	private SqlSession session;
	
	private static String namespace = "starrail.sharetext.mappers.ShareMapper";
	
	@Override
	public void create(ShareTextVO vo) throws Exception {
		session.insert(namespace+".create",vo);
	}

	@Override
	public ShareTextVO read(Integer sh_no) throws Exception {
		System.out.println(sh_no);
		return session.selectOne(namespace+".read",sh_no);
	}

	@Override
	public void update(ShareTextVO vo) throws Exception {
	
		session.update(namespace+".update",vo);

	}

	@Override
	public void delete(Integer sh_no) throws Exception {
		
		session.delete(namespace+".delete", sh_no);
		
	}

	@Override
	public List<ShareTextVO> listAll() throws Exception {
		
		return session.selectList(namespace+".listAll");
	}

	@Override
	public List<ShareTextVO> listPage(int page) throws Exception {
		
		if(page <=0) {
			page = 1;
			
		}
		
		page = (page -1) * 5;
		
		return session.selectList(namespace+".listPage",page);
	}

	@Override
	public List<ShareTextVO> listCriteria(SearchShareTextCriteria scri) throws Exception {
		
		return session.selectList(namespace+".listCriteria", scri, new RowBounds(scri.getPageStart(), scri.getPerPageNum())); 
		//안하면 다나옴
	}

	@Override
	public int countPaging(SearchShareTextCriteria scri) throws Exception {
		
		return session.selectOne(namespace+".countPaging",scri);
	}

	@Override
	public void updateReplyCnt(Integer sh_no, int amount) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("sh_no", sh_no);
		paramMap.put("amount", amount);
		
		session.update(namespace+".updateReplyCnt", paramMap);
		
	}

	@Override
	public void updateViewCnt(Integer sh_no) throws Exception {
		
		session.update(namespace+".updateViewCnt",sh_no);
		
	}
	
	
	//게시판 c_id정보 가져오기
	@Override
	public List<HashMap<String, Object>> courseDetailList(int c_id) {
		
		return session.selectList(namespace+".coursedetail",c_id);
	}

	@Override
	public List<CourseDetailVO> courseDetailList2(Integer sh_no) throws Exception {
		
		return session.selectList(namespace+".coursedetail2",sh_no);
	}

	@Override
	public List<CourseDetailVO> courseDetailList3(String m_id) throws Exception {
		System.out.println(m_id + ": " + "dao");
		return session.selectList(namespace+".coursedetail3",m_id);
	}

	@Override
	public List<CourseVO> selectCourse(String m_id) throws Exception {
		return session.selectList(namespace+".selectCourse", m_id);
	}

	

	@Override
	public List<ShareTextVO> listSearch(SearchShareTextCriteria scri) throws Exception {
		
		return session.selectList(namespace+".listSearch", scri);
	}

	@Override
	public int listSearchCount(SearchShareTextCriteria scri) throws Exception {
		
		return session.selectOne(namespace+".listSearchCount",scri);
	}
	
	

}
