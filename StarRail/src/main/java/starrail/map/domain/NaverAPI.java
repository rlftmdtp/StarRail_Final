package starrail.map.domain;

import java.util.List;

public class NaverAPI {
	private String total;
	private FoodVO[] items;

	public NaverAPI(){}

	public NaverAPI(String total, FoodVO[] items) {
		super();
		this.total = total;
		this.items = items;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public FoodVO[] getItems() {
		return items;
	}

	public void setItems(FoodVO[] items) {
		this.items = items;
	}
}
