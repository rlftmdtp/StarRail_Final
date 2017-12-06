package starrail.map.domain;

public class FoodVO {
	private String title;
	private String category;
	private String description;
	private String telephone;
	private String address;
	private String mapx;
	private String mapy;
	
	public FoodVO(){};
	
	public FoodVO(String title, String category, String description, String telephone, String address, String mapx,
			String mapy) {
		super();
		this.title = title;
		this.category = category;
		this.description = description;
		this.telephone = telephone;
		this.address = address;
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
	public String getMapx() {
		return mapx;
	}
	public void setMapx(String mapx) {
		this.mapx = mapx;
	}
	public String getMapy() {
		return mapy;
	}
	public void setMapy(String mapy) {
		this.mapy = mapy;
	}
	
	@Override
	public String toString() {
		return "Food [title=" + title + ", category=" + category + ", description=" + description + ", telephone="
				+ telephone + ", address=" + address + ", mapx=" + mapx + ", mapy=" + mapy + "]";
	}
}
