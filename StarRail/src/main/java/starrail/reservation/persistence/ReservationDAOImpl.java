package starrail.reservation.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import starrail.reservation.domain.ReservationVO;
import starrail.reservation.domain.SeatVO;

@Repository
public class ReservationDAOImpl implements ReservationDAO {

	@Inject
	private SqlSession session;
	
	private static String namespace="starrail.reservation.mappers.reservationMapper";
	
	//예약 등록
	@Override
	public void rCreate(ReservationVO vo) throws Exception {
		System.out.println("ReservationImpl까지 성공");
		session.insert(namespace+".rCreate",vo);
	}

	//예약 정보 조회
	@Override
	public List<ReservationVO> reservationRead(Integer res_no) throws Exception {
		System.out.println(res_no);

		return session.selectList(namespace+".reservationRead",res_no);
	}
	
	//가장 최근에 등록된 예약 번호
	@Override
	public Integer maxResNo() throws Exception {
		return session.selectOne(namespace+".maxResNo");
	}

	@Override
	public List<SeatVO> seatList(String traintype) throws Exception {
	
		return session.selectList(namespace+".seatList", traintype);
	}


	
}
