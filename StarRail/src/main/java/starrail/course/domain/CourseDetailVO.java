package starrail.course.domain;

public class CourseDetailVO {

	private int cd_id;
	private int c_id;
	private String cd_start;
	private String cd_stime;
	private String cd_end;
	private String cd_etime;
	
	public CourseDetailVO(){}

	public CourseDetailVO(int cd_id, int c_id, String cd_start, String cd_stime, String cd_end, String cd_etime) {

		super();
		this.cd_id = cd_id;
		this.c_id = c_id;
		this.cd_start = cd_start;
		this.cd_stime = cd_stime;
		this.cd_end = cd_end;
		this.cd_etime = cd_etime;
	}

	public int getCd_id() {
		return cd_id;
	}

	public void setCd_id(int cd_id) {
		this.cd_id = cd_id;
	}

	public int getC_id() {
		return c_id;
	}

	public void setC_id(int c_id) {
		this.c_id = c_id;
	}

	public String getCd_start() {
		return cd_start;
	}

	public void setCd_start(String cd_start) {
		this.cd_start = cd_start;
	}

	public String getCd_stime() {
		return cd_stime;
	}

	public void setCd_stime(String cd_stime) {
		this.cd_stime = cd_stime;
	}

	public String getCd_end() {
		return cd_end;
	}

	public void setCd_end(String cd_end) {
		this.cd_end = cd_end;
	}

	public String getCd_etime() {
		return cd_etime;
	}

	public void setCd_etime(String cd_etime) {
		this.cd_etime = cd_etime;
	}

	@Override
	public String toString() {
		return "CourseDetailVO [cd_id=" + cd_id + ", c_id=" + c_id + ", cd_start=" + cd_start + ", cd_stime=" + cd_stime
				+ ", cd_end=" + cd_end + ", cd_etime=" + cd_etime + "]";
	}

}
