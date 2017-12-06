package starrail.message.controller;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import starrail.message.domain.MessageVO;
import starrail.message.persistence.MessageDAO;
import starrail.message.service.MessageService;

@Controller
@RequestMapping(value = "/message/*")
public class MessageController {

	@Inject
	public MessageService service;
	public MessageDAO dao;

	@RequestMapping(value = "/start", method = RequestMethod.GET)
	public void startGET() {
		//희정이 동반자 찾기 버튼 
	}

	@RequestMapping(value = "/msg_insertform", method = RequestMethod.GET)
	public void msg_insertGET(MessageVO message) throws Exception {
		//쪽지보내기 버튼누르면 쪽지창 뜸
	}
	


	@RequestMapping(value = "/msg_insertform", method = RequestMethod.POST)
	public void msg_insertformPOST(MessageVO message) throws Exception {
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
	public ModelAndView msg_listGET(Model model, MessageVO messageVO) throws Exception {
		//로그인한 아이디의 받은 쪽지를 가져온다
		ModelAndView mav = new ModelAndView();
		messageVO.setM_id("dlwotmd");
		
		//msg_list로 아래의 3가지를 가지고 가겠다.
		mav.setViewName("/message/msg_list");
		mav.addObject("m_id", "dlwotmd");
		mav.addObject("updatenum", service.update_hit(messageVO.getM_id()));
		mav.addObject("list", service.messageList(messageVO.getM_id()));
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
	public ModelAndView msg_sendmailGET(Model model, MessageVO messageVO) throws Exception {
		//로그인한 아이디가 다른 사람에게 보낸 쪽지
		ModelAndView mav = new ModelAndView();
		messageVO.setM_id("dlwotmd");
		mav.setViewName("/message/msg_sendmail");
		mav.addObject("m_id", "dlwotmd");
		mav.addObject("updatenum", service.update_hit(messageVO.getM_id()));
		mav.addObject("list", service.sendemail(messageVO.getM_id()));
		return mav;
	}
	

}
