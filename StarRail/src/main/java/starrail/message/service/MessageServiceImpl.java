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
	
	@Override	//���� ������ + ���� �۹�ȣ+1
	public void regist(MessageVO message) throws Exception {
		int msg_no = dao.selectMsg_no();
		
		if(msg_no != 0){
			message.setMsg_no(dao.selectMsg_no()+1);
		}
		
		dao.insertMessage(message);
	}

	@Override	//������ ����Ʈ ��������
	public List<MessageVO> messageList(String m_id) throws Exception {
		System.out.println("����id : " + m_id);
		return dao.listMessage(m_id);
	}

	@Override	//���� ����
	public void remove(int msg_no) throws Exception {
		dao.delete(msg_no);
	}

	@Override	//���� Ȯ�� ����
	public Integer msg_hit(int msg_no) throws Exception {
		if(dao.msg_hit(msg_no)==null){
			return 0;
		}else{
			return dao.msg_hit(msg_no);
		}
		
	}

	@Override	//���� �ǽð� ����
	public Integer update_hit(String m_id) throws Exception {
		if(dao.update_hit(m_id) == null) {
			return 0;
		} else {
			return dao.update_hit(m_id);
		}		
	}

	@Override	//�������б�
	public MessageVO read(int msg_no) throws Exception {
		return dao.detail(msg_no);
	}

	@Override	//����������
	public List<MessageVO> sendemail(String m_id) throws Exception {
		return dao.sendemail(m_id);
	}

	
	

}
