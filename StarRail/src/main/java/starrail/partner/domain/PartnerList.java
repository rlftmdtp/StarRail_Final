package starrail.partner.domain;

import java.util.List;

import starrail.main.domain.UserVO;
import starrail.sharetext.domain.ShareTextVO;

public class PartnerList {
	private List<UserVO> userVO;
	private ShareTextVO shareText;
	
	public PartnerList(){}

	public PartnerList(List<UserVO> userVO, ShareTextVO shareText) {
		super();
		this.userVO = userVO;
		this.shareText = shareText;
	}

	public List<UserVO> getUserVO() {
		return userVO;
	}

	public void setUserVO(List<UserVO> userVO) {
		this.userVO = userVO;
	}

	public ShareTextVO getShareText() {
		return shareText;
	}

	public void setShareText(ShareTextVO shareText) {
		this.shareText = shareText;
	}

	@Override
	public String toString() {
		return "PartnerList [userVO=" + userVO + ", shareText=" + shareText + "]";
	}
	
}
