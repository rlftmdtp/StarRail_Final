package starrail.review.domain;


//해시태그 전체 테이블
public class Hash_SearchVO {
	private int hs_no;
	private String hs_search;
	private int hs_count;
	
	public int getHs_no() {
		return hs_no;
	}
	public void setHs_no(int hs_no) {
		this.hs_no = hs_no;
	}
	
	public String getHs_search() {
		return hs_search;
	}
	public void setHs_search(String hs_search) {
		this.hs_search = hs_search;
	}
	public int getHs_count() {
		return hs_count;
	}
	public void setHs_count(int hs_count) {
		this.hs_count = hs_count;
	}
	
	@Override
	public String toString() {
		return "Hash_SearchVO [hs_no=" + hs_no + ", hs_search=" + hs_search + ", hs_count="
				+ hs_count + "]";
	}
	
	
}
