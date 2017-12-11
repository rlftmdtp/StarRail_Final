package starrail.mypage.service;

import java.util.List;

import starrail.course.domain.CourseVO;
//import starrail.reservation.domain.ReservationVO;

public interface MypageService {
	
	public List<CourseVO> courseListService(String m_id) throws Exception;
//	public List<ReservationVO> reservationListService(String m_id) throws Exception;
	public void courseRemove(Integer c_id) throws Exception;

}
