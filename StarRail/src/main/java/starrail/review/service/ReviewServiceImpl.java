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
	@Override		//�ı� �Խ��� ���
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
	@Override	//�ı�Խ��� �󼼺���
	public ReviewVO read(Integer r_no) throws Exception {
		dao.updateR_hit(r_no);
		return dao.selectReview(r_no);
	}

	@Transactional
	@Override	//�ı�Խ��� �����ϱ�
	public void modify(ReviewVO review) throws Exception {
		System.out.println("짱똘  : " + review.toString());
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
	@Override	//�ı�Խ��� ����
	public void remove(Integer r_no) throws Exception {
		dao.deleteAttach(r_no);
		dao.deleteReview(r_no);
	}

	@Override	//��ü����
	public List<ReviewVO> list() throws Exception {
		return dao.listReview();
	}

	@Override	//����¡ó��
	public List<ReviewVO> listCriteria(ReviewCriteria cri) throws Exception {
		return dao.listCriteria(cri);
	}

	@Override
	public int listCountCriteria(ReviewCriteria cri) throws Exception {
		return dao.countPaging(cri);
	}

	@Override	//�˻� + ����¡
	public List<ReviewVO> listSearchCriteria(ReviewSearchCriteria cri) throws Exception {
		return dao.listSearch(cri);
	}

	@Override
	public int listSearchCount(ReviewSearchCriteria cri) throws Exception {
		return dao.listSearchCount(cri);
	}

	@Override	//���� �ҷ�����
	public List<String> getAttach(Integer r_no) throws Exception {
		return dao.getAttach(r_no);
	}

	@Override	//hash �۹�ȣ ��������
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

	@Override	//�ؽ��±� ����ǥ�������� �ڸ���
	public List<String> hashtagInsert(ReviewVO review, Hash_SearchVO searchVO) throws Exception {
		//����ǥ����
		Pattern p = Pattern.compile("\\#([0-9a-zA-Z��-�R]*)");
		//�ı�Խ��� ���뿡 �ִ� �͵� �����ͼ�
		Matcher m = p.matcher(review.getR_content());
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<String> list = new ArrayList<>();
		int h_no = 0;
		String r_hash = null;
		
		while (m.find()) {
			r_hash = specialCharacter_replace(m.group());
			System.out.println("���� r_hash : " + r_hash);
			if (dao.hash_no() != null) {
				h_no = dao.hash_no() + 1;
				System.out.println("���� h_no : "+h_no);
			}else{
				h_no = 1;
			}
				paramMap.put("h_no", h_no);
				paramMap.put("r_no", review.getR_no());
				paramMap.put("r_hash", r_hash);
				System.out.println("���� : "+paramMap);
				dao.tagAdd(paramMap);
				list.add(r_hash);
			}
		
		return list;

	}

	@Override
	public String specialCharacter_replace(String str) throws Exception {
		str = StringUtils.replace(str, "-_+=!@#$%^&*()[]{}|\\;:'\"<>,.?/~`�� ", "");

		if (str.length() < 1) {
			return null;
		}
		return str;
	}

	@Override	//�±� ��ü ��������
	public List<String> hashSearch() throws Exception {

		return dao.HashSearch();
	}

	@Override	//�Խ��� �󼼺��⿡ �� �ؽ��±� ��������
	public List<String> myHash(int r_no) throws Exception {
		return dao.myHash(r_no);
		
	}

	@Override	//��ü �ؽ����̺� �ش�Ǵ� �ؽ� +1
	public void updateHash(String r_hash) throws Exception {
		System.out.println("update�� �Ѿ�� hash : " + r_hash);
		dao.updatehash(r_hash);
	}

	@Override
	public void insertHash(String r_hash) throws Exception {
		System.out.println("insert�� �Ѿ�� hash : " + r_hash);
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
