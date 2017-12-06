package starrail.map.persistence;

import java.util.List;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.map.domain.StationXYVO;

public interface MapDAO {
	
	public List<CourseVO> courseList(String m_id);
	public StationXYVO stationXY(String station);
	public List<CourseDetailVO> courseDetailList(String c_id);
}
