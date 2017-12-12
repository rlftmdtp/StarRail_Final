package starrail.map.domain;

public class FestivalVO {
	private String addr1;
	private Long contentid;
	private String firstimage;
	private Double mapx;
	private Double mapy;
	private String tel;
	private String title;
	
	public FestivalVO(){}

	public FestivalVO(String addr1, Long contentid, String firstimage, Double mapx, Double mapy, String tel,
			String title) {
		super();
		this.addr1 = addr1;
		this.contentid = contentid;
		this.firstimage = firstimage;
		this.mapx = mapx;
		this.mapy = mapy;
		this.tel = tel;
		this.title = title;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public Long getContentid() {
		return contentid;
	}

	public void setContentid(Long contentid) {
		this.contentid = contentid;
	}

	public String getFirstimage() {
		return firstimage;
	}

	public void setFirstimage(String firstimage) {
		this.firstimage = firstimage;
	}

	public Double getMapx() {
		return mapx;
	}

	public void setMapx(Double mapx) {
		this.mapx = mapx;
	}

	public Double getMapy() {
		return mapy;
	}

	public void setMapy(Double mapy) {
		this.mapy = mapy;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	
}
