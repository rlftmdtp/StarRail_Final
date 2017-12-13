package starrail.payment.domain;

public class PaymentVO {

	private int pay_no;
	private int res_no;
	private String pay_price;
	private String pay_bank;
	private String pay_cardnum;
	private String pay_cardpw;
	private int pay_cvc;
	private int pay_mm;
	private int pay_yy;
	
	public int getPay_no() {
		return pay_no;
	}
	public void setPay_no(int pay_no) {
		this.pay_no = pay_no;
	}
	public int getRes_no() {
		return res_no;
	}
	public void setRes_no(int res_no) {
		this.res_no = res_no;
	}

	public String getPay_price() {
		return pay_price;
	}
	public void setPay_price(String pay_price) {
		this.pay_price = pay_price;
	}
	public String getPay_bank() {
		return pay_bank;
	}
	public void setPay_bank(String pay_bank) {
		this.pay_bank = pay_bank;
	}
	public String getPay_cardnum() {
		return pay_cardnum;
	}
	public void setPay_cardnum(String pay_cardnum) {
		this.pay_cardnum = pay_cardnum;
	}
	public String getPay_cardpw() {
		return pay_cardpw;
	}
	public void setPay_cardpw(String pay_cardpw) {
		this.pay_cardpw = pay_cardpw;
	}
	
	public int getPay_cvc() {
		return pay_cvc;
	}
	public void setPay_cvc(int pay_cvc) {
		this.pay_cvc = pay_cvc;
	}
	public int getPay_mm() {
		return pay_mm;
	}
	public void setPay_mm(int pay_mm) {
		this.pay_mm = pay_mm;
	}
	public int getPay_yy() {
		return pay_yy;
	}
	public void setPay_yy(int pay_yy) {
		this.pay_yy = pay_yy;
	}
	@Override
	public String toString() {
		return "PaymentVO [pay_no=" + pay_no + ", res_no=" + res_no + ", pay_price=" + pay_price + ", pay_bank="
				+ pay_bank + ", pay_cardnum=" + pay_cardnum + ", pay_cardpw=" + pay_cardpw + ", pay_cvc=" + pay_cvc
				+ ", pay_mm=" + pay_mm + ", pay_yy=" + pay_yy + "]";
	}
	
	
	
	
	
}
