package starrail.review.service;

import java.util.List;
import starrail.review.domain.ReviewVO;
import starrail.review.domain.Hash_SearchVO;
import starrail.review.domain.ReviewCriteria;
import starrail.review.domain.ReviewSearchCriteria;


public interface ReviewService {

	//�ı�Խ��� ���
	public void register(ReviewVO review) throws Exception;
	//�ı�Խ��� �󼼺���
	public ReviewVO read(Integer r_no) throws Exception;
	//�ı�Խ��� ����
	public void modify(ReviewVO review) throws Exception;
	//�ı�Խ��� ����
	public void remove(Integer r_no) throws Exception;
	//��ü �Խ���
	public List<ReviewVO> list() throws Exception;
	//��ü�Խ��� + ����¡ó��
	public List<ReviewVO> listCriteria(ReviewCriteria cri) throws Exception;
	
	public int listCountCriteria(ReviewCriteria cri) throws Exception;
	//��ü�Խ��� + ����¡ + �˻�
	public List<ReviewVO> listSearchCriteria(ReviewSearchCriteria cri) throws Exception;
	
	public int listSearchCount(ReviewSearchCriteria cri) throws Exception;
	//���� �ҷ�����
	public List<String> getAttach(Integer r_no) throws Exception;
	
	public int hash_no()throws Exception;

	public Integer selectR_no() throws Exception;
	
	public List<String> hashtagInsert(ReviewVO review, Hash_SearchVO searchVO) throws Exception;
	
	public String specialCharacter_replace(String str) throws Exception;
	//��ü �±� ��������
	public List<String> hashSearch() throws Exception;
	
	//�Խ��� �󼼺��⿡ �ؽ��±� ��������
	public List<String> myHash(int r_no) throws Exception;
	
	public void updateHash(String r_hash) throws Exception;
	public void insertHash(String r_hash) throws Exception;
	
}
