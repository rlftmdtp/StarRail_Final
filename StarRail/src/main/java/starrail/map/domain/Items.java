package starrail.map.domain;

import java.util.List;

public class Items {
	public List<FoodVO> list;
	
	public Items(){}
	
	public Items(List<FoodVO> list) {
		super();
		this.list = list;
	}

	public List<FoodVO> getList() {
		return list;
	}

	public void setList(List<FoodVO> list) {
		this.list = list;
	}
	
	
}
