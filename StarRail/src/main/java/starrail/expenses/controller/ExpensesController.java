package starrail.expenses.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import starrail.course.domain.CourseVO;
import starrail.expenses.domain.ExpensesVO;
import starrail.expenses.domain.StatementVO;
import starrail.expenses.service.ExpensesService;
import starrail.main.domain.UserVO;

@Controller
@RequestMapping(value = "/expenses/*")
public class ExpensesController {

	@Inject
	public ExpensesService service;

	//경비관리페이지 들어갈 때
	@RequestMapping(value = "/railro_expenses", method = RequestMethod.GET)
	public void railro_expensesGET(ExpensesVO expensesVO, Model model, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
	
		try {
			if(((UserVO) session.getAttribute("login")) != null){
				UserVO user =  (UserVO) session.getAttribute("login");		
				model.addAttribute("m_name", user.getM_name());
				model.addAttribute("m_id", user.getM_id());
			}else{
				model.addAttribute("m_name", null);
				model.addAttribute("m_id", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	// 예상경비 설정하기
	@RequestMapping(value = "/railro_expenses", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Integer> railro_expensesPOST(@RequestBody ExpensesVO expensesVO) throws Exception {
				service.expensesRegist(expensesVO);

			return new ResponseEntity<Integer>(expensesVO.getE_no(), HttpStatus.OK);

	}
	
	//지출내역 계산 및 저장
	@RequestMapping(value="/railro_amount", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> railro_amountPOST(@RequestBody StatementVO statementVO, ExpensesVO expensesVO) throws Exception{
		Map<String, Object> map = new HashMap<>();
		
		//총남은 금액 - 사용금액 계산한 것
		int totalMoney =service.totalMoney(statementVO.getE_no(), statementVO.getEd_amount());
		//계산하고 난 후 최종값 수정 및 지출내역 저장
		service.amountRegist(statementVO, service.totalMoney(statementVO.getE_no(), statementVO.getEd_amount())); 

		//오늘 쓴 총 금액
		int todayTotal = service.todayTotal(statementVO.getE_no(), statementVO.getEd_date());

		//map에 담아 jsp로 가져갈것
				map.put("ed_date", statementVO.getEd_date());
				map.put("ed_kategorie", statementVO.getEd_kategorie());
				map.put("ed_katename", statementVO.getEd_katename());
				map.put("ed_amount", statementVO.getEd_amount());
				map.put("e_no", statementVO.getE_no());
				map.put("e_total", totalMoney);
				map.put("todayTotal", todayTotal);
		
		return new ResponseEntity<Map<String, Object>>(map ,HttpStatus.OK);
	}
	
	//내 코스 가져올거야
	@RequestMapping(value="/expense_course", method=RequestMethod.POST)
	public ResponseEntity<List<CourseVO>> expense_coursePOST(@RequestBody String m_id)throws Exception{
		System.out.println("코스로 오니? ");
		System.out.println(m_id);
		List<CourseVO> list = new ArrayList<>();
		list = service.course(m_id);
		
		return new ResponseEntity<List<CourseVO>>(list, HttpStatus.OK);
	}
	
	//저장된 내역 불러오기
	@RequestMapping(value="/expense_recall", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<Map<String, Object>>> expense_recallPOST(@RequestBody String m_id)throws Exception{
		List<Map<String, Object>> list = new ArrayList<>();
		list = service.recall(m_id);
		System.out.println("컨트롤러 list : " + list);
		
		return new ResponseEntity<List<Map<String, Object>>>(list, HttpStatus.OK);
	}
	
	//저장된 내역 불러오기
	@RequestMapping(value="/recallData", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<Map<String, Object>>> recallDataPOST(@RequestBody int e_no)throws Exception{
		List<Map<String, Object>> list = new ArrayList<>();
		//int e_no = Integer.parseInt(data);
		System.out.println(e_no);
		list = service.recallData(e_no);
		System.out.println("컨트롤러맨~" + list);
		
		return new ResponseEntity<List<Map<String, Object>>>(list, HttpStatus.OK);
	}
	
	//저장된 내역 불러오기
	@RequestMapping(value="/listData", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<Map<String, Object>>> listDataPOST(@RequestBody StatementVO vo)throws Exception{
		List<Map<String, Object>> list =new ArrayList<>();
		System.out.println("=====================listDataPOST 접근");
		System.out.println("no: " + vo.getE_no());
		System.out.println("date: " + vo.getEd_date());
		list = service.listData(vo.getE_no(), vo.getEd_date());
		
		return new ResponseEntity<List<Map<String,Object>>>(list, HttpStatus.OK);
	}
	
	//도표
	@RequestMapping(value="/chart", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<Map<String, Integer>>> chartPOST(@RequestBody StatementVO vo)throws Exception{
		System.out.println(vo.getE_no());
		Map<String, Integer> map = new HashMap<>();
		map = service.chart(vo.getE_no());
		System.out.println(map);
		List<Map<String,Integer>> list = new ArrayList();
		
		list.add(map);
		return new ResponseEntity<List<Map<String, Integer>>>  (list, HttpStatus.OK);
	}

}
