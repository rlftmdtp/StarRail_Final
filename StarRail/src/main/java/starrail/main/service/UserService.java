package starrail.main.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import starrail.main.domain.UserVO;
import starrail.main.dto.LoginDTO;
import starrail.main.persistence.UserDAO;

@Service
public class UserService {
	
	@Inject
	private UserDAO dao;
	
	public UserVO login(LoginDTO dto) throws Exception{
		return dao.login(dto);
	}
}
