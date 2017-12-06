package starrail.map.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import starrail.course.domain.CourseDetailVO;
import starrail.map.domain.FoodVO;
import starrail.map.domain.StationXYVO;
import starrail.map.domain.StayVO;
import starrail.map.domain.TourVO;
import starrail.map.service.MapService;

@RestController
@RequestMapping("/maprest/*")
public class MapRestController {
	
	@Inject
	private MapService service;
	
	@RequestMapping(value="/coursedetail/{c_id}", method=RequestMethod.GET)
	public List<String> stationList(@PathVariable("c_id") String c_id){
		System.out.println("coursedetail Controller " +  c_id);
		
		// 코스 아이디를 통해 해당하는 역들이 온다.
		List<CourseDetailVO> courseDetailList = service.courseDetailList(c_id);
		
		// 하나씩 뽑아보면서 중복되는 역 이름은 빼야한다.
		List<String> stations = new ArrayList<String>();
		for(int i=0; i<courseDetailList.size(); i++){
			
			if(!stations.contains(courseDetailList.get(i).getCd_start())) // 값을 포함하고 있으면 넣지않는다
			{
				stations.add(courseDetailList.get(i).getCd_start());
			}
			
			if(!stations.contains(courseDetailList.get(i).getCd_end())) // 값을 포함하고 있으면 넣지않는다
			{
				stations.add(courseDetailList.get(i).getCd_end());
			}
		}
		
		return stations;
	}
		
	@RequestMapping(value="/stationXY/{station}", method=RequestMethod.GET)
	public StationXYVO stationXY(@PathVariable("station") String station){
		System.out.println("stationXY Controller " +  station);
		
		StationXYVO stationXY = service.stationXY(station);
		System.out.println(stationXY.getS_x());
		return stationXY;
	}
	
	@RequestMapping(value="/tourlist/{station}", method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> tourList(@PathVariable("station") String station){
		
		ResponseEntity<Map<String,Object>> entity = null;
		
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			
			List<FoodVO> foodList = service.foodList(station);
			List<StayVO> stayList = service.stayList(station);
			List<TourVO> tourList = service.tourList(station);
			
			// 각 지점과 거리를 구하는 로직....?
			//---------------------------
			
			map.put("foodList", foodList);			
			//map.put("stayList", stayList);			
			//map.put("tourList", tourList);
			
			entity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
	
	@RequestMapping(value="/datalab/{station}", method=RequestMethod.GET)
	public void dataLab(@PathVariable("station") String station){
		System.out.println("datalabController " + station);
		
		try {
			service.dataLab(station);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
