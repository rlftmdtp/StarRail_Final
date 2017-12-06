package starrail.course.controller;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.course.domain.IssueInfoVO;
import starrail.course.domain.StationVO;
import starrail.course.domain.TrainTimeVO;
import starrail.course.service.CourseService;
import starrail.course.util.StationParsingUtil;

@Controller
@RequestMapping("/course/*")
public class CourseController {
	
	@Inject
	private StationParsingUtil stationParser;
	
	@Inject
	private CourseService service;
	
	@RequestMapping(value="/makeCourse",method=RequestMethod.GET)	//코스짜기 페이지 열기
	public void courseGET() throws Exception{
			
	}
	
	@RequestMapping(value="/depList", method=RequestMethod.POST)	//n일차 선택 후 출발역 리스트 불러오기
	public ResponseEntity<List<StationVO>> depListPOST() throws Exception{

		ResponseEntity<List<StationVO>> entity = null;
		
		
		try {
			List<String> cityCodes = stationParser.getCityCode();
			List<StationVO> s_stations = stationParser.stationList(cityCodes);
			
			entity = new ResponseEntity<List<StationVO>>(s_stations, HttpStatus.OK); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
	
	@RequestMapping(value="/arrList", method=RequestMethod.POST)	//출발역 선택 후 가능한 도착역 리스트 불러오기
	@ResponseBody
	public ResponseEntity<List<StationVO>> arrListPOST(@RequestParam(value="depNode", required=false) String depNode,
														@RequestParam(value="depDate", required=false) String depDate) throws Exception{
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		
		ResponseEntity<List<StationVO>> entity = null;
		try {
			List<String> cityCodes = stationParser.getCityCode();
			List<StationVO> allNodes = stationParser.stationList(cityCodes);
			List<StationVO> arrStations = stationParser.arrStationList(depNode, depDate, allNodes);
			
			entity = new ResponseEntity<List<StationVO>>(arrStations, responseHeaders, HttpStatus.OK);  //200OK
							//ResponseEntity<String>는 실행결과, HttpStatus결과를 가져와 넣기 위한 것
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return entity;
	}
	
	@RequestMapping(value="/trainTime", method=RequestMethod.POST)	//출발 희망시간 선택 후 시간표 출력
	@ResponseBody
	public ResponseEntity<List<TrainTimeVO>> trainTimPOST(@RequestParam(value="depNode", required=false) String depNode,
															@RequestParam(value="depDate", required=false) String depDate,
															@RequestParam(value="arrNode", required=false) String arrNode,
															@RequestParam(value="selectedTime", required=false)int selectedTime) throws Exception{
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		ResponseEntity<List<TrainTimeVO>> entity = null;
		try {
			List<TrainTimeVO> traintimes = stationParser.getTimeTable(depNode, depDate, arrNode, selectedTime);
			entity = new ResponseEntity<List<TrainTimeVO>>(traintimes, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
	
	@RequestMapping(value="/issuelist", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<IssueInfoVO>> issuelistPOST(@RequestParam(value="selectedDep", required=false) String selectedDep,
															@RequestParam(value="selectedArr", required=false) String selectedArr) throws Exception{
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		ResponseEntity<List<IssueInfoVO>> entity = null;
		
		try {
			List<String> nodes = new ArrayList<String>();
			nodes.add(selectedDep);
			nodes.add(selectedArr);
			
			List<IssueInfoVO> list = service.issueList(nodes);
			entity = new ResponseEntity<List<IssueInfoVO>>(list, responseHeaders, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
	
	
	@RequestMapping(value = "/insertCourse", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> insertCoursePOST(@RequestParam(value="m_id", required=false)String m_id,
													@RequestParam(value="i_name", required=false)String i_name,
													@RequestParam(value="c_name", required=false)String c_name,
													@RequestParam(value="details", required=false)String details) throws Exception{
		ResponseEntity<String> entity = null;
		
		
		try {
			CourseVO c = new CourseVO();
			c.setM_id(m_id);
			c.setC_name(c_name);
			c.setI_name(i_name);
			c.setC_filename("test");	//추후 파일 업로드 구현 후에 수정
			
			List<String> detail_list = JSONArray.fromObject(details);
			
			List<CourseDetailVO> cds = new ArrayList<CourseDetailVO>();
			for(int i=0; i<detail_list.size(); i++){

				String[] data = detail_list.get(i).split("#");	//출발역#출발시간#도착역#도착시간 --> #으로 나눠서 배열에 저장 --> 인덱스 0:출발역 1:출발시간 2:도착역 3:도착시간
				
				CourseDetailVO cd = new CourseDetailVO();
				cd.setCd_start(data[0]);
				cd.setCd_stime(data[1]);
				cd.setCd_end(data[2]);
				cd.setCd_etime(data[3]);
				
				cds.add(cd);
			}
			
			service.courseRegist(c, cds);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	
	
	@RequestMapping(value="/editCourse{c_id}",method=RequestMethod.GET)	//코스짜기 페이지 열기
	public ModelAndView courseModifyGET(@PathVariable int c_id) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/course/editCourse");
		
		CourseVO course = service.courseRead(c_id);
		List<CourseDetailVO> details = service.courseDetailList(c_id);
		mav.addObject("course", course);
		mav.addObject("details", details);
		
		return mav;
	}
	
	@RequestMapping(value="/updateCourse",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> updateCoursePOST(@RequestParam(value="c_id", required=false)Integer c_id,
													@RequestParam(value="i_name", required=false)String i_name,
													@RequestParam(value="c_name", required=false)String c_name,
													@RequestParam(value="details", required=false)String details) throws Exception{
		ResponseEntity<String> entity = null;
		
		try {
			CourseVO c = new CourseVO();
			c.setC_id(c_id);
			c.setC_name(c_name);
			c.setI_name(i_name);
			c.setC_filename("test2");	//추후 파일 업로드 구현 후에 수정
			
			List<String> detail_list = JSONArray.fromObject(details);
			
			List<CourseDetailVO> cds = new ArrayList<CourseDetailVO>();
			for(int i=0; i<detail_list.size(); i++){

				String[] data = detail_list.get(i).split("#");	//출발역#출발시간#도착역#도착시간 --> #으로 나눠서 배열에 저장 --> 인덱스 0:출발역 1:출발시간 2:도착역 3:도착시간
				
				CourseDetailVO cd = new CourseDetailVO();
				cd.setCd_start(data[0]);
				cd.setCd_stime(data[1]);
				cd.setCd_end(data[2]);
				cd.setCd_etime(data[3]);
				
				cds.add(cd);
				
				service.courseModify(c, cds);
				entity = new ResponseEntity<String>("success", HttpStatus.OK);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
}
