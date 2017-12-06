package starrail.review.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import starrail.review.domain.FileVO;
import starrail.review.domain.ReviewVO;
import starrail.review.domain.ReviewCriteria;
import starrail.review.domain.ReviewSearchCriteria;


@Repository
public class ReviewDaoImpl implements ReviewDao{

	@Inject
	public SqlSession session;
	
	private static String namespace = "railro.review.mapper.ReviewMapper";
	
	@Override	//�ı�Խ��� ���
	public void insertReview(ReviewVO review) throws Exception{
		session.insert(namespace + ".insertReview", review);
	}
	
	@Override	//�ı�Խ��� �󼼺���
	public ReviewVO selectReview(Integer r_no) throws Exception{
		return session.selectOne(namespace + ".detailReview", r_no);
	}
	
	@Override	//�ı�Խ��� ����
	public void updateReview(ReviewVO review) throws Exception{
		session.update(namespace + ".updateReview", review);
	}
	
	@Override	//�ı�Խ��� ����
	public void deleteReview(Integer r_no) throws Exception{
		session.delete(namespace + ".deleteReview", r_no);
	}
	
	@Override	//��ü �Խ���
	public List<ReviewVO> listReview() throws Exception{
		return session.selectList(namespace+".listReview");
	}

	@Override //�۹�ȣ + 1
	public Integer selectR_no() {
		return session.selectOne(namespace+".selectR_no");
	}

	@Override	//����¡
	public List<ReviewVO> listPage(int Page) throws Exception {
		if(Page <= 0){
			Page = 1;
		}
		
		Page = (Page -1) *10;
		return session.selectList(namespace+".listPage", Page, new RowBounds(Page, 10));
	}

	@Override	//��ü�Խ��� + ����¡ó��
	public List<ReviewVO> listCriteria(ReviewCriteria cri) throws Exception {
		return session.selectList(namespace+".listCriteria", cri, new RowBounds(cri.getPageStart(), cri.getPerPageNum()));
	}

	@Override	//�� �Խù��� ����� 
	public int countPaging(ReviewCriteria cri) throws Exception {
		return session.selectOne(namespace+".countPaging", cri);
	}

	@Override	//��ü�Խ��� + ����¡ + �˻�
	public List<ReviewVO> listSearch(ReviewSearchCriteria cri) throws Exception {
		return session.selectList(namespace+".listSearch", cri, new RowBounds(cri.getPageStart(), cri.getPerPageNum()));
	}

	@Override
	public int listSearchCount(ReviewSearchCriteria cri) throws Exception {
		return session.selectOne(namespace+".listSearchCount", cri);
	}

	@Override	//���� ����
	public void addAttach(FileVO fileVO) throws Exception {
		session.insert(namespace+".addAttach", fileVO);
	}

	@Override	//���� �ҷ����� 
	public List<String> getAttach(Integer r_no) throws Exception {
		return session.selectList(namespace+".getAttach", r_no);
	}

	@Override	//���� ����
	public void deleteAttach(Integer r_no) throws Exception {
		session.delete(namespace+".deleteAttach", r_no);
	}

	@Override	//���� ����
	public void repalceAttach(String rf_fullname, Integer r_no) throws Exception {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		paramMap.put("r_no", r_no);
		paramMap.put("rf_fullname", rf_fullname);
		
		session.insert(namespace+".replaceAttach", paramMap);
	}

	
	@Override	//�Խù� ��ȸ��
	public void updateR_hit(Integer r_no) throws Exception {
		session.update(namespace+".updateR_hit", r_no);
	}

	@Override	//�۹�ȣ+1
	public int getR_no() throws Exception {
		return session.selectOne(namespace+".maxNum");
	}

	@Override	//�� �±� �߰��ϱ�
	public void tagAdd(Map<String, Object>paramMap) throws Exception {
		System.out.println("dao paramMap : " + paramMap);
		session.insert(namespace+".addHash", paramMap);
	}

	@Override
	public Integer hash_no() throws Exception {
		return session.selectOne(namespace+".selectH_no");
	}

	@Override	//��ü �±� ��������
	public List<String> HashSearch() throws Exception {
		return session.selectList(namespace+".tagGet");
	}

	@Override
	public List<String> myHash(int r_no) throws Exception {
		return session.selectList(namespace+".myhash", r_no);
	}

	@Override
	public void updatehash(String r_hash) throws Exception {
		session.update(namespace+".updatehash", r_hash);
	}

	@Override
	public void inserthash(Map<String, Object> map) throws Exception {
		session.insert(namespace+".inserthash", map);
	}

	@Override
	public Integer select_hs_no() throws Exception {
		return session.selectOne(namespace+".select_hs_no");
	}
	
}
