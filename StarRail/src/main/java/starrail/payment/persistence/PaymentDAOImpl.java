package starrail.payment.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import starrail.payment.domain.CardCompanyVO;
import starrail.payment.domain.PaymentVO;

@Repository
public class PaymentDAOImpl implements PaymentDAO {

	@Inject
	private SqlSession session;
	
	@Inject
	private static String namespace="starrail.recommend.mappers.paymentMapper";
	
	
	
	@Override
	public void payCreate(PaymentVO vo) throws Exception {
		session.insert(namespace+".payCreate",vo);

	}



	@Override
	public int maxPayNo() throws Exception {
		
		return session.selectOne(namespace+".maxPayNo");
	}

	
	@Override
	public CardCompanyVO cardView(CardCompanyVO cardVO) {
		return session.selectOne(namespace+".cardView", cardVO);
	}



	@Override
	public List<CardCompanyVO> cardCheck() throws Exception {
		
		return session.selectList(namespace+".cardcompany");
	}
}
