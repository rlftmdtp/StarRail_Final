package starrail.expenses.persistence;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import starrail.expenses.domain.ExpenseCourseVO;
import starrail.expenses.domain.ExpensesVO;
import starrail.expenses.domain.StatementVO;

@Repository
public class ExpensesDAOImpl implements ExpensesDAO {
	
	@Inject
	public SqlSession session;
	
	private static String namespace = "railro.expenses.mapper.ExpensesMapper";

	@Override	//������ ����
	public void expensesInsert(ExpensesVO expensesVO) throws Exception {
		session.insert(namespace+".expensesInsert", expensesVO);
	}

	@Override	//������������ �� e_no 1�� ����
	public Integer selectE_no() throws Exception {
		return session.selectOne(namespace+".selectE_no");
	}
	
	@Override	//���⳻�� ����
	public void amountInsert(StatementVO statementVO) throws Exception {
		session.insert(namespace+".amountInsert", statementVO);
	}
	
	@Override	//�� �����ݾ� ���ؿ���
	public Integer totalMoney(int e_no) throws Exception {
		return session.selectOne(namespace+".totalMoney", e_no);
	}
	
	@Override
	public Integer selectEd_no() throws Exception {
		return session.selectOne(namespace+".selectEd_no");
	}

	@Override	//�� ���� �ݾ�-���ݾ� �� �ݾ����� �� �����ݾ� ����
	public Integer updateExpenses(Map<String, Integer> map) throws Exception {
		return session.update(namespace+".updateExpenses", map);
	}

	@Override	//����ڰ� ���� ����� �� �ݾ� ��������
	public Integer todayTotal(Map<String, Object> map) throws Exception {
		return session.selectOne(namespace+".todayTotal", map);
	}

	@Override
	public List<Map<String, Object>> course(String id) throws Exception {
		System.out.println("dao : " + id);
		return session.selectList(namespace+".course" , id);
	}

	@Override
	public List<Map<String, Object>> recall(String m_id) throws Exception {
		return session.selectList(namespace+".recall", m_id);
	}

	@Override
	public List<Map<String, Object>> recallData(int e_no) throws Exception {
		return session.selectList(namespace+".recallData", e_no);
	}







}
