package starrail.partner.domain;

import java.util.List;

import starrail.main.domain.UserVO;
import starrail.sharetext.domain.ShareTextVO;

public class PartnerListVO {
	private List<UserVO> userVO;
	private List<ShareTextVO> shareText;

	public PartnerListVO(){}

	public List<UserVO> getUserVO() {
		return userVO;
	}

	public void setUserVO(List<UserVO> userVO) {
		this.userVO = userVO;
	}

	public List<ShareTextVO> getShareText() {
		return shareText;
	}

	public void setShareText(List<ShareTextVO> shareText) {
		this.shareText = shareText;
	}

	public PartnerListVO(List<UserVO> userVO, List<ShareTextVO> shareText) {
		super();
		this.userVO = userVO;
		this.shareText = shareText;
	}

	@Override
	public String toString() {
		return "PartnerList [userVO=" + userVO + ", shareText=" + shareText + "]";
	}
	
	
	
	
	
}
