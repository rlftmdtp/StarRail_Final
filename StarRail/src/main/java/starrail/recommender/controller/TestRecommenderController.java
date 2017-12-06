package starrail.recommender.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.math.hadoop.similarity.cooccurrence.measures.LoglikelihoodSimilarity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import starrail.course.domain.CourseVO;
import starrail.main.domain.UserVO;
import starrail.partner.service.PartnerService;

@Controller
@RequestMapping("/recomm/*")
public class TestRecommenderController {

	// 처음 동행 페이지 클릭 시 실행되는 컨트롤러
	@RequestMapping(value = "/recomm", method = RequestMethod.GET)
	public void create_CSV_GET() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("C:\\data\\u.data"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\data\\movies_info.csv"));

		String line;
		while ((line = br.readLine()) != null) {
			String[] values = line.split("\\t", -1);
			bw.write(values[0] + "," + values[1] + "," + values[2] + "," + values[3] + "\n");
		}

		br.close();
		bw.close();
	}

	// 피어슨 상관계수
	@RequestMapping(value = "/test1", method = RequestMethod.GET)
	public void Pearson_GET() throws Exception {

		// 데이터 모델 생성
		DataModel dm = new FileDataModel(new File("C:\\data\\movies_info.csv"));

		// 피어슨 - 유사도 모델 생성
		UserSimilarity sim = new PearsonCorrelationSimilarity(dm);

		// 가장 유사한 유저 2명을 기준으로 잡음
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, sim, dm);

		// 사용자 추천기 생성
		Recommender recommender = new GenericUserBasedRecommender(dm, neighborhood, sim);

		// 1번 유저에게 3개의 아이템 추천
		List<RecommendedItem> recommendations = recommender.recommend(1, 3);

