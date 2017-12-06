package starrail.message.service;

import java.util.List;

import starrail.message.domain.MessageVO;

public interface MessageService {

	//쪽지보내기
	public void regist(MessageVO message) throws Exception;
	
	//받은쪽지함 전체 리스트
	public List<MessageVO> messageList(String m_id) throws Exception;

	//보낸쪽지함 전체 리스트
	public List<MessageVO> sendemail(String m_id) throws Exception;

	//쪽지삭제
	public void remove(int msg_no) throws Exception;
	
	//쪽지 수신함 열림 닫힘 확인
	public Integer msg_hit(int msg_no) throws Exception;
	
	//실시간 쪽지갯수
	public Integer update_hit(String m_id) throws Exception;
	
	//쪽지확인하기
	public MessageVO read(int msg_no) throws Exception;
}
