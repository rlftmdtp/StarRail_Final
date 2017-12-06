package starrail.message.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import starrail.message.domain.MessageVO;

@Repository
public class MessageDAOImpl implements MessageDAO {

	@Inject
	public SqlSession session;
	
	private static String namespace = "railro.review.mapper.MessageMapper";
	
	@Override	//쪽지보내기
	public void insertMessage(MessageVO message) throws Exception {
		session.insert(namespace +".insertMessage", message); 
	}

	@Override	//글번호 +1
	public Integer selectMsg_no() throws Exception {
		return session.selectOne(namespace+".selectMsg_no");
	}

	@Override	//받은쪽지함 전체 리스트
	public List<MessageVO> listMessage(String m_id) throws Exception {
		System.out.println("daoid : " + m_id);
		return session.selectList(namespace+".list", m_id);
	}

	@Override	//쪽지삭제
	public void delete(int msg_no) throws Exception {
		session.delete(namespace + ".delete", msg_no);
	}

	@Override	//쪽지 수신함 열림 닫힘 확인
	public Integer msg_hit(int msg_no) throws Exception {
		return session.update(namespace+".msg_hit", msg_no);
	}

	@Override	//실시간 쪽지갯수
	public Integer update_hit(String m_id) throws Exception {
		return session.selectOne(namespace+".update_hit", m_id);
	}

	@Override	//쪽지확인하기
	public MessageVO detail(int msg_no) throws Exception {
		return session.selectOne(namespace+".detail", msg_no);
	}

	@Override	//보낸쪽지함 전체 리스트
	public List<MessageVO> sendemail(String m_id) throws Exception {
		return session.selectList(namespace+".sendemail", m_id);
	}



	

}
