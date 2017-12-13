package starrail.map.domain;

public class TourVO {
	private String title;
	private String tel;
	private String addr1;
	private Long contentid;
	private Long contenttypeid;
	private String firstimage;
	private Double mapx;
	private Double mapy;
	
	public TourVO(){}

	public TourVO(String title, String tel, String addr1, Long contentid, Long contenttypeid, String firstimage,
			Double mapx, Double mapy) {
		super();
		this.title = title;
		this.tel = tel;
		this.addr1 = addr1;
		this.contentid = contentid;
		this.contenttypeid = contenttypeid;
		this.firstimage = firstimage;
		this.mapx = mapx;
		this.mapy = mapy;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public Long getContenttypeid() {
		return contenttypeid;
	}

	public void setContenttypeid(Long contenttypeid) {
		this.contenttypeid = contenttypeid;
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
	
}
