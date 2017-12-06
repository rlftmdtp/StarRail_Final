package starrail.expenses.domain;


//지출내역 테이블
public class StatementVO {
	private int ed_no;
	private int e_no;
	private String ed_date;
	private String ed_kategorie;
	private String ed_katename;
	private int ed_amount;
	private String ed_plma;
	
	public int getEd_no() {
		return ed_no;
	}
	public void setEd_no(int ed_no) {
		this.ed_no = ed_no;
	}
	public int getE_no() {
		return e_no;
	}
	public void setE_no(int e_no) {
		this.e_no = e_no;
	}
	public String getEd_date() {
		return ed_date;
	}
	public void setEd_date(String ed_date) {
		this.ed_date = ed_date;
	}
	public String getEd_kategorie() {
		return ed_kategorie;
	}
	public void setEd_kategorie(String ed_kategorie) {
		this.ed_kategorie = ed_kategorie;
	}
	public String getEd_katename() {
		return ed_katename;
	}
	public void setEd_katename(String ed_katename) {
		this.ed_katename = ed_katename;
	}
	public int getEd_amount() {
		return ed_amount;
	}
	public void setEd_amount(int ed_amount) {
		this.ed_amount = ed_amount;
	}
	public String getEd_plma() {
		return ed_plma;
	}
	public void setEd_plma(String ed_plma) {
		this.ed_plma = ed_plma;
	}
	@Override
	public String toString() {
		return "StatementVO [ed_no=" + ed_no + ", e_no=" + e_no + ", ed_date=" + ed_date + ", ed_kategorie="
				+ ed_kategorie + ", ed_katename=" + ed_katename + ", ed_amount=" + ed_amount + ", ed_plma=" + ed_plma
				+ "]";
	}
	
	
	
	
}
