package starrail.map.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.rosuda.REngine.REXP;
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
import starrail.map.service.MapService;
import starrail.review.domain.ReviewCriteria;
import starrail.review.domain.ReviewPageMaker;

@RestController
@RequestMapping("/maprest/*")
public class MapRestController {
	
	static public Long festivalTableTotal;
	static public Long foodTableTotal;
	static public Long stayTableTotal;
	static public Long tourTableTotal;
	
	@Inject
	private MapService service;
	
	@RequestMapping(value="/coursedetail/{c_id}", method=RequestMethod.GET)
	public List<CourseDetailVO> stationList(@PathVariable("c_id") String c_id, Model model){
		System.out.println("coursedetail Controller " +  c_id);
		
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> map = new HashMap<String,Object>();
		
		// 코스 아이디를 통해 해당하는 역들이 온다.
		List<CourseDetailVO> courseDetailList = service.courseDetailList(c_id);
		
		// 하나씩 뽑아보면서 중복되는 역 이름은 빼야한다.
		List<String> stations = new ArrayList<String>();
		
		// 첫 번째 여행의 날짜를 뽑아야 한다.
		String startDate = courseDetailList.get(0).getCd_stime();
		System.out.println("startDate "+courseDetailList.get(0).getCd_stime());
		
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

		return courseDetailList;
	}
		
	@RequestMapping(value="/stationXY/{station}", method=RequestMethod.GET)
	public StationXYVO stationXY(@PathVariable("station") String station){
		System.out.println("stationXY Controller " +  station);
		
		StationXYVO stationXY = service.stationXY(station);
		System.out.println(stationXY.getS_x());
		return stationXY;
	}
	
