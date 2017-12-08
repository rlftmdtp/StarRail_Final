package starrail.review.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import starrail.review.domain.FileVO;
import starrail.review.domain.Hash_SearchVO;
import starrail.review.domain.Member_RecommendVO;
import starrail.review.domain.ReviewVO;
import starrail.review.domain.ReviewCriteria;
import starrail.review.domain.ReviewSearchCriteria;
import starrail.review.persistence.ReviewDao;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Inject
	private ReviewDao dao;


	@Transactional
	@Override		//후기 게시판 등록
	public void register(ReviewVO review) throws Exception {

		
		if (dao.selectR_no() != null) {
			review.setR_no(dao.selectR_no() + 1);
		}else{
			review.setR_no(1);
		}

		dao.insertReview(review);

		String[] files = review.getFiles();
		int r_no = dao.getR_no();
		System.out.println("num: " + r_no + " files : " + files);
		FileVO fileVO = new FileVO();
		fileVO.setR_no(r_no);

		if (files == null) {
			return;
		}

		for (String fileName : files) {
			fileVO.setRf_fullname(fileName);
			dao.addAttach(fileVO);
		}
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override	//후기게시판 상세보기
	public ReviewVO read(Integer r_no) throws Exception {
		dao.updateR_hit(r_no);
		return dao.selectReview(r_no);
	}

	@Transactional
	@Override	//후기게시판 수정하기
	public void modify(ReviewVO review) throws Exception {
		System.out.println("吏깅삓  : " + review.toString());
		dao.updateReview(review);

		Integer r_no = review.getR_no();
		dao.deleteAttach(r_no);

		String[] files = review.getFiles();
		if (files == null) {
			return;
		}
		for (String fileName : files) {
			dao.repalceAttach(fileName, r_no);
		}
	}

	@Transactional
	@Override	//후기게시판 삭제
	public void remove(Integer r_no) throws Exception {
		dao.deleteAttach(r_no);
		dao.deleteReview(r_no);
	}

	@Override	//전체보기
	public List<ReviewVO> list() throws Exception {
		return dao.listReview();
	}

	@Override	//페이징처리
	public List<ReviewVO> listCriteria(ReviewCriteria cri) throws Exception {
		return dao.listCriteria(cri);
	}

	@Override
	public int listCountCriteria(ReviewCriteria cri) throws Exception {
		return dao.countPaging(cri);
	}

	@Override	//검색 + 페이징
	public List<ReviewVO> listSearchCriteria(ReviewSearchCriteria cri) throws Exception {
		return dao.listSearch(cri);
	}

	@Override
	public int listSearchCount(ReviewSearchCriteria cri) throws Exception {
		return dao.listSearchCount(cri);
	}

	@Override	//파일 불러오기
	public List<String> getAttach(Integer r_no) throws Exception {
		return dao.getAttach(r_no);
	}

	@Override	//hash 글번호 가져오기
	public int hash_no() throws Exception {
		if (dao.hash_no() == null) {
			return 0;
		} else
			return dao.hash_no();
	}

	@Override
	public Integer selectR_no() throws Exception {
		if (dao.selectR_no() == null) {
			return 0;
		} else {
			return dao.selectR_no();
		}
	}

	@Override	//해시태그 정규표현식으로 자르기
	public List<String> hashtagInsert(ReviewVO review, Hash_SearchVO searchVO) throws Exception {
		//정규표현식
		Pattern p = Pattern.compile("\\#([0-9a-zA-Z가-힣]*)");
		//후기게시판 내용에 있는 것들 가져와서
		Matcher m = p.matcher(review.getR_content());
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<String> list = new ArrayList<>();
		int h_no = 0;
		String r_hash = null;
		
		while (m.find()) {
			r_hash = specialCharacter_replace(m.group());
			System.out.println("서비스 r_hash : " + r_hash);
			if (dao.hash_no() != null) {
				h_no = dao.hash_no() + 1;
				System.out.println("서비스 h_no : "+h_no);
			}else{
				h_no = 1;
			}
				paramMap.put("h_no", h_no);
				paramMap.put("r_no", review.getR_no());
				paramMap.put("r_hash", r_hash);
				System.out.println("서비스 : "+paramMap);
				dao.tagAdd(paramMap);
				list.add(r_hash);
			}
		
		return list;

	}

	@Override
	public String specialCharacter_replace(String str) throws Exception {
		str = StringUtils.replace(str, "-_+=!@#$%^&*()[]{}|\\;:'\"<>,.?/~`） ", "");

		if (str.length() < 1) {
			return null;
		}
		return str;
	}

	@Override	//태그 전체 가져오기
	public List<String> hashSearch() throws Exception {

		return dao.HashSearch();
	}

	@Override	//게시판 상세보기에 내 해시태그 가져오기
	public List<String> myHash(int r_no) throws Exception {
		return dao.myHash(r_no);
		
	}

	@Override	//전체 해시테이블에 해당되는 해시 +1
	public void updateHash(String r_hash) throws Exception {
		System.out.println("update로 넘어온 hash : " + r_hash);
		dao.updatehash(r_hash);
	}
	@Override
	public void insertHash(String r_hash) throws Exception {
		System.out.println("insert로 넘어온 hash : " + r_hash);
		int hs_no = 0;
		if(dao.select_hs_no()==null){
			hs_no = 1;
		}else{
			hs_no = dao.select_hs_no()+1;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("hs_no", hs_no);
		map.put("hs_search", r_hash);
		dao.inserthash(map);
	}

//--------------------------------------------------------------------------------------------------------------
	// 추천 시작
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
		
		@Override
		public List<ReviewVO> reviewRecommend_service(String tag, ReviewSearchCriteria cri) {
			return dao.reviewRecommend(tag, cri);
		}
		
		@Override
		public Integer reviewRecommendCount_service(String tag) {
			return dao.reviewRecommendCount(tag);
		}
		
		@Override
		public Integer selectMr_no_service() {
			return dao.selectMr_no();
		}

		@Override
		public void registMemberRecommend_service(Member_RecommendVO mr) {
			dao.insertMemberRecommend(mr);
			System.out.println("서비스입니다");
		}

		@Override
		public List<Integer> selectCheckR_no_service(int r_no) {
			return dao.selectCheckR_no(r_no);
		}

		@Override
		public List<Map<String, Integer>> list_MemberRecommend_service() {
			return dao.list_MemberRecommend();
		}
		
		@Override
		public List<Integer> list_reviewRecommend(int m_no) throws Exception {
			// 추천하기 위해 전체 사용자의 후기 클릭 정보를 가져옴 -> 그 내용을 csv파일로 생성함
			List<Map<String, Integer>> list = dao.list_MemberRecommend();
			int mr_count = 0;
			
			//System.out.println(list);
			
			BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\data\\review_prefer.csv"));
		
			for(int i=0; i<list.size(); i++) {
				
				mr_count = Integer.parseInt(String.valueOf(list.get(i).get("MR_COUNT")));
				
				// 5번 이상 클릭 -> 선호도 5
				// 4번 클릭 -> 선호도 4 ...
				if( mr_count  >= 5){
					mr_count = 5;
				}else if(mr_count >= 4){
					mr_count = 4;
				}else if(mr_count >= 3){
					mr_count = 3;
				}else if(mr_count >= 2){
					mr_count = 2;
				}else if(mr_count >= 0){
					mr_count = 1;
				}
				
				//System.out.println("t서비스 " + mr_count);
				
				bw.write(list.get(i).get("M_NO") + "," + list.get(i).get("R_NO") + "," + mr_count +"\n");
			}
			bw.close();
			
			
			PearsonRecommender(m_no);
			
			return null;
		}
		
		
		
		public void PearsonRecommender(int m_no) throws Exception {

			System.out.println("피어슨");
			
			// 데이터 모델 생성
			DataModel dm = new FileDataModel(new File("C:\\data\\review_prefer.csv"));

			// 피어슨 - 유사도 모델 생성
			UserSimilarity sim = new PearsonCorrelationSimilarity(dm);

			// 가장 유사한 유저 2명을 기준으로 잡음
			UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, sim, dm);

			// 사용자 추천기 생성
			Recommender recommender = new GenericUserBasedRecommender(dm, neighborhood, sim);

			// 1번 유저에게 3개의 아이템 추천
			List<RecommendedItem> recommendations = recommender.recommend(1, 3);

			for (RecommendedItem recommendation : recommendations) {
				//System.out.println("피어슨 recommendation" + recommendation.getItemID());
			}
		}

		@Override
		public void updateMr_count_service(Member_RecommendVO mr) {
			dao.updateMr_count(mr);
		}		
		
		// 추천 끝

}
