package starrail.expenses.persistence;

import java.util.List;
import java.util.Map;

import starrail.expenses.domain.ExpenseCourseVO;
import starrail.expenses.domain.ExpensesVO;
import starrail.expenses.domain.StatementVO;

public interface ExpensesDAO {

	//������ ����
	public void expensesInsert(ExpensesVO expensesVO) throws Exception;
	public Integer selectE_no() throws Exception;

	//�� �����ݾ� ���ؿ���
	public Integer totalMoney(int e_no) throws Exception;
	
	//���⳻�� ����
	public void amountInsert(StatementVO statementVO) throws Exception;
	public Integer selectEd_no() throws Exception;
	
	//�� ���� �ݾ�-���ݾ� �� �ݾ����� �� �����ݾ� ����
	public Integer updateExpenses(Map<String, Integer> map) throws Exception;
	
	//����ڰ� ���� ����� �� �ݾ� ��������
	public Integer todayTotal(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> course(String id) throws Exception;
	
	public List<Map<String, Object>> recall(String m_id) throws Exception;
	
	public List<Map<String, Object>> recallData(int e_no) throws Exception;

	
}
