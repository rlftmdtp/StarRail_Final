package starrail.review.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import starrail.review.domain.FileVO;
import starrail.review.domain.Hash_SearchVO;
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










}
