package starrail.sharetext.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import starrail.sharetext.domain.ShareTextCriteria;
import starrail.sharetext.domain.SharetextReplyVO;
import starrail.sharetext.persistence.ShareTextDAO;
import starrail.sharetext.persistence.SharetextReplyDAO;

@Service
public class SharetextReplyServiceImpl implements SharetextReplyService {

	@Inject
	private SharetextReplyDAO dao;
	
	@Inject
	private ShareTextDAO sdao;
	
	//¥Ò±€ √ﬂ∞°
	@Transactional
	@Override
	public void addReply(SharetextReplyVO vo) throws Exception {
		
		dao.create(vo);
		sdao.updateReplyCnt(vo.getSh_no(), 1);
	}
	//¥Ò±€ ∏Ò∑œ
	@Override
	public List<SharetextReplyVO> listReply(Integer sh_no) throws Exception {
		
		return dao.list(sh_no);
	}
	//¥Ò±€ ºˆ¡§
	@Override
	public void modifyReply(SharetextReplyVO vo) throws Exception {
		dao.update(vo);
	}
	
	//¥Ò±€ ªË¡¶
	@Override
	public void removeReply(Integer sr_no) throws Exception {
		
		int sh_no=dao.getSh_no(sr_no);
		dao.delete(sr_no);
		sdao.updateReplyCnt(sh_no, -1);

	}
	
	//¥Ò±€ ∆‰¿Ã¬° √≥∏Æ
	@Override
	public List<SharetextReplyVO> listReplyPage(Integer sh_no, ShareTextCriteria scri) throws Exception {
		
		return dao.listPage(sh_no, scri);
	}

	@Override
	public int count(Integer sh_no) throws Exception {
		
		return dao.count(sh_no);
	}

}
