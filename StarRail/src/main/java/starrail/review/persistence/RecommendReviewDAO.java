package starrail.review.persistence;

import java.util.List;
import java.util.Map;

import starrail.review.domain.Hash_SearchVO;

public interface RecommendReviewDAO {
	public  List<Map<String, Integer>> preferList();
	public List<Hash_SearchVO> tagRecommend(List<Integer> list);
}
