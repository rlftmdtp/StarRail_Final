package starrail.map.domain;

public class StationXYVO {
	
	private Float s_x;
	private Float s_y;
	
	public StationXYVO(){}
	
	public StationXYVO(Float s_x, Float s_y) {
		super();
		this.s_x = s_x;
		this.s_y = s_y;
	}

	public Float getS_x() {
		return s_x;
	}

	public void setS_x(Float s_x) {
		this.s_x = s_x;
	}

	public Float getS_y() {
		return s_y;
	}

	public void setS_y(Float s_y) {
		this.s_y = s_y;
	}

	@Override
	public String toString() {
		return "StationXYVO [s_x=" + s_x + ", s_y=" + s_y + "]";
	}
	
}
