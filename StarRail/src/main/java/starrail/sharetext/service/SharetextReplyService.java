package starrail.sharetext.service;

import java.util.List;

import starrail.sharetext.domain.ShareTextCriteria;
import starrail.sharetext.domain.SharetextReplyVO;

public interface SharetextReplyService {
	
	public void addReply(SharetextReplyVO vo) throws Exception;
	
	public List<SharetextReplyVO> listReply(Integer sh_no)throws Exception;
	
	public void modifyReply(SharetextReplyVO vo)throws Exception;
	
	public void removeReply(Integer sr_no)throws Exception;
	
	public List<SharetextReplyVO> listReplyPage(Integer sh_no, ShareTextCriteria scri)throws Exception;
	
	public int count(Integer sh_no)throws Exception;
	
}
