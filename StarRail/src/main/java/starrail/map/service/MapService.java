package starrail.map.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RserveException;
import org.springframework.http.ResponseEntity;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.map.domain.BlogVO;
import starrail.map.domain.FestivalDetailVO;
import starrail.map.domain.FestivalVO;
import starrail.map.domain.FoodDetailVO;
import starrail.map.domain.FoodVO;
import starrail.map.domain.LocationDTO;
import starrail.map.domain.StationXYVO;
import starrail.map.domain.StayDetailVO;
import starrail.map.domain.StayVO;
import starrail.map.domain.TourVO;

public interface MapService {
	public static final String clientId = "FeSf9NchU5GMk0kap7Kn";//애플리케이션 클라이언트 아이디값";
	public static final String clientSecret = "PFQhfHmyH1";//애플리케이션 클라이언트 시크릿값";
	
	public List<CourseDetailVO> courseDetailList(String c_id);
	
	public List<CourseVO> courseList(String m_id);
	public StationXYVO stationXY(String station);
	
	public List<FestivalVO> festival(String areaCode, String pageNo, String date) throws Exception;
	public FestivalDetailVO festivaldetail(String contentid) throws Exception;
	
	public List<FoodVO> foodList(String areaCode) throws Exception;
	public List<FoodVO> foodTableList(String areaCode, String pageNo) throws Exception;
	public Map<String, Object> foodDetail(String contentid) throws Exception;
	
	public List<StayVO> stayList(String station) throws Exception;
	public List<StayVO> stayTableList(String areaCode, String pageNo) throws Exception;
	public Map<String,Object> stayDetail(String contentid) throws Exception;
	
	public List<TourVO> tourList(String station) throws Exception;
	public List<TourVO> tourTableList(String areaCode, String pageNo) throws Exception;
	public Map<String, Object> tourDetail(String contentid) throws Exception;
	
	public void addLocation(LocationDTO dto);
	
	public List<BlogVO> dataBlog(String title) throws Exception;
	public Double dataLab(String title) throws Exception;
	public String Ranalysis() throws RserveException, REXPMismatchException;	
}
