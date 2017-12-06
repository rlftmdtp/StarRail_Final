package starrail.map.service;

import java.util.List;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.map.domain.FoodVO;
import starrail.map.domain.StationXYVO;
import starrail.map.domain.StayVO;
import starrail.map.domain.TourVO;

public interface MapService {
	public static final String clientId = "FeSf9NchU5GMk0kap7Kn";//애플리케이션 클라이언트 아이디값";
	public static final String clientSecret = "PFQhfHmyH1";//애플리케이션 클라이언트 시크릿값";
	
	public List<CourseVO> courseList(String m_id);
	public StationXYVO stationXY(String station);
	public List<FoodVO> foodList(String station) throws Exception;
	public List<StayVO> stayList(String station) throws Exception;
	public List<TourVO> tourList(String station) throws Exception;
	public void dataLab(String station) throws Exception;
	public List<CourseDetailVO> courseDetailList(String c_id);
	
	
}
