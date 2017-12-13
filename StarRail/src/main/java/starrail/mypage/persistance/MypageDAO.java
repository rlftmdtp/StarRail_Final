package starrail.mypage.persistance;

import java.util.List;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.reservation.domain.ReservationVO;

public interface MypageDAO {
	
	public List<CourseVO> courseList(String m_id) throws Exception;
	public List<ReservationVO> reservationList(String m_id) throws Exception;
	public void courseDelete(Integer c_id) throws Exception;
	public void courseDetailDelete(Integer c_id) throws Exception;
	
}
