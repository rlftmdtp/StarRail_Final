package starrail.map.domain;

public class StayDetailVO {
	private String checkintime;
	private String checkouttime;
	private String reservationlodging;
	private String reservationurl;
	private String roomcount;
	private String firstimage;
	
	public StayDetailVO(){}

	public StayDetailVO(String checkintime, String checkouttime, String reservationlodging, String reservationurl,
			String roomcount, String firstimage) {
		super();
		this.checkintime = checkintime;
		this.checkouttime = checkouttime;
		this.reservationlodging = reservationlodging;
		this.reservationurl = reservationurl;
		this.roomcount = roomcount;
		this.firstimage = firstimage;
	}

	public String getCheckintime() {
		return checkintime;
	}

	public void setCheckintime(String checkintime) {
		this.checkintime = checkintime;
	}

	public String getCheckouttime() {
		return checkouttime;
	}

	public void setCheckouttime(String checkouttime) {
		this.checkouttime = checkouttime;
	}

	public String getReservationlodging() {
		return reservationlodging;
	}

	public void setReservationlodging(String reservationlodging) {
		this.reservationlodging = reservationlodging;
	}

	public String getReservationurl() {
		return reservationurl;
	}

	public void setReservationurl(String reservationurl) {
		this.reservationurl = reservationurl;
	}

	public String getRoomcount() {
		return roomcount;
	}

	public void setRoomcount(String roomcount) {
		this.roomcount = roomcount;
	}

	public String getFirstimage() {
		return firstimage;
	}

	public void setFirstimage(String firstimage) {
		this.firstimage = firstimage;
	}

}
