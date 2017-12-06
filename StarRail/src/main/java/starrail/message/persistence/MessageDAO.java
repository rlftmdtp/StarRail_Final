package starrail.message.persistence;

import java.util.List;

import starrail.message.domain.MessageVO;

public interface MessageDAO {

	//����������
	public void insertMessage(MessageVO message) throws Exception;
	
	//�۹�ȣ +1
	public Integer selectMsg_no() throws Exception;
	
	//���������� ��ü ����Ʈ
	public List<MessageVO> listMessage(String m_id) throws Exception;

	//���������� ��ü ����Ʈ
	public List<MessageVO> sendemail(String m_id) throws Exception;

	//��������
	public void delete(int msg_no) throws Exception;
	
	//���� ������ ���� ���� Ȯ��
	public Integer msg_hit(int msg_no) throws Exception;
	
	//�ǽð� ��������
	public Integer update_hit(String m_id) throws Exception;
	
	//����Ȯ���ϱ�
	public MessageVO detail(int msg_no) throws Exception;
	
}
