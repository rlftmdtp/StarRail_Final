package starrail.main.domain;

public class UserVO {
	private String m_id;
	private String m_pw;
	private String m_name;
	private String m_gender;
	private int m_age;
	private int m_no;
	
	public UserVO(){}
	
	
	
	public UserVO(String m_id, String m_pw, String m_name, String m_gender, int m_age, int m_no) {
		super();
		this.m_id = m_id;
		this.m_pw = m_pw;
		this.m_name = m_name;
		this.m_gender = m_gender;
		this.m_age = m_age;
		this.m_no = m_no;
	}



	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public String getM_pw() {
		return m_pw;
	}
	public void setM_pw(String m_pw) {
		this.m_pw = m_pw;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public String getM_gender() {
		return m_gender;
	}
	public void setM_gender(String m_gender) {
		this.m_gender = m_gender;
	}
	public int getM_age() {
		return m_age;
	}
	public void setM_age(int m_age) {
		this.m_age = m_age;
	}
	public int getM_no() {
		return m_no;
	}
	public void setM_no(int m_no) {
		this.m_no = m_no;
	}



	@Override
	public String toString() {
		return "UserVO [m_id=" + m_id + ", m_pw=" + m_pw + ", m_name=" + m_name + ", m_gender=" + m_gender + ", m_age="
				+ m_age + ", m_no=" + m_no + "]";
	}
	
	
	
}
