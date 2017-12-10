package starrail.reservation.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import starrail.reservation.domain.ReservationVO;
import starrail.reservation.domain.SeatVO;
import starrail.reservation.persistence.ReservationDAO;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Inject
	private ReservationDAO dao;
	
	//������
	@Override
	public void regist(ReservationVO reservation) throws Exception {
		dao.rCreate(reservation);
	}

	//����������ȸ
	@Override
	public List<ReservationVO> reservationRead(Integer res_no) throws Exception {
		System.out.println("reservationService����");
		return dao.reservationRead(res_no);
	}

	@Override
	public List<SeatVO> seatList(String traintype) throws Exception {
		
		return dao.seatList(traintype);
	}

	@Override
	public Integer getMaxResNo() throws Exception {
		return dao.maxResNo();
	}

}
