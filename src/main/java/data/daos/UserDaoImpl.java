package data.daos;

import org.springframework.beans.factory.annotation.Autowired;

import data.entities.Token;
import data.entities.User;

public class UserDaoImpl implements UserDaoExtended {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TokenDao tokenDao;
	
	@Override
	public User findByTokenValue(String tokenValue) {
		System.out.println(tokenValue);
		Token token = tokenDao.findByValue(tokenValue);
		if (token == null) return null;
		if (token.isTokenExpired()) {
			return null;
		}
		else {
			return token.getUser();
		}
	}

}
