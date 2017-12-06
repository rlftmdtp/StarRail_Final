package starrail.expenses.service;

import java.util.List;
import java.util.Map;
import starrail.expenses.domain.ExpensesVO;
import starrail.expenses.domain.StatementVO;

public interface ExpensesService {
	//예상 경비 설정
	public void expensesRegist(ExpensesVO expensesVO) throws Exception;
	
	//총 남은금액 구해오기
	public Integer totalMoney(int e_no, int ed_amount) throws Exception;
	
	//지출 내역 저장
	public void amountRegist(StatementVO statementVO, Integer total) throws Exception;
	
	//사용자가 오늘 사용한 총 금액
	public int todayTotal(int e_no, String ed_date)throws Exception;

	//코스
	public List<Map<String, Object>> course(String id) throws Exception;
	
	//불러오기
	public List<Map<String, Object>> recall(String m_id) throws Exception;

	//선택한 저장내역 뽑아오기
	public List<Map<String, Object>> recallData(int e_no) throws Exception;
	
	
	
}
