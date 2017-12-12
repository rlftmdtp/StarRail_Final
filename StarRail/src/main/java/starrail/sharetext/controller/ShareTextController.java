package starrail.sharetext.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import starrail.course.domain.CourseDetailVO;
import starrail.main.domain.UserVO;
import starrail.sharetext.domain.SearchShareTextCriteria;
import starrail.sharetext.domain.ShareTextCriteria;
import starrail.sharetext.domain.ShareTextVO;
import starrail.sharetext.domain.SharetextPageMaker;
import starrail.sharetext.service.ShareTextService;

@Controller
@RequestMapping("/sharetext/*")
public class ShareTextController {
	
	@Inject
	private ShareTextService service;
	
	
	//회원정보 가지고 오기
	/*@RequestMapping(value="/register", method=RequestMethod.GET)
	public void registerGET(ShareTextVO share, Model model, HttpServletRequest request) throws Exception{
		
		//회원정보가 담겨있는 session
		HttpSession session = request.getSession();
		
		UserVO uservo = (UserVO)session.getAttribute("login");
		
		String m_id= uservo.getM_name();
		
		System.out.println("register get...");
	}*/
	
	//글 등록 페이지 보여줌
	@RequestMapping(value="/sharetext_insert", method=RequestMethod.GET)
	public void registerGET(ShareTextVO share, Model model, HttpServletRequest request)throws Exception{
		System.out.println("register get...성공[컨트롤러]");
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("login");
		model.addAttribute("login", userVO);
		
		System.out.println(userVO.getM_id());
		System.out.println(service.selectCourse(userVO.getM_id()));
		//model.addAttribute("courseDetailList", service.courseDetailList3(userVO.getM_id()));
		model.addAttribute("courseList", service.selectCourse(userVO.getM_id()));
	}
	
	@ResponseBody
	@RequestMapping(value="/selectCourse/{c_id}", method=RequestMethod.GET)
	public List<HashMap<String, Object>> selectCourse(@PathVariable("c_id") int c_id, Model model) throws Exception{
		System.out.println("코스선택 컨트롤러.............");
		List<HashMap<String, Object>> list	= service.courselistAll(c_id);
		//model.addAttribute("courseDetailList", list);
		
		return list;
	}
	
	//글 등록
	@RequestMapping(value="/sharetext_insert", method=RequestMethod.POST)
	public String registPOST(ShareTextVO share, RedirectAttributes rttr, HttpServletRequest request, Model model) throws Exception{
		System.out.println("regist post...성공[컨트롤러]");
		System.out.println(share.toString());
		
		
		
		
		
		service.regist(share);
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/sharetext/sharetext_listPage";
	}
	
		//목록보기
		@RequestMapping(value="/sharetext/sharetext_listAll", method=RequestMethod.GET)
		public void listAll(Model model) throws Exception{
			
			System.out.println("show all list...");
			model.addAttribute("list", service.listAll());
			
		}
	
		//디테일
		@RequestMapping(value="/sharetext_detail",method=RequestMethod.GET)
		public void read(@RequestParam("sh_no") int sh_no, Model model, HttpServletRequest reqeust)throws Exception{
			
			HttpSession session = reqeust.getSession();
			UserVO userVO = (UserVO) session.getAttribute("login");
			
			List<CourseDetailVO> list = service.courseDetailList2(sh_no);
			System.out.println(list);
			
			System.out.println("read text...");
			model.addAttribute(service.read(sh_no));
			model.addAttribute("list",list);
			model.addAttribute("login", userVO);
		}
		
		
		//삭제
		@RequestMapping("remove")
		public String remove(@RequestParam("sh_no") Integer sh_no, RedirectAttributes rttr)throws Exception{
			service.remove(sh_no);
			
			rttr.addFlashAttribute("msg", "SUCCESS");
			
			return "redirect:/sharetext/sharetext_listPage";
		}
		
		//수정
		@RequestMapping(value="/sharetext_update", method=RequestMethod.GET)
		public void modifyGET(@RequestParam("sh_no")int sh_no, Model model,HttpServletRequest request)throws Exception{
			System.out.println("update text...");
			List<CourseDetailVO> list = service.courseDetailList2(sh_no);
			System.out.println(list);
			HttpSession session = request.getSession();
			UserVO userVO = (UserVO) session.getAttribute("login");
			model.addAttribute("login", userVO);
			model.addAttribute(service.read(sh_no));
			model.addAttribute("list",list);
		}
		
		@RequestMapping(value="/sharetext_update", method=RequestMethod.POST)
		public String modifyPOST(ShareTextVO share, RedirectAttributes rttr)throws Exception{
			System.out.println("mod post...");
			
			service.modify(share);
			rttr.addFlashAttribute("msg", "SUCCESS");
			
			return "redirect:/sharetext/sharetext_listPage";
		}
		
		@RequestMapping(value="/sharetext_listCri", method=RequestMethod.GET)
		public void listAll(SearchShareTextCriteria scri, Model model) throws Exception{
			
			System.out.println("show list page with criteria...");
			
			model.addAttribute("list", service.listCriteria(scri));
		}
		
		@RequestMapping(value="/sharetext_listPage", method=RequestMethod.GET)
		public void listPage(@ModelAttribute("scri") SearchShareTextCriteria scri, Model model)throws Exception{
			
			System.out.println(scri.toString());
			
			model.addAttribute("list", service.listCriteria(scri));
			SharetextPageMaker pageMaker = new SharetextPageMaker();
			pageMaker.setCri(scri);
			pageMaker.setTotalCount(service.listCountCriteria(scri));  
			
			model.addAttribute("pageMaker",pageMaker);
		}
}

