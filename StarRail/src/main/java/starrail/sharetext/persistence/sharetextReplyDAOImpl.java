package starrail.sharetext.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import starrail.sharetext.domain.ShareTextCriteria;
import starrail.sharetext.domain.SharetextReplyVO;

@Repository
public class sharetextReplyDAOImpl implements SharetextReplyDAO {

	@Inject
	private SqlSession session;
	
	private static String namespace = "starrail.sharetext.mappers.shareReplyMapper";
	
	//댓글 목록
	@Override
	public List<SharetextReplyVO> list(Integer sh_no) throws Exception {
		
		return session.selectList(namespace+".list", sh_no);
	}

	
	/*글생성*/
	@Override
	public void create(SharetextReplyVO vo) throws Exception {
		session.insert(namespace+".create",vo);

	}
	//글 수정
	@Override
	public void update(SharetextReplyVO vo) throws Exception {
		session.selectList(namespace+".update",vo);

	}

	//글 삭제
	@Override
	public void delete(Integer sr_no) throws Exception {
		session.delete(namespace+".delete",sr_no);

	}


	@Override
	public List<SharetextReplyVO> listPage(Integer sh_no, ShareTextCriteria scri) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		
		paramMap.put("sh_no", sh_no);
		paramMap.put("scri", scri);
		
		return session.selectList(namespace+".listPage", paramMap);
		
	}


	@Override
	public int count(Integer sh_no) throws Exception {
		
		return session.selectOne(namespace+".count", sh_no);
	}


	@Override
	public int getSh_no(Integer sr_no) throws Exception {
		
		return session.selectOne(namespace+".getSh_no",sr_no);
	}

}
