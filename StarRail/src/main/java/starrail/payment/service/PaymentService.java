package starrail.payment.service;

import java.util.List;

import starrail.payment.domain.CardCompanyVO;
import starrail.payment.domain.PaymentVO;

public interface PaymentService {

	public void regist(PaymentVO payment)throws Exception;
	public int getMaxPayNo()throws Exception;
	public List<CardCompanyVO> cardCheck()throws Exception;
	public CardCompanyVO cardView(CardCompanyVO vo)throws Exception;

}
