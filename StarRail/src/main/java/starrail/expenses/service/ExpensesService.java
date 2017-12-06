package starrail.expenses.service;

import java.util.List;
import java.util.Map;
import starrail.expenses.domain.ExpensesVO;
import starrail.expenses.domain.StatementVO;

public interface ExpensesService {
	//���� ��� ����
	public void expensesRegist(ExpensesVO expensesVO) throws Exception;
	
	//�� �����ݾ� ���ؿ���
	public Integer totalMoney(int e_no, int ed_amount) throws Exception;
	
	//���� ���� ����
	public void amountRegist(StatementVO statementVO, Integer total) throws Exception;
	
	//����ڰ� ���� ����� �� �ݾ�
	public int todayTotal(int e_no, String ed_date)throws Exception;

	//�ڽ�
	public List<Map<String, Object>> course(String id) throws Exception;
	
	//�ҷ�����
	public List<Map<String, Object>> recall(String m_id) throws Exception;

	//������ ���峻�� �̾ƿ���
	public List<Map<String, Object>> recallData(int e_no) throws Exception;
	
	
	
}
