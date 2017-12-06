package starrail.partner.persistence;

import java.util.HashMap;
import java.util.List;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.main.domain.UserVO;

public interface PartnerDAO {
	public List<CourseVO> myCourse_List(UserVO userVO);
	public List<CourseDetailVO> mySchedule_List(HashMap<String, Object> map);
	public List<CourseDetailVO> courseDetail_List(List<Integer> list);
	public List<UserVO> partnerSearch_List(CourseDetailVO courseDetailVO);
	public CourseDetailVO courseDetail_Search_List(Integer cd_id);
}
