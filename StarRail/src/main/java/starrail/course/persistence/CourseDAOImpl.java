package starrail.course.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.course.domain.IssueInfoVO;
import starrail.course.domain.StationCoorVO;

@Repository
public class CourseDAOImpl implements CourseDAO {
	
	@Inject
	private SqlSession session;
	
	private static String namespace = "starrail.course.mappers.courseMapper";

	@Override
	public IssueInfoVO issueSelect(String i_name) throws Exception {
		return session.selectOne(namespace+".issueSelect", i_name);
	}

	@Override
	public Integer selectC_id() throws Exception {
		return session.selectOne(namespace+".selectC_id");
	}

	@Override
	public Integer selectCd_id() throws Exception {
		return session.selectOne(namespace+".selectCd_id");
	}

	@Override
	public void courseInsert(CourseVO c) throws Exception {
		session.insert(namespace+".courseInsert",c);
		
	}

	@Override
	public void courseDetailInsert(CourseDetailVO cd) throws Exception {
		session.insert(namespace+".courseDetailInsert",cd);
		
	}

	@Override
	public CourseVO courseSelect(Integer c_id) throws Exception {
		return session.selectOne(namespace+".courseSelect",c_id);
	}

	@Override
	public List<CourseDetailVO> courseDetailList(Integer c_id) throws Exception {
		return session.selectList(namespace+".courseDetailList", c_id);
	}

	@Override
	public void courseUpdate(CourseVO c) throws Exception {
		session.update(namespace+".courseUpdate", c);
		
	}

	@Override
	public void courseDetailDelete(Integer c_id) throws Exception {
		session.delete(namespace+".courseDetailDelete", c_id);
	}

	@Override
	public StationCoorVO getCoordinate(String stc_name) throws Exception {
		
		return session.selectOne(namespace+".getCoordinate", stc_name);
	}

}
