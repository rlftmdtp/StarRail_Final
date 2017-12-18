package starrail.payment.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import starrail.payment.domain.CardCompanyVO;
import starrail.payment.domain.PaymentVO;
import starrail.payment.persistence.PaymentDAO;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Inject
	private PaymentDAO dao;

	@Override
	public void regist(PaymentVO payment) throws Exception {
		
		dao.payCreate(payment);
		
	}

	@Override
	public int getMaxPayNo() throws Exception {
		
		return dao.maxPayNo();
	}
	
	@Override
	public CardCompanyVO cardView(CardCompanyVO vo) throws Exception {
		return dao.cardView(vo);
	}

	@Override
	public List<CardCompanyVO> cardCheck() throws Exception {
		
		return dao.cardCheck();
	}
	
}