		for (RecommendedItem recommendation : recommendations) {
			System.out.println("피어슨 recommendation" + recommendation);
		}
	}

	// 타니모토 계수
	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	public void tanimotoCoefficient_GET() throws Exception {

		// 데이터 모델 생성
		DataModel dm = new FileDataModel(new File("C:\\data\\movies_info.csv"));

		// 타니모토 - 유사도 모델 생성
		UserSimilarity sim = new TanimotoCoefficientSimilarity(dm);

		// 가장 유사한 유저 2명을 기준으로 잡음
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, sim, dm);

		// 사용자 추천기 생성
		Recommender recommender = new GenericUserBasedRecommender(dm, neighborhood, sim);

		// 1번 유저에게 3개의 아이템 추천
		List<RecommendedItem> recommendations = recommender.recommend(1, 3);

		for (RecommendedItem recommendation : recommendations) {
			System.out.println("타니모토 계수 : " + recommendation);
		}
	}

	// 스피어만 상관 계수
	@RequestMapping(value = "/test3", method = RequestMethod.GET)
	public void spearmanCorrelationSimilarity_GET() throws Exception {

		// 데이터 모델 생성
		DataModel dm = new FileDataModel(new File("C:\\data\\movies_info.csv"));

		// 타니모토 - 유사도 모델 생성
		UserSimilarity sim = new SpearmanCorrelationSimilarity(dm);

		// 가장 유사한 유저 2명을 기준으로 잡음
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, sim, dm);

		// 사용자 추천기 생성
		Recommender recommender = new GenericUserBasedRecommender(dm, neighborhood, sim);

		// 1번 유저에게 3개의 아이템 추천
		List<RecommendedItem> recommendations = recommender.recommend(1, 3);

		for (RecommendedItem recommendation : recommendations) {
			System.out.println("스피어만 상관계수 : " + recommendation);
		}
	}

	// 유클리디언 거리
	@RequestMapping(value = "/test4", method = RequestMethod.GET)
	public void euclidean_GET() throws Exception {

		// 데이터 모델 생성
		DataModel dm = new FileDataModel(new File("C:\\data\\movies_info.csv"));

		// 타니모토 - 유사도 모델 생성
		UserSimilarity sim = new EuclideanDistanceSimilarity(dm);

		// 가장 유사한 유저 2명을 기준으로 잡음
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, sim, dm);

		// 사용자 추천기 생성
		Recommender recommender = new GenericUserBasedRecommender(dm, neighborhood, sim);

		// 1번 유저에게 3개의 아이템 추천
		List<RecommendedItem> recommendations = recommender.recommend(1, 3);

		for (RecommendedItem recommendation : recommendations) {
			System.out.println("유클리디언 거리 : " + recommendation);
		}
	}

	
	// 아이템기반 유사도 : LogLikelihoodSimilarity사용 
	@RequestMapping(value = "/test5", method = RequestMethod.GET)
	public void item_LogLiklihood_GET() throws Exception {

		try {
			// 데이터 모델 생성
			DataModel dm = new FileDataModel(new File("C:\\data\\test.csv"));
			
			// 유사도 모델 : ItemSimilarity 사용
			ItemSimilarity sim = new LogLikelihoodSimilarity(dm);

			// 추천기 선택 : ItemBased
			GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dm, sim);

			int x = 1;

			System.out.println("LogLikelihoodSimilarity 이용");
			
			// 데이터 모델 내의 item들의 iterator를 단계별로 이동하며 추천 아이템들 제공
			for (LongPrimitiveIterator items = dm.getItemIDs(); items.hasNext();) {
				
				// 현재 item ID -> id는 오류를 방지하기 위해 long타입 사용
				long itemId = items.nextLong();
				
				// 현재 item아이디와 가장 유사한 5개의 아이템 추천
				List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemId, 1);
				
				// 유사한 아이템 출력 = '현재 아이템ID | 추천된 아이템ID | 유사도' ==> 유사도가 1에 가까울수록 추천순위가 높은것임
				for (RecommendedItem recommendation : recommendations) {
					//System.out.println(itemId + "," + recommendation.getItemID() + "," + recommendation.getValue());
					System.out.println("추천 결과 : " + itemId + "," + recommendation.getItemID());
					
				}
				
                // 유저 1번 2번에게 아이템ID 5까지 유사한 아이템들 5개씩 추천해준다
				x++;
				if(x > 2) System.exit(1);
			}

		} catch (IOException e) {
			System.out.println("there was an error.");
			e.printStackTrace();
		} catch (TasteException e) {
			System.out.println("there was an Taste Exception.");
			e.printStackTrace();
		}
	}

	
	// 아이템기반 유사도 : TanimotoCoefficientSimilarity사용 
	@RequestMapping(value = "/test6", method = RequestMethod.GET)
	public void tanimotoCoefficientSimilarity_GET() throws Exception {

		try {
			// 데이터 모델 생성
			DataModel dm = new FileDataModel(new File("C:\\data\\movies_info.csv"));
			
			// 유사도 모델 : TanimotoCoefficientSimilarity 사용
			TanimotoCoefficientSimilarity sim = new TanimotoCoefficientSimilarity(dm);

			// 추천기 선택 : TanimotoCoefficientSimilarity
			GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dm, sim);

			int x = 1;

			System.out.println("TanimotoCoefficientSimilarity 이용");
			
			// 데이터 모델 내의 item들의 iterator를 단계별로 이동하며 추천 아이템들 제공
			for (LongPrimitiveIterator items = dm.getItemIDs(); items.hasNext();) {
				
				// 현재 item ID -> id는 오류를 방지하기 위해 long타입 사용
				long itemId = items.nextLong();
				
				// 현재 item아이디와 가장 유사한 5개의 아이템 추천
				List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemId, 5);
				
				// 유사한 아이템 출력 = '현재 아이템ID | 추천된 아이템ID | 유사도' ==> 유사도가 1에 가까울수록 추천순위가 높은것임
				for (RecommendedItem recommendation : recommendations) {
					System.out.println(itemId + "," + recommendation.getItemID() + "," + recommendation.getValue());
				}
				
				// 아이템ID 5까지 유사한 아이템들 5개씩 추천해준다
				x++;
				if(x > 2) System.exit(1);
			}

		} catch (IOException e) {
			System.out.println("there was an error.");
			e.printStackTrace();
		} catch (TasteException e) {
			System.out.println("there was an Taste Exception.");
			e.printStackTrace();
		}
	}
	
	
}
