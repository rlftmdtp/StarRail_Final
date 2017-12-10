package starrail.reservation.persistence;

import java.util.List;

import starrail.reservation.domain.ReservationVO;
import starrail.reservation.domain.SeatVO;

public interface ReservationDAO {

	public void rCreate(ReservationVO vo)throws Exception;
	public List<ReservationVO> reservationRead(Integer res_no)throws Exception;
	public Integer maxResNo() throws Exception;
	public List<SeatVO> seatList(String traintype)throws Exception;
	
}
