package starrail.payment.persistence;

import java.util.List;

import starrail.payment.domain.CardCompanyVO;
import starrail.payment.domain.PaymentVO;

public interface PaymentDAO {

	public void payCreate (PaymentVO vo)throws Exception;
	public int maxPayNo() throws Exception;
	public List<CardCompanyVO> cardCheck()throws Exception;
	public CardCompanyVO cardView(CardCompanyVO cardVO);
}
