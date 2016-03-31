package data.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import data.entities.Token;

public class TokenDaoImpl implements TokenExtended {

	@Autowired
	private TokenDao tokenDao;
	
	@Override
	public void deleteExpiredTokens() {
		List<Token> allTokens = tokenDao.findAll();
		for (Token t:allTokens) {	
			if (t.isTokenExpired()) tokenDao.delete(t);
		}	
	}

	
}
