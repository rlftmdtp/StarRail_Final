package starrail.message.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import starrail.main.domain.UserVO;
import starrail.message.domain.MessageVO;
import starrail.message.service.MessageService;

@Controller
@RequestMapping(value = "/message/*")
public class MessageController {

	@Inject
	public MessageService service;


	@RequestMapping(value = "/msg_insertform", method = RequestMethod.GET)
	public void msg_insertPartnerGET(MessageVO message,@RequestParam("msg_sendid") String msg_sendid, 
			Model model, HttpServletRequest request) throws Exception {
		//희정 동반자
		
		HttpSession session = request.getSession();
		
		try {
			if(((UserVO) session.getAttribute("login")) != null){
				UserVO user =  (UserVO) session.getAttribute("login");		
				if(msg_sendid!=null){
					System.out.println("보내는사람 있어");
					model.addAttribute("m_id", user.getM_id());
					model.addAttribute("msg_sendid", msg_sendid);
				}else{
					System.out.println("null이야");
					model.addAttribute("m_id", user.getM_id());
					model.addAttribute("msg_sendid", "");
				}
				
			}else{
				model.addAttribute("m_id", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@RequestMapping(value = "/msg_insertform", method = RequestMethod.POST)
	public void msg_insertformPOST(MessageVO message) throws Exception {
		System.out.println("여기는 오니?");
		System.out.println(message);
		//보내기 누르면 쪽지가 상대방에게 보내진다!
		service.regist(message);
	}

	@RequestMapping(value = "/msg_insertok", method = RequestMethod.POST)
	public ResponseEntity<String> MessageinsertPOST() throws Exception {
		//쪽지 자세히보고 확인 누르면 닫히게 하려고 
		ResponseEntity<String> entity = null;
		try {
			entity = new ResponseEntity<String>("", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	

	@RequestMapping(value = "/msg_list", method = RequestMethod.GET)
	public ModelAndView msg_listGET(Model model, MessageVO messageVO, HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		//로그인한 아이디의 받은 쪽지를 가져온다
		ModelAndView mav = new ModelAndView();
		
		try {
			if(((UserVO) session.getAttribute("login")) != null){
				UserVO user =  (UserVO) session.getAttribute("login");		
				mav.setViewName("/message/msg_list");
				mav.addObject("m_name", user.getM_name());
				mav.addObject("m_id", user.getM_id());
				mav.addObject("updatenum", service.update_hit( user.getM_id()));
				mav.addObject("list", service.messageList( user.getM_id()));
			}else{
				model.addAttribute("hashSearchVO", null);
				model.addAttribute("m_name", "?");
				model.addAttribute("m_id", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;
	}
	

	@RequestMapping(value = "/message_delete", method = RequestMethod.POST)
	public String MessageDeleteGET(@RequestBody int test) throws Exception {
		//쪽지 삭제
		service.remove(test);

		return "redirect:/message/msg_list";
	}

	@RequestMapping(value = "/msg_detail{msg_no}", method = RequestMethod.GET)
	public ModelAndView MessageDetailGET(@PathVariable int msg_no, Model model) throws Exception {
		//쪽지 자세히 보기
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/message/msg_detail");
		mav.addObject("messageVO", service.read(msg_no));
		mav.addObject("msg", service.msg_hit(msg_no));
		return mav;
	}

	@RequestMapping(value = "/msg_detail", method = RequestMethod.POST)
	public ResponseEntity<String> MessageDetailPOST() throws Exception {
		//쪽지 자세히보고 확인 누르면 자식창 닫히게 하려고 
		ResponseEntity<String> entity = null;
		try {
			entity = new ResponseEntity<String>("", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value = "/msg_sendmail", method = RequestMethod.GET)
	public ModelAndView msg_sendmailGET(Model model, MessageVO messageVO, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();

		//로그인한 아이디의 받은 쪽지를 가져온다
		ModelAndView mav = new ModelAndView();
		
		try {
			if(((UserVO) session.getAttribute("login")) != null){
				UserVO user =  (UserVO) session.getAttribute("login");		
				mav.setViewName("/message/msg_sendmail");
				mav.addObject("m_name", user.getM_name());
				mav.addObject("m_id", user.getM_id());
				mav.addObject("updatenum", service.update_hit( user.getM_id()));
				mav.addObject("list", service.sendemail( user.getM_id()));
			}else{
				model.addAttribute("hashSearchVO", null);
				model.addAttribute("m_name", "?");
				model.addAttribute("m_id", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return mav;
	}
	

}
