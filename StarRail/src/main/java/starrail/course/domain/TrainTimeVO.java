package starrail.course.domain;

public class TrainTimeVO implements Comparable<TrainTimeVO>{
	
	private String trainType;
	private String depTime;
	private String arrTime;
	
	
	
	public String getTrainType() {
		return trainType;
	}
	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}
	public String getDepTime() {
		return depTime;
	}
	public void setDepTime(String depTime) {
		this.depTime = depTime;
	}
	public String getArrTime() {
		return arrTime;
	}
	public void setArrTime(String arrTime) {
		this.arrTime = arrTime;
	}
	@Override
	public int compareTo(TrainTimeVO o) {
		
		return arrTime.compareTo(o.arrTime);
	}
	

}