	@RequestMapping(value="/festival/{areaCode}/{pageNo}/{date}", method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> festival(@PathVariable("areaCode") String areaCode, @PathVariable("pageNo") String pageNo
			, @PathVariable("date") String date){
		
		System.out.println("Area Controller " +  areaCode + "PageNo " + pageNo + " Date " + date);
		
		ResponseEntity<Map<String,Object>> entity = null;
		List<FestivalVO> festivalList = new ArrayList<FestivalVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		
			try {
				ReviewCriteria cri = new ReviewCriteria();
				cri.setPage(Integer.parseInt(pageNo));
				
				ReviewPageMaker pageMaker = new ReviewPageMaker();
				pageMaker.setCri(cri);
				
				festivalList = service.festival(areaCode, pageNo, date);
				map.put("festivalList", festivalList);
				
				pageMaker.setTotalCount(Integer.parseInt(festivalTableTotal.toString()));
				map.put("pageMaker", pageMaker);
				
				entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				entity = new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
			}
			
			return entity;
	}
	@RequestMapping(value="/festivaldetail/{contentid}", method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> festival(@PathVariable("contentid") String contentid){
		
		System.out.println("festivalDetail Controller " +  contentid);
		
		ResponseEntity<Map<String,Object>> entity = null;
		Map<String, Object> map = new HashMap<String, Object>();
		List<FestivalDetailVO> festivaldetailList = new ArrayList<FestivalDetailVO>();
		FestivalDetailVO festivalDetailVO = null;
			try {
				festivalDetailVO = service.festivaldetail(contentid);
				System.out.println(festivalDetailVO.getTitle());
				festivaldetailList.add(festivalDetailVO);
				map.put("festivaldetailList", festivaldetailList);
				
				entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				entity = new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
			}
			
			return entity;
	}
	
	@RequestMapping(value="/food/{areaCode}", method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> foodList(@PathVariable("areaCode") String areaCode){
		
		ResponseEntity<Map<String,Object>> entity = null;
		
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			
			List<FoodVO> foodList = service.foodList(areaCode);
			
			map.put("foodList", foodList);			
			
			entity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	@RequestMapping(value="/foodtable/{areaCode}/{pageNo}", method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> foodtableList(@PathVariable("areaCode") String areaCode, @PathVariable("pageNo") String pageNo){
		
		ResponseEntity<Map<String,Object>> entity = null;
		
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			
			ReviewCriteria cri = new ReviewCriteria();
			cri.setPage(Integer.parseInt(pageNo));
			
			ReviewPageMaker pageMaker = new ReviewPageMaker();
			pageMaker.setCri(cri);
			
			List<FoodVO> foodList = service.foodTableList(areaCode,pageNo);
			
			map.put("foodList", foodList);
			
			pageMaker.setTotalCount(Integer.parseInt(foodTableTotal.toString()));
			map.put("pageMaker", pageMaker);
			
			entity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	@RequestMapping(value="/fooddetail/{contentid}", method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> foodDetailList(@PathVariable("contentid") String contentid){
	
		System.out.println("contentid는?" + contentid);
		
		ResponseEntity<Map<String,Object>> entity = null;
		
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			
			map = service.foodDetail(contentid);
			
			entity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	@RequestMapping(value="/stay/{areaCode}", method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> stayList(@PathVariable("areaCode") String areaCode){
		
		ResponseEntity<Map<String,Object>> entity = null;
		
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			
			List<StayVO> stayList = service.stayList(areaCode);
			
			map.put("stayList", stayList);			
			
			entity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	@RequestMapping(value="/staytable/{areaCode}/{pageNo}", method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> staytableList(@PathVariable("areaCode") String areaCode, @PathVariable("pageNo") String pageNo){
		
		ResponseEntity<Map<String,Object>> entity = null;
		
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			
			ReviewCriteria cri = new ReviewCriteria();
			cri.setPage(Integer.parseInt(pageNo));
			
			ReviewPageMaker pageMaker = new ReviewPageMaker();
			pageMaker.setCri(cri);
			
			List<StayVO> stayList = service.stayTableList(areaCode,pageNo);
			
			map.put("stayList", stayList);
			
			pageMaker.setTotalCount(Integer.parseInt(stayTableTotal.toString()));
			map.put("pageMaker", pageMaker);
			
			entity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	@RequestMapping(value="/staydetail/{contentid}", method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> stayDetailList(@PathVariable("contentid") String contentid){
	
		System.out.println("숙소detail의 contentid는?" + contentid);
		
		ResponseEntity<Map<String,Object>> entity = null;
		
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			
			map = service.stayDetail(contentid);
		
			entity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	@RequestMapping(value="/tour/{areaCode}", method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> tourList(@PathVariable("areaCode") String areaCode){
		
		ResponseEntity<Map<String,Object>> entity = null;
		
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			
			List<TourVO> tourList = service.tourList(areaCode);
			
			map.put("tourList", tourList);			
			
			entity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	@RequestMapping(value="/tourtable/{areaCode}/{pageNo}", method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> tourtableList(@PathVariable("areaCode") String areaCode, @PathVariable("pageNo") String pageNo){
		
		ResponseEntity<Map<String,Object>> entity = null;
		
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			
			ReviewCriteria cri = new ReviewCriteria();
			cri.setPage(Integer.parseInt(pageNo));
			
			ReviewPageMaker pageMaker = new ReviewPageMaker();
			pageMaker.setCri(cri);
			
			List<TourVO> tourList = service.tourTableList(areaCode,pageNo);
			
			map.put("tourList", tourList);
			
			pageMaker.setTotalCount(Integer.parseInt(tourTableTotal.toString()));
			map.put("pageMaker", pageMaker);
			
			entity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	@RequestMapping(value="/tourdetail/{contentid}", method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> tourDetailList(@PathVariable("contentid") String contentid){
	
		System.out.println("관광지 detail의 contentid는?" + contentid);
		
		ResponseEntity<Map<String,Object>> entity = null;
		
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			
			map = service.tourDetail(contentid);
		
			entity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	@RequestMapping(value="/savalocation", method=RequestMethod.POST)
	public ResponseEntity<String> savelocation(@RequestBody LocationDTO dto){
		
		ResponseEntity<String> entity = null;
		System.out.println("SaveLocation c_id체크 " + dto.getC_id());
		System.out.println("SaveLocation sort " + dto.getSort());
		
		try {
			service.addLocation(dto);
			entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);  //400error
		}
		return entity;
	}
	
	/*
	@RequestMapping(value="/datablog/{blogtitle}/{page}", method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  dataBlog(@PathVariable("blogtitle") String blogtitle
			,@PathVariable("page") Integer page){
		System.out.println("datablogController " + blogtitle);
		
		ResponseEntity<Map<String,Object>> entity = null;
		
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			
			ReviewCriteria cri = new ReviewCriteria();
			cri.setPage(page);
			
			ReviewPageMaker pageMaker = new ReviewPageMaker();
			pageMaker.setCri(cri);
			
			List<BlogVO> tempList = service.dataBlog(blogtitle); // 전부가 왔다.
			
			List<BlogVO> blogList = new ArrayList<BlogVO>();
			for(int i=(page*3-3); i<page*3; i++)
			{
				blogList.add(tempList.get(i));
			}
			System.out.println("R분석 서비스로 넘어갑니다");
			//service.Ranalysis();
	
			map.put("blogList", blogList);
			
			pageMaker.setTotalCount(tempList.size());
			map.put("pageMaker", pageMaker);
			
			entity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
		return entity;	
	}
	
	@RequestMapping(value="/datalab/{labtitle}", method=RequestMethod.GET)
	public ResponseEntity<Map<String,Double>>  dataLab(@PathVariable("labtitle") String labtitle){
		System.out.println("dataLabController " + labtitle);
		
		ResponseEntity<Map<String,Double>> entity = null;
		
		try {
			Map<String,Double> map = new HashMap<String,Double>();
			Double ratio = service.dataLab(labtitle);
			
			map.put("ratio", ratio);
			entity = new ResponseEntity<Map<String,Double>>(map,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;	
	}
	*/
}
