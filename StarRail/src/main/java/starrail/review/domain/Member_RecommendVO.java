package starrail.review.domain;

public class Member_RecommendVO {
	private Integer mr_no;
	private String m_id;
	private Integer r_no;
	private Integer mr_count;
	
	public Integer getMr_count() {
		return mr_count;
	}

	public void setMr_count(Integer mr_count) {
		this.mr_count = mr_count;
	}

	public Member_RecommendVO(){}


	public Member_RecommendVO(Integer mr_no, String m_id, Integer r_no, Integer mr_count) {
		super();
		this.mr_no = mr_no;
		this.m_id = m_id;
		this.r_no = r_no;
		this.mr_count = mr_count;
	}

	public Integer getMr_no() {
		return mr_no;
	}

	public void setMr_no(Integer mr_no) {
		this.mr_no = mr_no;
	}

	public String getM_id() {
		return m_id;
	}

	public void setM_id(String m_id) {
		this.m_id = m_id;
	}

	public Integer getR_no() {
		return r_no;
	}

	public void setR_no(Integer r_no) {
		this.r_no = r_no;
	}

	@Override
	public String toString() {
		return "Member_RecommendVO [mr_no=" + mr_no + ", m_id=" + m_id + ", r_no=" + r_no + ", mr_count=" + mr_count
				+ "]";
	}


	
	
}
