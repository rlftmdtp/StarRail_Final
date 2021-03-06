package starrail.review.persistence;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import starrail.review.domain.Hash_SearchVO;

public class RecommendReviewDAOImpl implements RecommendReviewDAO {
	@Inject
	private SqlSession session;
	
	
	private static String namespace = "starrail.review.mappers.recommendReviewMapper";
	
	@Override
	public List<Map<String, Integer>> preferList() {
		return session.selectList(namespace+".prefer_Select");
	}

	@Override
	public List<Hash_SearchVO> tagRecommend(List<Integer> list) {
		//System.out.println("dao : "+ session.selectList(namespace+".tagRecommend_Select", list));
		return session.selectList(namespace+".tagRecommend_Select", list);
	}
}
