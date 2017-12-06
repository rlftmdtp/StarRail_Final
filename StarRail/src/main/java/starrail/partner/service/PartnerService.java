package starrail.partner.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.main.domain.UserVO;

public interface PartnerService {
	public List<CourseVO> myCourse_List_service(UserVO userVO);
	public List<CourseDetailVO> mySchedule_List_service(HashMap<String, Object> map);
	public List<CourseDetailVO> courseDetail_List_service(Map<String, String> list);
	public List<UserVO> partnerSearch_List_service(CourseDetailVO courseDetailVO);
	public CourseDetailVO courseDetail_Search_List_service(Integer cd_id);
}
