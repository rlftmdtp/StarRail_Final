package starrail.message.domain;

import java.io.Serializable;
import java.util.Date;

public class MessageVO implements Serializable{
	private int msg_no;
	private String m_id; 
	private String msg_sendid; 
	private String msg_content;
	private Date msg_date;
	private int msg_hit;
	
	public MessageVO(){}

	public Date getMsg_date() {
		return msg_date;
	}

	public void setMsg_date(Date msg_date) {
		this.msg_date = msg_date;
	}

	public int getMsg_no() {
		return msg_no;
	}

	public void setMsg_no(int msg_no) {
		this.msg_no = msg_no;
	}

	public String getM_id() {
		return m_id;
	}

	public void setM_id(String m_id) {
		this.m_id = m_id;
	}

	public String getMsg_sendid() {
		return msg_sendid;
	}

	public void setMsg_sendid(String msg_sendid) {
		this.msg_sendid = msg_sendid;
	}

	public String getMsg_content() {
		return msg_content;
	}

	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}


	public int getMsg_hit() {
		return msg_hit;
	}

	public void setMsg_hit(int msg_hit) {
		this.msg_hit = msg_hit;
	}

	@Override
	public String toString() {
		return "MessageVO [msg_no=" + msg_no + ", m_id=" + m_id + ", msg_sendid=" + msg_sendid + ", msg_content="
				+ msg_content + ", msg_date=" + msg_date + ", msg_hit=" + msg_hit + "]";
	}
	
	
}
