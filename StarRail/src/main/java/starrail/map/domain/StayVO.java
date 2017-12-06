package starrail.map.domain;

public class StayVO {
	private String title;
	private String category;
	private String description;
	private String telephone;
	private String address;
	private String link;
	private Integer mapx;
	private Integer mapy;
	
	public StayVO(){}

	public StayVO(String title, String category, String description, String telephone, String address, String link,
			Integer mapx, Integer mapy) {
		super();
		this.title = title;
		this.category = category;
		this.description = description;
		this.telephone = telephone;
		this.address = address;
		this.link = link;
		this.mapx = mapx;
		this.mapy = mapy;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getMapx() {
		return mapx;
	}

	public void setMapx(Integer mapx) {
		this.mapx = mapx;
	}

	public Integer getMapy() {
		return mapy;
	}

	public void setMapy(Integer mapy) {
		this.mapy = mapy;
	};
	
	

}
