package starrail.expenses.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

import starrail.expenses.domain.ExpenseCourseVO;
import starrail.expenses.domain.ExpensesVO;
import starrail.expenses.domain.StatementVO;
import starrail.expenses.persistence.ExpensesDAO;

@Service
public class ExpensesServiceImpl implements ExpensesService {

	@Inject
	private ExpensesDAO dao;

	@Override // 예상경비 저장하기
	public void expensesRegist(ExpensesVO expensesVO) throws Exception {

		if (dao.selectE_no() != null) {
			expensesVO.setE_no(dao.selectE_no() + 1);
		} else {
			expensesVO.setE_no(1);
		}
		dao.expensesInsert(expensesVO);
	}

	
	@Override
	public Integer totalMoney(int e_no, int ed_amount) throws Exception {
		// 총 잔액 - 사용금액 계산해주기
		int total = dao.totalMoney(e_no) - ed_amount;
		
		System.out.println("서비스 계산기 : dao.totalMoney(e_no) : " + dao.totalMoney(e_no));
		System.out.println("서비스 계산기 : ed_amount : " + ed_amount);
		System.out.println("서비스 계산기 : total : " + total);
		return total;
	}

	@Override	//지출내역 저장
	public void amountRegist(StatementVO statementVO, Integer total) throws Exception {
		if (dao.selectEd_no() != null) {
			statementVO.setEd_no(dao.selectEd_no() + 1);
		} else {
			statementVO.setEd_no(1);
		}

		// total하고 e_no같이 넘기기
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("total", total);
		map.put("e_no", statementVO.getE_no());
		dao.updateExpenses(map); // 가져온 total을 총잔액에 update해주고
		dao.amountInsert(statementVO); // 지출내역에는 insert해줌
	}

	@Override	//오늘 사용한 총 금액
	public int todayTotal(int e_no, String ed_date) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("e_no", e_no);
		map.put("ed_date", ed_date);

		//잔액이 없으면 0
		if(dao.todayTotal(map)==null){
			return 0;
		}else{
			return dao.todayTotal(map);
		}
		
	}

	@Override
	public List<Map<String, Object>> course(String id) throws Exception {
		System.out.println("service m_id는 : " +id);
		List<Map<String, Object>> list = new ArrayList<>();	
		
		list = dao.course(id);
		
		for(int i=0; i<list.size(); i++){
			System.out.println((i+1)+"c_id : "+list.get(i).get("c_name"));
			System.out.println("c_id : "+list.get(i).get("c_id"));
		}
		
		System.out.println("service list : " +list);
		return list;
	}

	@Override
	public List<Map<String, Object>> recall(String m_id) throws Exception {
		List<Map<String, Object>> list = new ArrayList<>();
		
		list = dao.recall(m_id);
		for(int i=0; i<list.size(); i++){
			System.out.println("e_no : "+list.get(i).get("e_no"));
			System.out.println("e_title : "+list.get(i).get("e_title"));
		}
		
		return list;
	}


	@Override
	public List<Map<String, Object>> recallData(int e_no) throws Exception {
		List<Map<String, Object>> list = new ArrayList<>();
		
		list = dao.recallData(e_no);
		for(int i=0; i<list.size(); i++){
			System.out.println("e_no : " + list.get(i).get("e_no"));
			System.out.println("e_sdate : " + list.get(i).get("e_sdate"));
			System.out.println("e_edate : " + list.get(i).get("e_edate"));
			System.out.println("e_total : " + list.get(i).get("e_total"));
			System.out.println("ed_no : " + list.get(i).get("ed_no"));
			System.out.println("ed_date : " + list.get(i).get("ed_date"));
			System.out.println("ed_kategorie : " + list.get(i).get("ed_kategorie"));
			System.out.println("ed_katename : " + list.get(i).get("ed_katename"));
			System.out.println("ed_amount : " + list.get(i).get("ed_amount"));
		}
				
		return list;
		
	}
}
