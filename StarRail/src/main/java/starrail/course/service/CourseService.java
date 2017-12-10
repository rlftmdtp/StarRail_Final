package starrail.course.service;

import java.util.List;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.course.domain.IssueInfoVO;
import starrail.course.domain.StationCoorVO;

public interface CourseService {
	
	public List<IssueInfoVO> issueList(List<String> nodes) throws Exception;	//발권역 정보 불러오기
	public void courseRegist(CourseVO c, List<CourseDetailVO> cds) throws Exception;
	
	public CourseVO courseRead(Integer c_id) throws Exception;
	public List<CourseDetailVO> courseDetailList(Integer c_id) throws Exception;
	
	public void courseModify(CourseVO c, List<CourseDetailVO> cds) throws Exception;
	
	public StationCoorVO getCoordinateService(String stc_name) throws Exception;
}
