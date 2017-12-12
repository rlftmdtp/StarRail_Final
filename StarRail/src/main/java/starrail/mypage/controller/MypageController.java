package starrail.mypage.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import starrail.course.domain.CourseVO;
import starrail.reservation.domain.ReservationVO;
import starrail.main.domain.UserVO;
import starrail.mypage.service.MypageService;

@Controller
@RequestMapping("/mypage/*")
public class MypageController {

	@Inject
	private MypageService service;
	
	@RequestMapping(value="/mypage", method=RequestMethod.GET)	//마이페이지 열기
	public void courseGET(HttpServletRequest request, Model model) throws Exception{
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("login");
		model.addAttribute("user", user);
		
		List<CourseVO> courses = service.courseListService(user.getM_id());
		List<ReservationVO> reserves = service.reservationListService(user.getM_id());
		
		model.addAttribute("courses", courses);
		model.addAttribute("reserves", reserves);
	}
	
	
	@RequestMapping(value="/deleteCourse", method=RequestMethod.POST)
	public ResponseEntity<String> deleteCoursePOST(@RequestParam(value="c_id", required=false)Integer c_id){
		ResponseEntity<String> entity = null;
		
		try {
			service.courseRemove(c_id);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return entity;
	}
	
	
	
	
}
