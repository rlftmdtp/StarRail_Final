package starrail.map.domain;

public class FestivalDetailVO {
	private String addr1;
	private String firstimage;
	private String homepage;
	private String overview;
	private String tel;
	private String telname;
	private String title;
	
	public FestivalDetailVO(){}

	public FestivalDetailVO(String addr1, String firstimage, String homepage, String overview, String tel,
			String telname, String title) {
		super();
		this.addr1 = addr1;
		this.firstimage = firstimage;
		this.homepage = homepage;
		this.overview = overview;
		this.tel = tel;
		this.telname = telname;
		this.title = title;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getFirstimage() {
		return firstimage;
	}

	public void setFirstimage(String firstimage) {
		this.firstimage = firstimage;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTelname() {
		return telname;
	}

	public void setTelname(String telname) {
		this.telname = telname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
