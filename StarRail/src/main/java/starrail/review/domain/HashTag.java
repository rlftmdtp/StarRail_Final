package starrail.review.domain;


//r_no를 통한 내 해시태그 저장
public class HashTag {
	private int h_no;
	private int r_no;
	private String r_hash;
	
	public HashTag(int h_no, int r_no, String r_hash) {
		this.h_no = h_no;
		this.r_no = r_no;
		this.r_hash = r_hash;
	}
	public int getH_no() {
		return h_no;
	}
	public void setH_no(int h_no) {
		this.h_no = h_no;
	}
	public int getR_no() {
		return r_no;
	}
	public void setR_no(int r_no) {
		this.r_no = r_no;
	}
	public String getR_hash() {
		return r_hash;
	}
	public void setR_hash(String r_hash) {
		this.r_hash = r_hash;
	}
	@Override
	public String toString() {
		return "HashTag [h_no=" + h_no + ", r_no=" + r_no + ", r_hash=" + r_hash + "]";
	}
	
	
}
