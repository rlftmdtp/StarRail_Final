package starrail.recommender.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.stereotype.Service;

import starrail.recommender.persistence.RecommenderDAO;
import starrail.review.domain.Hash_SearchVO;

@Service
public class RecommenderServiceImpl implements RecommenderService {

	@Inject
	private RecommenderDAO dao;
	
	@Override
	public   List<Integer> preferList_service(int m_no) throws Exception {
		// 추천하기 위해 전체 사용자의 회원가입 정보를 가져옴 -> 그 내용을 csv파일로 생성함
		List<Map<String, Integer>> list = dao.preferList();
		BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\data\\member_prefer.csv"));
	
		for(int i=0; i<list.size(); i++) {
			bw.write(list.get(i).get("M_NO") + "," + list.get(i).get("TAG_NO") + "\n");
		}
		bw.close();
		
		List<Integer> recommend_list = recommender_service(m_no);
		return recommend_list;
	}

	
	
	// 추천 페이지에 들어가면 로그인한 사용자에게 맞춤 태그를 추천해주는 메소드
	@Override
	public List<Integer> recommender_service(int m_no) {
		List<Integer> list = new ArrayList<Integer>();

		try {			
			// 데이터 모델 생성
			DataModel dm = new FileDataModel(new File("C:\\data\\member_prefer.csv"));
			
			// 유사도 모델 : ItemSimilarity 사용
			ItemSimilarity sim = new LogLikelihoodSimilarity(dm);

			// 추천기 선택 : ItemBased
			GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dm, sim);

			int x = 1;

			System.out.println("LogLikelihoodSimilarity 이용" + m_no);			
				
			// 현재 item ID -> id는 오류를 방지하기 위해 long타입 사용
			long m_No = m_no;
				
			// 현재 item아이디와 가장 유사한 5개의 아이템 추천
			List<RecommendedItem> recommendations = recommender.mostSimilarItems(m_No, 8);
				
			// 유사한 아이템 출력 = '현재 아이템ID | 추천된 아이템ID | 유사도' ==> 유사도가 1에 가까울수록 추천순위가 높은것임
			for (RecommendedItem recommendation : recommendations) {
				System.out.println("추천 결과 : " + m_No + "," + recommendation.getItemID());
				list.add((int) recommendation.getItemID());
				System.out.println(list);
				System.out.println("dd");
			}

		} catch (IOException e) {
			System.out.println("there was an error.");
			e.printStackTrace();
		} catch (TasteException e) {
			System.out.println("there was an Taste Exception.");
			e.printStackTrace();
		}
		
		return list;		
	}



	@Override
	public List<Hash_SearchVO> tagRecommend_service(List<Integer> list) {
		//System.out.println("서비스 : "+dao.tagRecommend(list));
		return dao.tagRecommend(list);
	}







}
