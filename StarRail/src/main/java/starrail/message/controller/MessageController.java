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
		//������ ������ ã�� ��ư 
	}

	@RequestMapping(value = "/msg_insertform", method = RequestMethod.GET)
	public void msg_insertGET(MessageVO message) throws Exception {
		//���������� ��ư������ ����â ��
	}
	


	@RequestMapping(value = "/msg_insertform", method = RequestMethod.POST)
	public void msg_insertformPOST(MessageVO message) throws Exception {
		//������ ������ ������ ���濡�� ��������!
		service.regist(message);
	}

	@RequestMapping(value = "/msg_insertok", method = RequestMethod.POST)
	public ResponseEntity<String> MessageinsertPOST() throws Exception {
		//���� �ڼ������� Ȯ�� ������ ������ �Ϸ��� 
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
		//�α����� ���̵��� ���� ������ �����´�
		ModelAndView mav = new ModelAndView();
		messageVO.setM_id("dlwotmd");
		
		//msg_list�� �Ʒ��� 3������ ������ ���ڴ�.
		mav.setViewName("/message/msg_list");
		mav.addObject("m_id", "dlwotmd");
		mav.addObject("updatenum", service.update_hit(messageVO.getM_id()));
		mav.addObject("list", service.messageList(messageVO.getM_id()));
		return mav;
	}
	

	@RequestMapping(value = "/message_delete", method = RequestMethod.POST)
	public String MessageDeleteGET(@RequestBody int test) throws Exception {
		//���� ����
		service.remove(test);

		return "redirect:/message/msg_list";
	}

	@RequestMapping(value = "/msg_detail{msg_no}", method = RequestMethod.GET)
	public ModelAndView MessageDetailGET(@PathVariable int msg_no, Model model) throws Exception {
		//���� �ڼ��� ����
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/message/msg_detail");
		mav.addObject("messageVO", service.read(msg_no));
		mav.addObject("msg", service.msg_hit(msg_no));
		return mav;
	}

	@RequestMapping(value = "/msg_detail", method = RequestMethod.POST)
	public ResponseEntity<String> MessageDetailPOST() throws Exception {
		//���� �ڼ������� Ȯ�� ������ �ڽ�â ������ �Ϸ��� 
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
		//�α����� ���̵� �ٸ� ������� ���� ����
		ModelAndView mav = new ModelAndView();
		messageVO.setM_id("dlwotmd");
		mav.setViewName("/message/msg_sendmail");
		mav.addObject("m_id", "dlwotmd");
		mav.addObject("updatenum", service.update_hit(messageVO.getM_id()));
		mav.addObject("list", service.sendemail(messageVO.getM_id()));
		return mav;
	}
	

}
