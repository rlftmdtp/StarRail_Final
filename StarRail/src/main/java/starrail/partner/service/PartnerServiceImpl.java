package starrail.partner.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.main.domain.UserVO;
import starrail.partner.persistence.PartnerDAO;

@Service
public class PartnerServiceImpl implements PartnerService {

	@Inject
	private PartnerDAO dao;

	
	@Override
	public List<CourseVO> myCourse_List_service(UserVO userVO) {
		// System.out.println("서비스 : " + userVO);
		return dao.myCourse_List(userVO);
	}

	
	@Override
	public List<CourseDetailVO> mySchedule_List_service(HashMap<String, Object> map) {
		//System.out.println("서비스 : " + dao.mySchedule_List(map));
		return dao.mySchedule_List(map);
	}

	
	@Override
	public List<CourseDetailVO> courseDetail_List_service(Map<String, String> list) {
		List<Integer> list_value = new ArrayList<Integer>();

		for (String mapkey : list.keySet()) {
			int cd_id = Integer.parseInt(list.get(mapkey));
			list_value.add(cd_id);
		}
		System.out.println("*****" + list_value);
		return dao.courseDetail_List(list_value);
	}

	
	@Override
	public List<UserVO> partnerSearch_List_service(CourseDetailVO courseDetailVO) {		
/*		Map<String, Object> map = new HashMap<String, Object>();
		
		for(int i=0; i<list.size(); i++){			
			List<UserVO> user_list = dao.partnerSearch_List(list.get(i));
			map.put("course"+i, user_list);
		}		
		System.out.println("####map사이즈 : "+map.size());*/
		
		
		List<UserVO> user_list = dao.partnerSearch_List(courseDetailVO);		
		return user_list;
	}


	@Override
	public CourseDetailVO courseDetail_Search_List_service(Integer cd_id) {
		return  dao.courseDetail_Search_List(cd_id);
	}

}
