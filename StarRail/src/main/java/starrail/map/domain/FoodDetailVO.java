package starrail.map.domain;

public class FoodDetailVO {
	private String firstmenu;
	private String treatmenu;
	private String infocenterfood;
	private String opentimefood;
	private String reservationfood;
	private String restdatefood;
	
	public FoodDetailVO(){}

	public FoodDetailVO(String firstmenu, String treatmenu, String infocenterfood, String opentimefood,
			String reservationfood, String restdatefood) {
		super();
		this.firstmenu = firstmenu;
		this.treatmenu = treatmenu;
		this.infocenterfood = infocenterfood;
		this.opentimefood = opentimefood;
		this.reservationfood = reservationfood;
		this.restdatefood = restdatefood;
	}

	public String getFirstmenu() {
		return firstmenu;
	}

	public void setFirstmenu(String firstmenu) {
		this.firstmenu = firstmenu;
	}

	public String getTreatmenu() {
		return treatmenu;
	}

	public void setTreatmenu(String treatmenu) {
		this.treatmenu = treatmenu;
	}

	public String getInfocenterfood() {
		return infocenterfood;
	}

	public void setInfocenterfood(String infocenterfood) {
		this.infocenterfood = infocenterfood;
	}

	public String getOpentimefood() {
		return opentimefood;
	}

	public void setOpentimefood(String opentimefood) {
		this.opentimefood = opentimefood;
	}

	public String getReservationfood() {
		return reservationfood;
	}

	public void setReservationfood(String reservationfood) {
		this.reservationfood = reservationfood;
	}

	public String getRestdatefood() {
		return restdatefood;
	}

	public void setRestdatefood(String restdatefood) {
		this.restdatefood = restdatefood;
	}
	
	
}
