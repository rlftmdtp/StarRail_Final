package starrail.reservation.domain;

public class SeatVO {
	private String seat_id;
	private String seat_num;
	private String train_count;
	private String train_type;
	
	public String getSeat_id() {
		return seat_id;
	}
	public void setSeat_id(String seat_id) {
		this.seat_id = seat_id;
	}
	public String getSeat_num() {
		return seat_num;
	}
	public void setSeat_num(String seat_num) {
		this.seat_num = seat_num;
	}
	public String getTrain_count() {
		return train_count;
	}
	public void setTrain_count(String train_count) {
		this.train_count = train_count;
	}
	public String getTrain_type() {
		return train_type;
	}
	public void setTrain_type(String train_type) {
		this.train_type = train_type;
	}
	
	
}
