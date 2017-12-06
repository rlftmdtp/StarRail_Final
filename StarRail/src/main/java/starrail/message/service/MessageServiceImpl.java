package starrail.message.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import starrail.message.domain.MessageVO;
import starrail.message.persistence.MessageDAO;


@Service
public class MessageServiceImpl implements MessageService {

	@Inject
	private MessageDAO dao;
	
	@Override	//쪽지 보내기 + 쪽지 글번호+1
	public void regist(MessageVO message) throws Exception {
		int msg_no = dao.selectMsg_no();
		
		if(msg_no != 0){
			message.setMsg_no(dao.selectMsg_no()+1);
		}
		
		dao.insertMessage(message);
	}

	@Override	//쪽지함 리스트 가져오기
	public List<MessageVO> messageList(String m_id) throws Exception {
		System.out.println("서비스id : " + m_id);
		return dao.listMessage(m_id);
	}

	@Override	//쪽지 삭제
	public void remove(int msg_no) throws Exception {
		dao.delete(msg_no);
	}

	@Override	//쪽지 확인 유무
	public Integer msg_hit(int msg_no) throws Exception {
		if(dao.msg_hit(msg_no)==null){
			return 0;
		}else{
			return dao.msg_hit(msg_no);
		}
		
	}

	@Override	//쪽지 실시간 갯수
	public Integer update_hit(String m_id) throws Exception {
		if(dao.update_hit(m_id) == null) {
			return 0;
		} else {
			return dao.update_hit(m_id);
		}		
	}

	@Override	//쪽지함읽기
	public MessageVO read(int msg_no) throws Exception {
		return dao.detail(msg_no);
	}

	@Override	//보낸쪽지함
	public List<MessageVO> sendemail(String m_id) throws Exception {
		return dao.sendemail(m_id);
	}

	
	

}
