package starrail.course.util;

import java.util.Comparator;

import starrail.course.domain.StationVO;

public class StationNameComparator implements Comparator<StationVO> {

	@Override
	public int compare(StationVO o1, StationVO o2) {
		String name1=o1.getName();
		String name2=o2.getName();
		
		
		return name1.compareTo(name2);
	}

}
