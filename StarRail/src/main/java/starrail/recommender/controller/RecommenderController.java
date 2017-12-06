package starrail.recommender.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import starrail.main.domain.UserVO;
import starrail.recommender.service.RecommenderService;
import starrail.review.domain.Hash_SearchVO;

@Controller
@RequestMapping(value="/recommend/*")
public class RecommenderController {
	
	@Inject
	private RecommenderService service;
	
	@RequestMapping(value="/recommend", method = RequestMethod.GET)
	public void todayTag(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		int m_no;	
		String m_name;	
		List<Integer> list = new ArrayList<Integer>();
		List<Hash_SearchVO> tag_list = new ArrayList<Hash_SearchVO>();
		
		try {
			if(((UserVO) session.getAttribute("login")) != null){
				UserVO user =  (UserVO) session.getAttribute("login");		
				m_no = user.getM_no();
				
				list = service.preferList_service(m_no);
				tag_list = service.tagRecommend_service(list);
				System.out.println("컨트롤러 결과값 : " + tag_list);
				
				model.addAttribute("hashSearchVO", tag_list);
				model.addAttribute("m_name", user.getM_name());
			}else{
				model.addAttribute("hashSearchVO", null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
