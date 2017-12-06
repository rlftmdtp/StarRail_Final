package starrail.main.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import starrail.main.domain.UserVO;
import starrail.main.dto.LoginDTO;

@Repository
public class UserDAO{

	@Inject
	private SqlSession session;
	
	private static String namespace = "starrail.main.mappers.userMapper";
	
	public UserVO login(LoginDTO dto) throws Exception{
		
		return session.selectOne(namespace+".login",dto);
	}
}
