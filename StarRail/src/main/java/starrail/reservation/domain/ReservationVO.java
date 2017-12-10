package starrail.reservation.domain;



public class ReservationVO {

	private Integer res_no;
	private String m_id;
	private String res_sdate;
	private String res_tcount;
	private String i_name;
	
	public int getRes_no() {
		return res_no;
	}
	public void setRes_no(Integer res_no) {
		this.res_no = res_no;
	}
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public String getRes_sdate() {
		return res_sdate;
	}
	public void setRes_sdate(String res_sdate) {
		this.res_sdate = res_sdate;
	}
	public String getRes_tcount() {
		return res_tcount;
	}
	public void setRes_tcount(String res_tcount) {
		this.res_tcount = res_tcount;
	}
	public String getI_name() {
		return i_name;
	}
	public void setI_name(String i_name) {
		this.i_name = i_name;
	}
	@Override
	public String toString() {
		return "ReservationVO [res_no=" + res_no + ", m_id=" + m_id + ", res_sdate=" + res_sdate + ", res_tcount="
				+ res_tcount + ", i_name=" + i_name + "]";
	}
	
	
}
