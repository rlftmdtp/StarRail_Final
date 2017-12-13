package starrail.course.persistence;

import java.util.List;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.course.domain.IssueInfoVO;
import starrail.course.domain.StationCoorVO;

public interface CourseDAO {
	
	public IssueInfoVO issueSelect(String i_name) throws Exception;	//발권역 정보 가져오기
	
	public Integer selectC_id() throws Exception;	//c_id max값
	public Integer selectCd_id() throws Exception;	//cd_id max값
	
	public void courseInsert(CourseVO c) throws Exception;
	public void courseDetailInsert(CourseDetailVO cd) throws Exception;
	
	public CourseVO courseSelect(Integer c_id) throws Exception;
	public List<CourseDetailVO> courseDetailList(Integer c_id) throws Exception;

	public void courseUpdate(CourseVO c) throws Exception;
	public void courseDetailDelete(Integer c_id) throws Exception;
	
	public StationCoorVO getCoordinate(String stc_name) throws Exception;
}
