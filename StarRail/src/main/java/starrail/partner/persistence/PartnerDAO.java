package starrail.partner.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.main.domain.UserVO;
import starrail.partner.domain.PartnerListVO;

public interface PartnerDAO {
	public List<CourseVO> myCourse_List(UserVO userVO);
	public List<CourseDetailVO> mySchedule_List(HashMap<String, Object> map);
	public List<CourseDetailVO> courseDetail_List(List<Integer> list);
	public List<UserVO> partnerSearch_List(CourseDetailVO courseDetailVO);
	public CourseDetailVO courseDetail_Search_List(Integer cd_id);
}
