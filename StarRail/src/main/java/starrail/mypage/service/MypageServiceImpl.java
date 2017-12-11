package starrail.mypage.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import starrail.course.domain.CourseVO;
//import starrail.reservation.domain.ReservationVO;
import starrail.course.persistence.CourseDAO;
import starrail.mypage.persistance.MypageDAO;

@Service
public class MypageServiceImpl implements MypageService {
	
	@Inject
	private MypageDAO dao;
	
	@Override
	public List<CourseVO> courseListService(String m_id) throws Exception {
		return dao.courseList(m_id); 
	}

	/*@Override
	public List<ReservationVO> reservationListService(String m_id) throws Exception {
		return dao.reservationList(m_id);
	}*/

	@Override
	public void courseRemove(Integer c_id) throws Exception {
		dao.courseDetailDelete(c_id);
		dao.courseDelete(c_id);
	}

}
