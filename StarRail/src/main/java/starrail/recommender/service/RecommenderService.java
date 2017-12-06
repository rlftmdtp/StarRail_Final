package starrail.recommender.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.INTERNAL;

import starrail.review.domain.Hash_SearchVO;
import starrail.review.domain.ReviewVO;


public interface RecommenderService {
/*	public List<Integer> preferList_service(int m_no) throws Exception;
	public List<Integer> recommender_service(int m_no);
	public List<Hash_SearchVO> tagRecommend_service(List<Integer> list);*/
	public List<ReviewVO> reviewRecommend(String tag);
}
