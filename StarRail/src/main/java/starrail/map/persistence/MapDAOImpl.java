package starrail.map.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.map.domain.StationXYVO;
import starrail.map.domain.StationXYVO;

@Repository
public class MapDAOImpl implements MapDAO {
	
	@Inject
	private SqlSession session;
	
	private static String namespace = "starrail.mpa.mappers.MapMapper";
	
	@Override
	public List<CourseVO> courseList(String m_id) {
		return session.selectList(namespace + ".courseList", m_id); 
	}
	
	@Override
	public List<CourseDetailVO> courseDetailList(String c_id) {
		return session.selectList(namespace + ".stations", c_id);
	}
		
	@Override
	public StationXYVO stationXY(String station) {
		return session.selectOne(namespace + ".stationXY", station); 
	}
}
