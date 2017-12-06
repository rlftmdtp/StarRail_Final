package starrail.partner.persistence;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.main.domain.UserVO;

@Repository
public class PartnerDAOImpl implements PartnerDAO {

	@Inject
	private SqlSession session;
	
	private static String namespace = "starrail.partner.mappers.partnerMapper";

	
	// 로그인되어있는 m_id에 해당하는 CourseVO객체를 리턴
	@Override
	public List<CourseVO> myCourse_List(UserVO userVO) {
		//System.out.println("DAO : " + userVO);
		 //System.out.println(userVO.getM_id());
		 String m_id = userVO.getM_id();
		return session.selectList(namespace+".myCourse_Select", m_id);
	}


	@Override
	public List<CourseDetailVO> mySchedule_List(HashMap<String, Object> map) {
		//System.out.println("dao : " + session.selectList(namespace+".mySchedule_Select", map));
		return session.selectList(namespace+".mySchedule_Select", map);
	}


	@Override
	public List<CourseDetailVO> courseDetail_List(List<Integer> list) {
		
		return session.selectList(namespace+".courseDetail_Select", list);
	}


	@Override
	public List<UserVO> partnerSearch_List(CourseDetailVO courseDetailVO) {
		return session.selectList(namespace+".partnerSearch_Select", courseDetailVO);
	}


	@Override
	public CourseDetailVO courseDetail_Search_List(Integer cd_id) {
		return session.selectOne(namespace+".cd_Select", cd_id);
	}
	
	
	
	
	
	
	
	
	
	
	
}
