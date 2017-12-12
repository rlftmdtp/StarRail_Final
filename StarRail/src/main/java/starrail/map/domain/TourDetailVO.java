package starrail.map.domain;

public class TourDetailVO {
	private String infocenter;
	private String useseason;
	private String usetime;
	
	public TourDetailVO(){}
	
	public TourDetailVO(String infocenter, String useseason, String usetime) {
		super();
		this.infocenter = infocenter;
		this.useseason = useseason;
		this.usetime = usetime;
	}

	public String getInfocenter() {
		return infocenter;
	}

	public void setInfocenter(String infocenter) {
		this.infocenter = infocenter;
	}

	public String getUseseason() {
		return useseason;
	}

	public void setUseseason(String useseason) {
		this.useseason = useseason;
	}

	public String getUsetime() {
		return usetime;
	}

	public void setUsetime(String usetime) {
		this.usetime = usetime;
	}
	
	
	
}
