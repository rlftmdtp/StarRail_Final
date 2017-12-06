package starrail.main.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import starrail.main.domain.UserVO;
import starrail.main.dto.LoginDTO;
import starrail.main.service.UserService;

@Controller
@RequestMapping("/main/*")
public class MainController {
	
	@Inject
	private UserService service;
	
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public void viewGET() throws Exception{
		
	}
	
	@RequestMapping(value="/main", method=RequestMethod.GET)
	public void mainGET() throws Exception{
		
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public void loginGET(@ModelAttribute("dto") LoginDTO dto){
		
	}
	
	@RequestMapping(value="/loginPost", method=RequestMethod.POST)
	public void loginPOST(LoginDTO dto, HttpSession session, Model model) throws Exception{
		
		System.out.println(dto.getM_id());
		System.out.println(dto.getM_pw());
		
		session.setAttribute("id", dto.getM_id());
		UserVO vo = service.login(dto);
		
		// 濡쒓렇�씤 �떎�뙣
		if(vo == null){
			System.out.println("�븘�씠�뵒�굹 鍮꾨�踰덊샇媛� ���졇�뒿�땲�떎");
			
			return;
		}
		
		// 濡쒓렇�씤 �꽦怨�
		model.addAttribute("userVO", vo);
	}
}
