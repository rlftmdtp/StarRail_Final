package starrail.sharetext.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import starrail.sharetext.domain.ShareTextCriteria;
import starrail.sharetext.domain.SharetextPageMaker;
import starrail.sharetext.domain.SharetextReplyVO;
import starrail.sharetext.service.SharetextReplyService;

@RestController   //JSON�� ���� ����� �� �ִ�.
@RequestMapping("/replies/*")
public class SharetextReplyController {

	@Inject
	private SharetextReplyService service;
	
	//��ۻ���
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ResponseEntity<String>register(@RequestBody SharetextReplyVO vo){    
		System.out.println("controller...............");
		ResponseEntity<String> entity = null;
		try {
			service.addReply(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	//��� ���
	@RequestMapping(value="/all/{sh_no}", method= RequestMethod.GET)
	public ResponseEntity<List<SharetextReplyVO>> list(
			@PathVariable("sh_no") Integer sh_no) {
		ResponseEntity<List<SharetextReplyVO>> entity = null;
		try {
			entity = new ResponseEntity<>(service.listReply(sh_no), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	//��� ����
	@RequestMapping(value="/{sr_no}", method={RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> update(@PathVariable("sr_no") Integer sr_no, @RequestBody SharetextReplyVO vo){
		ResponseEntity<String> entity = null;
		try {
			vo.setSr_no(sr_no);
			service.modifyReply(vo);
			
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
		e.printStackTrace();
		entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
				
		}
		return entity;
	}
	
	//��� ����
	@RequestMapping(value="/{sr_no}", method= RequestMethod.DELETE)	
	public ResponseEntity<String> remove (
			@PathVariable("sr_no") Integer  sr_no){
		ResponseEntity<String> entity = null;
		try {
			service.removeReply(sr_no);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(
					e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
		
	}
	
	//����¡ó��
	@RequestMapping(value="/{sh_no}/{page}",method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listPage(
			@PathVariable("sh_no") Integer sh_no,
			@PathVariable("page") Integer page){
		ResponseEntity<Map<String, Object>> entity = null;
		
		try {
			ShareTextCriteria scri = new ShareTextCriteria();
			scri.setPage(page);
			
		    SharetextPageMaker pageMaker = new SharetextPageMaker();
			pageMaker.setScri(scri);
			
			Map<String, Object> map = new HashMap<String, Object>();
			List<SharetextReplyVO> list = service.listReplyPage(sh_no, scri);   //��۰����� �����ִ� ��.
			
			map.put("list", list);    //list���� ��� ����� ����ִ�. 
			
			int replyCount = service.count(sh_no);  //��� ������ replyCount�� �־���. 
			pageMaker.setTotalCount(replyCount);   //pageMaker���� replyCount�� �־���. 
			
			map.put("pageMaker", pageMaker);
			
			entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
}
