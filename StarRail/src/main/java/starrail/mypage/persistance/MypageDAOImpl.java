package starrail.mypage.persistance;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.reservation.domain.ReservationVO;

@Repository
public class MypageDAOImpl implements MypageDAO {

	@Inject
	private SqlSession session;
	
	private static String namespace = "starrail.mypage.mappers.mypageMapper";
	
	
	@Override
	public List<CourseVO> courseList(String m_id) throws Exception {
		return session.selectList(namespace+".courseList", m_id);
	}

	@Override
	public List<ReservationVO> reservationList(String m_id) throws Exception {
		return session.selectList(namespace+".reservationList", m_id);
	}

	@Override
	public void courseDelete(Integer c_id) throws Exception {
		session.delete(namespace+".courseDelete", c_id);
	}

	@Override
	public void courseDetailDelete(Integer c_id) throws Exception {
		session.delete(namespace+".courseDetailDelete", c_id);
	}
}
