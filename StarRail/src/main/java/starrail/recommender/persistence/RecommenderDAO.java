package starrail.recommender.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import starrail.review.domain.Hash_SearchVO;


public interface RecommenderDAO {
	public  List<Map<String, Integer>> preferList();
	public List<Hash_SearchVO> tagRecommend(List<Integer> list);
}
