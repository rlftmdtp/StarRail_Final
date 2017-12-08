package starrail.review.service;

import java.util.List;

import starrail.review.domain.Hash_SearchVO;

public interface RecommendReviewService {
	public List<Integer> preferList_service(int m_no) throws Exception;
	public List<Integer> recommender_service(int m_no);
	public List<Hash_SearchVO> tagRecommend_service(List<Integer> list);
}
