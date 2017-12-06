package starrail.map.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.main.domain.UserVO;
import starrail.map.service.MapService;

@Controller
@RequestMapping("/map/*")
public class MapController {
	
	private static final Logger logger = LoggerFactory.getLogger(MapController.class);
	
	@Inject
	private MapService service;
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public void viewGET(HttpServletRequest request,Model model) throws Exception{
		logger.info("Map Controller에 오신것을 환영합니다 !");
		
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute("login");
		
		// 유저의 코스와 코스 디테일을 가져와야 한다.
		// m_id를 통해 c_id를 가져온다.
		List<CourseVO> courseList = service.courseList(userVO.getM_id());
		model.addAttribute("courseList", courseList);
	}
	
}
