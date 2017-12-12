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
	
	//���� ���
	@Override
	public void rCreate(ReservationVO vo) throws Exception {
		System.out.println("ReservationImpl테스트");
		session.insert(namespace+".rCreate",vo);
	}

	//���� ���� ��ȸ
	@Override
	public List<ReservationVO> reservationRead(Integer res_no) throws Exception {
		System.out.println(res_no);

		return session.selectList(namespace+".reservationRead",res_no);
	}
	
	//���� �ֱٿ� ��ϵ� ���� ��ȣ
	@Override
	public Integer maxResNo() throws Exception {
		return session.selectOne(namespace+".maxResNo");
	}

	@Override
	public List<SeatVO> seatList(String traintype) throws Exception {
	
		return session.selectList(namespace+".seatList", traintype);
	}


	
}
