package starrail.sharetext.persistence;

import java.util.List;

import starrail.sharetext.domain.ShareTextCriteria;
import starrail.sharetext.domain.SharetextReplyVO;

public interface SharetextReplyDAO {

	public List<SharetextReplyVO> list(Integer sh_no) throws Exception;
	
	public void create(SharetextReplyVO vo) throws Exception;
	
	public void update(SharetextReplyVO vo)throws Exception;
	
	public void delete(Integer sr_no)throws Exception;
	
	//其捞隆 贸府 1
	public List<SharetextReplyVO> listPage(Integer sh_no, ShareTextCriteria scri) throws Exception;
	
	//其捞隆 贸府 2
	public int count(Integer sh_no) throws Exception;
	
	public int getSh_no(Integer sr_no)throws Exception;
}
