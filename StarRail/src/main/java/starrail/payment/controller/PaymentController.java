package starrail.payment.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import starrail.main.domain.UserVO;
import starrail.payment.domain.CardCompanyVO;
import starrail.payment.domain.PaymentVO;
import starrail.payment.service.PaymentService;

@Controller
@RequestMapping("/payment/*") 
public class PaymentController {

	@Inject 
	private PaymentService service;
	
	//결제정보 등록
	@RequestMapping(value="/payment_insert", method=RequestMethod.GET)
	public void registerGET(@RequestParam("res_no")Integer res_no ,@RequestParam("res_price")String res_price , Model model)throws Exception{
		System.out.println("paymentGet success...");
		model.addAttribute("res_no",res_no);
		model.addAttribute("res_price",res_price);
	}
	
	@RequestMapping(value="/payment_insert", method=RequestMethod.POST)
	public String registerPOST(PaymentVO vo){
		System.out.println("paymenPost success...");
		System.out.println(vo.toString());
		try {
			service.regist(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/payment/payment_success";
	}
	
	@RequestMapping(value="/payment_success",method=RequestMethod.GET)
	public void success(Model model, HttpServletRequest request)throws Exception{
		System.out.println("payment success....");
	
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute("login");
		model.addAttribute("login", userVO);
		
	}
	
	
	//카드 check
	@ResponseBody
	@RequestMapping(value="pay", method=RequestMethod.GET)
	public List<CardCompanyVO> cardcheck(Model model)throws Exception{
		System.out.println("pay tfrctyft success....");
		List<CardCompanyVO> list = service.cardCheck();
		System.out.println(list);
		return list;
	}
}
