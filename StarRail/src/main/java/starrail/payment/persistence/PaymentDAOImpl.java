package starrail.payment.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

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

}
