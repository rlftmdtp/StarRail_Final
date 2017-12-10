package starrail.recommender.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import starrail.review.domain.Hash_SearchVO;
import starrail.review.domain.ReviewVO;


@Repository
public class RecommenderDAOImpl implements RecommenderDAO {

	@Inject
	private SqlSession session;
	
	private static String namespace = "starrail.recommend.mappers.recommendMapper";
	
	/*@Override
	public List<Map<String, Integer>> preferList() {
		return session.selectList(namespace+".prefer_Select");
	}

	@Override
	public List<Hash_SearchVO> tagRecommend(List<Integer> list) {
		//System.out.println("dao : "+ session.selectList(namespace+".tagRecommend_Select", list));
		return session.selectList(namespace+".tagRecommend_Select", list);
	}*/

	@Override
	public List<ReviewVO> reviewRecommend(String tag) {
		return session.selectList(namespace+".reviewRecommend_Select", tag);
	}
}
