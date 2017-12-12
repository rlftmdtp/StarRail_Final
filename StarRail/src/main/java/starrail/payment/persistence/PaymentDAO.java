package starrail.payment.persistence;

import starrail.payment.domain.PaymentVO;

public interface PaymentDAO {

	public void payCreate (PaymentVO vo)throws Exception;
	public int maxPayNo() throws Exception;
}
