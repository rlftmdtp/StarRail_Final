package starrail.course.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.course.domain.IssueInfoVO;
import starrail.course.domain.StationCoorVO;
import starrail.course.persistence.CourseDAO;

@Service
public class CourseServiceImpl implements CourseService {

	@Inject
	private CourseDAO dao;
	
	@Override
	public List<IssueInfoVO> issueList(List<String> nodes) throws Exception {
		List<IssueInfoVO> list = new ArrayList<IssueInfoVO>();
		
		for(int i=0; i<nodes.size(); i++){
			list.add(dao.issueSelect(nodes.get(i)));
		}
		
		return list;
	}

	@Override
	public void courseRegist(CourseVO c, List<CourseDetailVO> cds) throws Exception {
		int c_id = dao.selectC_id()+1;
		
		c.setC_id(c_id);

		dao.courseInsert(c);
		for(int i=0; i<cds.size(); i++){
			cds.get(i).setC_id(c_id);
			cds.get(i).setCd_id(dao.selectCd_id()+1);
			dao.courseDetailInsert(cds.get(i));
		}
	}

	@Override
	public CourseVO courseRead(Integer c_id) throws Exception {
		return dao.courseSelect(c_id);
	}

	@Override
	public List<CourseDetailVO> courseDetailList(Integer c_id) throws Exception {
		List<CourseDetailVO> list = dao.courseDetailList(c_id);
		Collections.sort(list, new Comparator<CourseDetailVO>(){

			@Override
			public int compare(CourseDetailVO o1, CourseDetailVO o2) {
				String cd_stime1=o1.getCd_stime();
				String cd_stime2=o2.getCd_stime();
				return cd_stime1.compareTo(cd_stime2);
			}
		});
		return list;
	}

	@Override
	public void courseModify(CourseVO c, List<CourseDetailVO> cds) throws Exception {
		dao.courseDetailDelete(c.getC_id());
		dao.courseUpdate(c);
		
		for(int i=0; i<cds.size(); i++){
			cds.get(i).setC_id(c.getC_id());
			cds.get(i).setCd_id(dao.selectCd_id()+1);
			dao.courseDetailInsert(cds.get(i));
		}
		
	}

	@Override
	public StationCoorVO getCoordinateService(String stc_name) throws Exception {
		
		return dao.getCoordinate(stc_name);
	}

	

}
