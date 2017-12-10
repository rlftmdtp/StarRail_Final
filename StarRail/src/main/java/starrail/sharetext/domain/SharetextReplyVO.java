package starrail.sharetext.domain;

import java.util.Date;

public class SharetextReplyVO {

	private Integer sr_no;
	private Integer sh_no;
	private String sr_content;
	private String replyer;
	private Date sr_date;
	private Date updatesr_date;
	
	
	public Integer getSr_no() {
		return sr_no;
	}
	public void setSr_no(Integer sr_no) {
		this.sr_no = sr_no;
	}
	public Integer getSh_no() {
		return sh_no;
	}
	public void setSh_no(Integer sh_no) {
		this.sh_no = sh_no;
	}
	public String getSr_content() {
		return sr_content;
	}
	public void setSr_content(String sr_content) {
		this.sr_content = sr_content;
	}
	public String getReplyer() {
		return replyer;
	}
	public void setReplyer(String replyer) {
		this.replyer = replyer;
	}
	public Date getSr_date() {
		return sr_date;
	}
	public void setSr_date(Date sr_date) {
		this.sr_date = sr_date;
	}
	public Date getUpdatesr_date() {
		return updatesr_date;
	}
	public void setUpdatesr_date(Date updatesr_date) {
		this.updatesr_date = updatesr_date;
	}
	@Override
	public String toString() {
		return "SharetextReplyVO [sr_no=" + sr_no + ", sh_no=" + sh_no + ", sr_content=" + sr_content + ", replyer="
				+ replyer + ", sr_date=" + sr_date + ", updatesr_date=" + updatesr_date + "]";
	}
	
	
}
