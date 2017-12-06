package starrail.main.dto;

public class LoginDTO {
	
	private String m_id;
	private String m_pw;
	private boolean useCookie;
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
	public boolean isUseCookie() {
		return useCookie;
	}
	public void setUseCookie(boolean useCookie) {
		this.useCookie = useCookie;
	}
	@Override
	public String toString() {
		return "LoginDTO [m_id=" + m_id + ", m_pw=" + m_pw + ", useCookie=" + useCookie + "]";
	}
	
	
}
