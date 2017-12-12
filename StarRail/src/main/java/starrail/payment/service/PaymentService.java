package starrail.payment.service;

import starrail.payment.domain.PaymentVO;

public interface PaymentService {

	public void regist(PaymentVO payment)throws Exception;
	public int getMaxPayNo()throws Exception;
}
