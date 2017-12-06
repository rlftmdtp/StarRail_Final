package starrail.review.persistence;

import java.util.List;
import java.util.Map;

import starrail.review.domain.FileVO;
import starrail.review.domain.Hash_SearchVO;
import starrail.review.domain.ReviewVO;
import starrail.review.domain.ReviewCriteria;
import starrail.review.domain.ReviewSearchCriteria;


public interface ReviewDao {

	//�ı�Խ��� ���
	public void insertReview(ReviewVO review) throws Exception;
	//�ı�Խ��� �󼼺���
	public ReviewVO selectReview(Integer r_no) throws Exception;
	//�ı�Խ��� ����
	public void updateReview(ReviewVO review) throws Exception;
	//�ı�Խ��� ����
	public void deleteReview(Integer r_no) throws Exception;
	//��ü �Խ���
	public List<ReviewVO> listReview() throws Exception;
	
	public List<ReviewVO> listPage(int Page) throws Exception;
	//��ü�Խ��� + ����¡ó��
	public List<ReviewVO> listCriteria(ReviewCriteria cri) throws Exception;
	//��ü�Խ��� + ����¡ + �˻�
	public List<ReviewVO> listSearch(ReviewSearchCriteria cri) throws Exception;
	
	public int listSearchCount(ReviewSearchCriteria cri) throws Exception;
	
	public Integer selectR_no() throws Exception;
	//�� �Խù��� ����� 
	public int countPaging(ReviewCriteria cri) throws Exception;

	//���� ����
	public void addAttach(FileVO rf_fullname) throws Exception;
	int getR_no() throws Exception;
	//���� �ҷ�����
	public List<String> getAttach(Integer r_no) throws Exception;
	//���� ����
	public void deleteAttach(Integer r_no) throws Exception;
	//���� ����
	public void repalceAttach(String rf_fullName, Integer r_no) throws Exception;
	//�Խù� ��ȸ��
	public void updateR_hit(Integer r_no) throws Exception;
	//�±� �߰�
//	public void tagAdd(Integer h_no, Integer r_no, String r_hash) throws Exception; 
	public void tagAdd(Map<String, Object> paramMap) throws Exception; 
	
	public Integer hash_no() throws Exception;
	//��ü �±� ��ȸ
	public List<String> HashSearch() throws Exception;
	
	//�Խ��� �󼼺��⿡ �ؽ��±� ��������
	public List<String> myHash(int r_no) throws Exception;
	
	public void updatehash(String r_hash) throws Exception;
	
	public void inserthash(Map<String, Object> map) throws Exception;
	
	public Integer select_hs_no() throws Exception;
}
