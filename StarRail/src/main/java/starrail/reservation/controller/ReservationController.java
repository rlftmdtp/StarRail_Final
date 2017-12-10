package starrail.reservation.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import starrail.main.domain.UserVO;
import starrail.reservation.domain.ReservationVO;
import starrail.reservation.domain.SeatVO;
import starrail.reservation.domain.TrainSCVO;
import starrail.reservation.service.ReservationService;
import starrail.reservation.util.ReservationUtil;
import starrail.sharetext.domain.StationPakageVO;

@Controller
@RequestMapping("/reservation/*")
public class ReservationController {

@Inject
private ReservationUtil reservationutil;

@Inject
private ReservationService service;
	
//예약 저장
@RequestMapping(value="/Reservation_view", method=RequestMethod.GET)
public void registGET(ReservationVO reservation, Model model, HttpServletRequest request)throws Exception{
	System.out.println("Reservation registGET 성공");
	
	HttpSession session = request.getSession();
	UserVO userVO = (UserVO)session.getAttribute("login");
	model.addAttribute("login",userVO);
	
	List<String> citycode = reservationutil.getCityCode();
	List<StationPakageVO> list = reservationutil.stationList(citycode);
	model.addAttribute("list",list);
	
}
//예약 저장
@RequestMapping(value="/Reservation_view", method=RequestMethod.POST)
public String registPOST(ReservationVO reservation, RedirectAttributes rttr, Model model)throws Exception{
	System.out.println("Reservation registPOST 성공");
	System.out.println(reservation.toString());
	
	service.regist(reservation);
	/*model.addAttribute("result", "success");*/
	/*reservation.setRes_no(service.getMaxResNo());*/
	/*rttr.addFlashAttribute("msg", "success");*/
	/*rttr.addAttribute("res_no", reservation.getRes_no());*/
	return "redirect:/reservation/Reservation_result";
}
	

//예약 정보 조회
@RequestMapping(value="Reservation_result", method=RequestMethod.GET)
public void read(@RequestParam("res_no") int res_no, Model model) throws Exception{
	System.out.println("reservation read...中");
	model.addAttribute(service.reservationRead(res_no));
}


@RequestMapping(value="trainTime", method=RequestMethod.POST)
public ResponseEntity<List<TrainSCVO>>  trainTime(@RequestParam(value="s_station")String s_station,
													@RequestParam(value="e_station")String e_station,
													@RequestParam(value="s_date")String s_date,
													@RequestParam(value="s_time")String s_time)throws Exception{
	
	ResponseEntity<List<TrainSCVO>> entity = null;
	
	try {
		List<TrainSCVO> trains = reservationutil.getTimeTable(s_station, s_date, e_station, s_time);
		
		entity = new ResponseEntity<List<TrainSCVO>>(trains, HttpStatus.OK);
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	
	return entity;
}

@RequestMapping(value="seats", method=RequestMethod.POST)
public ResponseEntity<List<SeatVO>> seatListPost (@RequestParam(value="traintype")String traintype)throws Exception{
	
	ResponseEntity<List<SeatVO>> entity = null;
	
	try {
		List<SeatVO> list = service.seatList(traintype);
		entity= new ResponseEntity<List<SeatVO>>(list, HttpStatus.OK);
				
	} catch (Exception e) {
		e.printStackTrace();
		}
	return entity;
}


}
