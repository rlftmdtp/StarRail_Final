package starrail.reservation.service;

import java.util.List;

import starrail.reservation.domain.ReservationVO;
import starrail.reservation.domain.SeatVO;

public interface ReservationService {

	public void regist(ReservationVO reservation)throws Exception;
	public List<ReservationVO> reservationRead(Integer res_no)throws Exception;
	public List<SeatVO> seatList(String traintype)throws Exception;
	public Integer getMaxResNo()throws Exception;
	
}
