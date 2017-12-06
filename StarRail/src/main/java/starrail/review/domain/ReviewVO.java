package starrail.review.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

//후기 게시판 테이블
public class ReviewVO implements Serializable{
	private int r_no;
	private String m_id;
	private String r_title;
	private String r_content;
	private Timestamp r_date;
	private int r_hit;

	
	private String[] files;
	
	public ReviewVO() {}
	

	public int getR_no() {
		return r_no;
	}
	public void setR_no(int r_no) {
		this.r_no = r_no;
	}
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public String getR_title() {
		return r_title;
	}
	public void setR_title(String r_title) {
		this.r_title = r_title;
	}
	public String getR_content() {
		return r_content;
	}
	public void setR_content(String r_content) {
		this.r_content = r_content;
	}
	public Timestamp getR_date() {
		return r_date;
	}
	public void setR_date(Timestamp r_date) {
		this.r_date = r_date;
	}
	public int getR_hit() {
		return r_hit;
	}
	public void setR_hit(int r_hit) {
		this.r_hit = r_hit;
	}

	public String[] getFiles() {
		return files;
	}

	public void setFiles(String[] files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "ReviewVO [r_no=" + r_no + ", m_id=" + m_id + ", r_title=" + r_title
				+ ", r_content=" + r_content + ", r_date=" + r_date + ", r_hit=" + r_hit
				+ ", files=" + Arrays.toString(files) + "]";
	}





	
	
}

